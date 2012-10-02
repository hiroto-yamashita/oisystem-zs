package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.TransactionEvent;
import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemzs.ejb.ShukkayoteiLocal;
import com.oisix.oisystemzs.ejb.ShukkayoteiLocalHome;
import com.oisix.oisystemzs.ejb.ShukkayoteimeisaiLocal;
import com.oisix.oisystemzs.ejb.ShukkayoteimeisaiLocalHome;
import com.oisix.oisystemzs.ejb.UserLocal;
import com.oisix.oisystemzs.ejb.SoukoData;
import com.oisix.oisystemzs.objectmap.TaniMap;
import java.util.Calendar;
import java.util.HashSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.naming.NamingException;
import javax.ejb.CreateException;

public class DoshukkayoteiinputEvent extends TransactionEvent {

    private LinkedList meisaiList = new LinkedList();

    private int shukkokubun;
    private int shukkayoteishubetsu;
    private java.util.Date shukkayotei_date;
    private java.util.Date nouhinyotei_date;
    private String  shukkamoto_id;
    private String  nouhinsaki_id;
    private String  nouhinsakimei;
    private String  nohinsakiyuubin;
    private String  nouhinsakijuusho;
    private String  nouhinsakitel;
    private String  nouhinsakifax;
    private String  bikou;

    private int user_id;
    private String souko_id;

    private String shukkayotei_bg;
    public String getShukkayotei_bg() { return shukkayotei_bg; }

    public void init(HttpServletRequest request) {
        // 出庫区分の取得
        String shukkokubunstr = getInput("#shukkokubun");
        if (shukkokubunstr == null || shukkokubunstr.equals("")) {
            errorlist.add("出庫区分が未選択です。");
            result = RC_INPUTERROR;
        } else {
            try {
                shukkokubun = Integer.parseInt(shukkokubunstr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("出庫区分が不正です。");
                result = RC_INPUTERROR;
            }
        }

        // 出荷予定種別の取得
        String shubetsustr = getInput("#shukkayoteishubetsu");
        if (shubetsustr == null || shubetsustr.equals("")) {
            errorlist.add("出荷予定種別が未選択です。");
            result = RC_INPUTERROR;
        } else {
            try {
                shukkayoteishubetsu = Integer.parseInt(shubetsustr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("出荷予定種別が不正です。");
                result = RC_INPUTERROR;
            }
        }

        // 出荷予定日の取得
        int year = 0;
        int month = 0;
        int day = 0;
        String shukka_yearstr = getInput("#shukka_year");
        String shukka_monthstr = getInput("#shukka_month");
        String shukka_datestr = getInput("#shukka_date");
        try {
            year = Integer.parseInt(shukka_yearstr);
            month = Integer.parseInt(shukka_monthstr);
            day = Integer.parseInt(shukka_datestr);
            Calendar cal = Calendar.getInstance();
            cal.clear();
            cal.set(year, month - 1, day);
            shukkayotei_date = cal.getTime();
        } catch (NumberFormatException nfe) {
            Debug.println(nfe);
            errorlist.add("出荷予定日が不正です。");
            result = RC_INPUTERROR;
        }

        // 納品予定日の取得
        String nouhin_yearstr = getInput("#nouhin_year");
        String nouhin_monthstr = getInput("#nouhin_month");
        String nouhin_datestr = getInput("#nouhin_date");
        try {
            year = Integer.parseInt(nouhin_yearstr);
            month = Integer.parseInt(nouhin_monthstr);
            day = Integer.parseInt(nouhin_datestr);
            Calendar cal = Calendar.getInstance();
            cal.clear();
            cal.set(year, month - 1, day);
            nouhinyotei_date = cal.getTime();
        } catch (NumberFormatException nfe) {
            Debug.println(nfe);
            errorlist.add("納品予定日が不正です。");
            result = RC_INPUTERROR;
        }

        // 出荷元IDの取得
        shukkamoto_id = getInput("#shukkamoto_id");
        if (shukkamoto_id == null || shukkamoto_id.equals("")) {
            errorlist.add("出荷元コードが未入力です。");
            result = RC_INPUTERROR;
        }
        // 納品先IDの取得
        nouhinsaki_id = getInput("#nouhinsaki_id");
        if (nouhinsaki_id == null || nouhinsaki_id.equals("")) {
            nouhinsaki_id = null;
        }
        // 納品先データの取得
        nouhinsakimei = getInput("#nouhinsakimei");
        nohinsakiyuubin = getInput("#nohinsakiyuubin");
        nouhinsakijuusho = getInput("#nouhinsakijuusho");
        nouhinsakitel = getInput("#nouhinsakitel");
        nouhinsakifax = getInput("#nouhinsakifax");
        bikou = getInput("#bikou");

        // 同じ商品コード入力時対応用
        HashSet shouhin_idSet = new HashSet();
        for (int i = 0; i < 30; i++) {
            String strInd = String.valueOf(i);
            String shouhin_id = getInput("#shouhin_id"+strInd);
            // 商品IDが取得できた列のみ処理
            if (shouhin_id != null && !shouhin_id.equals("")) {
                // 二重入力対応
                if (shouhin_idSet.contains(shouhin_id)) {
                    errorlist.add("商品コード:" + 
                      shouhin_id + "が複数入力されています");
                    result = RC_INPUTERROR;
                    continue;
                }
                shouhin_idSet.add(shouhin_id);

                HashMap meisaiMap = new HashMap();
                meisaiMap.put("shouhin_id", shouhin_id);

                String suuryoustr = getInput("#suuryou"+strInd);
                Float suuryou = null;
                try {
                    suuryou = new Float(suuryoustr);
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add("SUURYOUが不正です。");
                    result = RC_INPUTERROR;
                    continue;
                }
                meisaiMap.put("suuryou", suuryou);

                String tanistr = getInput("#tani"+strInd);
                String tani;
                try {
                    int tani_id = Integer.parseInt(tanistr);
                    tani = TaniMap.getTani(tani_id).getTani();
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add("TANIが不正です。");
                    result = RC_INPUTERROR;
                    continue;
                }
                meisaiMap.put("tani", tani);
                meisaiList.add(meisaiMap);
            }
        }

        if (meisaiList.size() == 0) {
            errorlist.add("商品が１つも選択されていません。");
            result = RC_INPUTERROR;
        }

        UserLocal user = (UserLocal)session.getAttribute("USER");
        user_id = user.getUser_id();
        SoukoData souko = (SoukoData)session.getAttribute("SOUKO");
        souko_id = souko.getSouko_id();
    }

    public void handleEvent(HashMap attr) {
        if (result == RC_INPUTERROR) { return; }
        java.util.Date now = new java.util.Date();
        try {
            ShukkayoteiLocalHome sylh = (ShukkayoteiLocalHome)
              ServiceLocator.getLocalHome(
              "java:comp/env/ejb/ShukkayoteiLocal");
            ShukkayoteiLocal shukkayotei = sylh.create(
              shukkayoteishubetsu,
              shukkokubun,
              shukkayotei_date,
              nouhinyotei_date,
              shukkamoto_id,
              nouhinsaki_id,
              nouhinsakimei,
              nohinsakiyuubin,
              nouhinsakijuusho,
              nouhinsakitel,
              nouhinsakifax,
              bikou,
              user_id
            );
            int shukkayotei_id = shukkayotei.getShukkayotei_id();
            shukkayotei_bg = shukkayotei.getShukkayotei_bg();

            ShukkayoteimeisaiLocalHome symlh = (ShukkayoteimeisaiLocalHome)
              ServiceLocator.getLocalHome(
                "java:comp/env/ejb/ShukkayoteimeisaiLocal");
            Iterator iter = meisaiList.iterator();
            while (iter.hasNext()) {
                HashMap meisaiMap = (HashMap)iter.next();
                String shouhin_id = (String)meisaiMap.get("shouhin_id");
                String tani = (String)meisaiMap.get("tani");
                Float fSuuryou = (Float)meisaiMap.get("suuryou");
                float suuryou = fSuuryou.floatValue();
                ShukkayoteimeisaiLocal shukkayoteimeisai = symlh.create(
                  shukkayotei_bg,
                  souko_id,
                  shouhin_id,
                  suuryou,
                  tani,
                  0.0f,
                  1,
                  user_id,
                  0, //標準売価
                  null, //目安
                  null, //換算前商品コード
                  null, //換算前商品名
                  null, //換算前規格
                  0 //換算前数量
                );
            }
        } catch (NamingException ne) {
            Debug.println(ne);
            result = RC_INPUTERROR;
            errorlist.add("システムエラー・NamingException");
            setRollbackOnly();
            return;
        } catch (CreateException ce) {
            Debug.println(ce);
            result = RC_INPUTERROR;
            errorlist.add("システムエラー・CreateException");
            setRollbackOnly();
            return;
        }
    }

    public void postHandle(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("INPUTSHUKKAYOTEI_BG", shukkayotei_bg);
    }
}
