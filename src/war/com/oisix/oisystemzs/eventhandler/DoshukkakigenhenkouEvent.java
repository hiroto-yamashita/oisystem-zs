package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.TransactionEvent;
import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemzs.ejb.ZaikomeisaiPK;
import com.oisix.oisystemzs.ejb.ZaikomeisaiLocal;
import com.oisix.oisystemzs.ejb.ZaikomeisaiLocalHome;
import com.oisix.oisystemzs.ejb.ZaikoPK;
import com.oisix.oisystemzs.ejb.ZaikoLocal;
import com.oisix.oisystemzs.ejb.ZaikoLocalHome;
import com.oisix.oisystemzs.ejb.NyuukoPK;
import com.oisix.oisystemzs.ejb.NyuukoLocal;
import com.oisix.oisystemzs.ejb.NyuukoLocalHome;
import com.oisix.oisystemzs.ejb.UserLocal;
import com.oisix.oisystemzs.Names;
import java.util.HashMap;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.naming.NamingException;
import javax.ejb.FinderException;
import javax.ejb.CreateException;

public class DoshukkakigenhenkouEvent extends TransactionEvent {

    private int zaikomeisai_id;
    private java.util.Date shukkakigen;

    private int user_id;

    public void init(HttpServletRequest request) {
        // 在庫明細コードの取得
        String zaikomeisai_idstr = getInput("#id");
        try {
            zaikomeisai_id = Integer.parseInt(zaikomeisai_idstr);
        } catch (NumberFormatException nfe) {
            Debug.println(nfe);
            errorlist.add("システムエラー。idを数値に変換できません");
            result = RC_INPUTERROR;
            return;
        }

        // 出荷予定日の取得
        int year = 0;
        int month = 0;
        int day = 0;
        String shukkakigenyear = getInput("#kigenyear");
        String shukkakigenmonth = getInput("#kigenmonth");
        String shukkakigendate = getInput("#kigendate");
        try {
            year = Integer.parseInt(shukkakigenyear);
            month = Integer.parseInt(shukkakigenmonth);
            day = Integer.parseInt(shukkakigendate);
            Calendar cal = Calendar.getInstance();
            cal.clear();
            cal.set(year, month - 1, day);
            shukkakigen = cal.getTime();
        } catch (NumberFormatException nfe) {
            Debug.println(nfe);
            errorlist.add("出荷期限が不正です。");
            result = RC_INPUTERROR;
        }

        // ユーザの取得
        UserLocal user = (UserLocal)session.getAttribute("USER");
        user_id = user.getUser_id();
    }

    public void handleEvent(HashMap attr) {
        if (result == RC_INPUTERROR) { return; }
        try {
            // 在庫明細データの取得
            ZaikomeisaiLocalHome zmlh = (ZaikomeisaiLocalHome)
              ServiceLocator.getLocalHome(
              "java:comp/env/ejb/ZaikomeisaiLocal");
            ZaikomeisaiPK zmpk = new ZaikomeisaiPK(zaikomeisai_id);
            ZaikomeisaiLocal zml = zmlh.findByPrimaryKey(zmpk);
            int zaiko_id = zml.getZaiko_id();
            zml.setShukkakigen(shukkakigen);

            // 在庫データの取得
            ZaikoLocalHome zlh = (ZaikoLocalHome)
              ServiceLocator.getLocalHome("java:comp/env/ejb/ZaikoLocal");
            ZaikoPK zpk = new ZaikoPK(zaiko_id);
            ZaikoLocal zl = zlh.findByPrimaryKey(zpk);
            int nyuuko_id = zl.getNyuuko_id();

            if (nyuuko_id != Names.NONE_ID) {
                // 入庫データがある場合は一緒に更新
                NyuukoLocalHome nlh = (NyuukoLocalHome)
                  ServiceLocator.getLocalHome("java:comp/env/ejb/NyuukoLocal");
                NyuukoPK npk = new NyuukoPK(nyuuko_id);
                NyuukoLocal nl = nlh.findByPrimaryKey(npk);
                nl.setShukkakigen(shukkakigen);
            }
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
        }
    }

    public void postHandle(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("ZAIKOMEISAI_ID", String.valueOf(zaikomeisai_id));
    }
}
