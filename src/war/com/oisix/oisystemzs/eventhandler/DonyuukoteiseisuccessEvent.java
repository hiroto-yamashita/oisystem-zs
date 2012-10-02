package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.TransactionEvent;
import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemzs.ejb.NyuukoPK;
import com.oisix.oisystemzs.ejb.NyuukoLocal;
import com.oisix.oisystemzs.ejb.NyuukoLocalHome;
import com.oisix.oisystemzs.ejb.NyuukoData;
import com.oisix.oisystemzs.ejb.UserLocal;
import com.oisix.oisystemzs.ejb.exception.UkeireException;
import com.oisix.oisystemzs.ejb.exception.NyuukoModifyException;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.naming.NamingException;
import javax.ejb.FinderException;
import javax.ejb.CreateException;

public class DonyuukoteiseisuccessEvent extends TransactionEvent {

    private String strNyuuko_id;
    private int motonyuuko_id;

    private String motonyuuko_bg;
    private String teiseinyuuko_bg;
    private String teiseinyuukostr;
    
    public String getMotonyuuko_bg() { return motonyuuko_bg; }
    public String getTeiseinyuuko_bg() { return teiseinyuuko_bg; }

    //private boolean isAutoCalcTanka;
    private int user_id;
    private float shiiresuuryou;
    private float nyuukosuuryou;
    //private float tanka;

    public void init(HttpServletRequest request) {
        // 入庫番号の取得
        strNyuuko_id = getInput("#nyuuko_id");
        try {
            motonyuuko_id = Integer.parseInt(strNyuuko_id);
        } catch (NumberFormatException nfe) {
            Debug.println(nfe);
            errorlist.add("NYUUKO_IDが不正です。");
            result = RC_INPUTERROR;
            return;
        }
        // 移動平均単価または標準単価を計算
        //String strTanka = getInput("#nyuukotanka");
        //if (strTanka == null || strTanka.equals("")) {
        //    isAutoCalcTanka = true;
        //}
        //else {
        //    isAutoCalcTanka = false;
        //    try {
        //        tanka = Float.parseFloat(strTanka);
        //    } catch (NumberFormatException nfe) {
        //        Debug.println(nfe);
        //        errorlist.add("単価が不正です。");
        //        result = RC_INPUTERROR;
        //    }
        //}
        // 入庫数量の取得
        String strNyuukosuuryou = getInput("#nyuukosuuryou");
        try {
            nyuukosuuryou = Float.parseFloat(strNyuukosuuryou);
        } catch (NumberFormatException nfe) {
            Debug.println(nfe);
            errorlist.add("入庫数量が不正です。");
            result = RC_INPUTERROR;
        }
        // 仕入数量の取得
        String strShiiresuuryou = getInput("#shiiresuuryou");
        try {
            shiiresuuryou = Float.parseFloat(strShiiresuuryou);
        } catch (NumberFormatException nfe) {
            Debug.println(nfe);
            errorlist.add("仕入数量が不正です。");
            result = RC_INPUTERROR;
        }
        // ユーザの取得
        UserLocal user = (UserLocal)session.getAttribute("USER");
        user_id = user.getUser_id();
    }

    public void handleEvent(HashMap attr) {
        if (result == RC_INPUTERROR) { return; }
        java.util.Date now = new java.util.Date();
        try {
            // 元入庫データの取得
            NyuukoLocalHome nklh = (NyuukoLocalHome)
              ServiceLocator.getLocalHome("java:comp/env/ejb/NyuukoLocal");
            NyuukoPK pk = new NyuukoPK(motonyuuko_id);
            NyuukoLocal motonyuuko = nklh.findByPrimaryKey(pk);
            motonyuuko_bg = motonyuuko.getNyuuko_bg();

            // 訂正入庫データの作成準備
            NyuukoData teiseiData = motonyuuko.getNyuukoData();
            float teiseinyuukosuuryou = -1*nyuukosuuryou - teiseiData.getNyuukosuuryou();
            if (teiseinyuukosuuryou > 0) {
                result = RC_INPUTERROR;
                errorlist.add("数量が訂正前より増えています。");
            }
            float teiseishiiresuuryou = -1*shiiresuuryou - teiseiData.getShiiresuuryou();
            if (teiseishiiresuuryou > 0) {
                result = RC_INPUTERROR;
                errorlist.add("数量が訂正前より増えています。");
            }
            
            //float teiseitanka = tanka;
            //float teiseinyuukokingaku = teiseinyuukosuuryou * teiseitanka;
            //if (teiseinyuukokingaku > 0) {
            //    result = RC_INPUTERROR;
            //    errorlist.add("金額が訂正前より増えています。");
            //}

            if (result == RC_INPUTERROR) {
                setRollbackOnly();
                return;
            }

            // 訂正入庫データの作成
            NyuukoLocal teiseinyuuko = motonyuuko.modify(
              nyuukosuuryou,
              shiiresuuryou,
              user_id
            );
            int teiseinyuuko_id = teiseinyuuko.getNyuuko_id();
            String teiseinyuukostr = Integer.toString(teiseinyuuko_id);
            teiseinyuuko_bg = teiseinyuuko.getNyuuko_bg();

        } catch (NamingException ne) {
            Debug.println(ne);
            result = RC_INPUTERROR;
            errorlist.add("システムエラー・NamingException");
            setRollbackOnly();
            return;
        } catch (FinderException fe) {
            Debug.println(fe);
            result = RC_INPUTERROR;
            errorlist.add("システムエラー・FinderException");
            setRollbackOnly();
            return;
        } catch (UkeireException ue){
            Debug.println(ue);
            result = RC_INPUTERROR;
            errorlist.add("システムエラー・UkeireException");
            setRollbackOnly();
            return;
        } catch (NyuukoModifyException nme){
            Debug.println(nme);
            result = RC_INPUTERROR;
            errorlist.add("システムエラー・NyuukoModifyException");
            setRollbackOnly();
            return;
        }
    }

    public void postHandle(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("MOTONYUUKO_BG", motonyuuko_bg);
        session.setAttribute("TEISEINYUUKO_BG", teiseinyuuko_bg);
        session.setAttribute("TEISEINYUUKO_ID", teiseinyuukostr);
    }
}
