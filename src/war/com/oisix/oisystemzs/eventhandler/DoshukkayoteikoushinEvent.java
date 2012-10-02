package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.TransactionEvent;
import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemzs.ejb.ShukkayoteiLocal;
import com.oisix.oisystemzs.ejb.ShukkayoteiLocalHome;
import com.oisix.oisystemzs.ejb.ShukkayoteiPK;
import com.oisix.oisystemzs.ejb.ShukkayoteimeisaiLocal;
import com.oisix.oisystemzs.ejb.ShukkayoteimeisaiLocalHome;
import com.oisix.oisystemzs.ejb.ShukkayoteimeisaiPK;
import com.oisix.oisystemzs.ejb.UserLocal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.naming.NamingException;
import javax.ejb.CreateException;
import javax.ejb.FinderException;

public class DoshukkayoteikoushinEvent extends TransactionEvent {

    private LinkedList meisaiList = new LinkedList();
    private LinkedList newmeisaiList = new LinkedList();

    private String strShukkayotei_id;

    private int shukkayotei_id;
    private int shukkokubun;
    private int shukkayoteishubetsu;
    private java.util.Date shukkayotei_date;
    private java.util.Date nouhinyotei_date;
    private String  shukkamoto_id;
    private String  nouhinsaki_id;
    private String  nouhinsakimei;
    private String  nouhinsakiyuubin;
    private String  nouhinsakijuusho;
    private String  nouhinsakitel;
    private String  nouhinsakifax;
    private String  bikou;

    private int user_id;

    private String shukkayotei_bg;
    public String getShukkayotei_bg() { return shukkayotei_bg; }

    public void init(HttpServletRequest request) {
        // 出荷予定番号の取得
        strShukkayotei_id = getInput("#shukkayotei_id");
        try {
            shukkayotei_id = Integer.parseInt(strShukkayotei_id);
        } catch (NumberFormatException nfe) {
            Debug.println(nfe);
            errorlist.add("SHUKKAYOTEI_IDが不正です。");
            result = RC_INPUTERROR;
            return;
        }

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
//        if (nouhinsaki_id == null || nouhinsaki_id.equals("")) {
//            errorlist.add("納品先コードが未入力です。");
//            result = RC_INPUTERROR;
//        }
        // 納品先データの取得
        nouhinsakimei = getInput("#nouhinsakimei");
        nouhinsakiyuubin = getInput("#nohinsakiyuubin");
        nouhinsakijuusho = getInput("#nouhinsakijuusho");
        nouhinsakitel = getInput("#nouhinsakitel");
        nouhinsakifax = getInput("#nouhinsakifax");
        bikou = getInput("#bikou");

        // 明細数の取得
        String strMeisaisuu = getInput("#meisaisuu");
        int meisaisuu = 0;
        try {
            meisaisuu = Integer.parseInt(strMeisaisuu);
        } catch (NumberFormatException nfe) {
            Debug.println(nfe);
            errorlist.add("MEISAISUUが不正です。");
            result = RC_INPUTERROR;
            return;
        }

        for (int i = 0; i < meisaisuu; i++) {
            String strInd = String.valueOf(i);
            HashMap meisaiMap = new HashMap();
            String meisai_idstr = getInput("#shukkayoteimeisai_id"+strInd);
            Integer meisai_id = null;
            try {
                meisai_id = new Integer(meisai_idstr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("出荷明細IDが不正です。");
                result = RC_INPUTERROR;
                return;
            }
            meisaiMap.put("meisai_id", meisai_id);

            String shouhin_id = getInput("#shouhin_id"+strInd);
            if (shouhin_id == null || shouhin_id.equals("")) {
                errorlist.add("商品コードを入力してください。");
                result = RC_INPUTERROR;
                return;
            }
            meisaiMap.put("shouhin_id", shouhin_id);

            String suuryoustr = getInput("#suuryou"+strInd);
            if (suuryoustr == null || suuryoustr.equals("")) {
                errorlist.add("数量を入力してください。");
                result = RC_INPUTERROR;
                return;
            }
            Float suuryou = null;
            try {
                suuryou = new Float(suuryoustr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("数量が不正です。");
                result = RC_INPUTERROR;
                return;
            }
            meisaiMap.put("suuryou", suuryou);

            String tani = getInput("#tani"+strInd);
            if (tani == null || tani.equals("")) {
                errorlist.add("単位を選択してください。");
                result = RC_INPUTERROR;
                return;
            }
            meisaiMap.put("tani", tani);

            String shukkajoukyoustr = getInput("#shukkajoukyou"+strInd);
            if (shukkajoukyoustr == null || shukkajoukyoustr.equals("")) {
                errorlist.add("出荷状況を選択してください。");
                result = RC_INPUTERROR;
                return;
            }
            Integer shukkajoukyou = null;
            try {
                shukkajoukyou = new Integer(shukkajoukyoustr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("出荷状況が不正です。");
                result = RC_INPUTERROR;
                return;
            }
            meisaiMap.put("shukkajoukyou", shukkajoukyou);

            meisaiList.add(meisaiMap);
        }

        if (meisaiList.size() == 0) {
            errorlist.add("商品が１つも選択されていません。");
            result = RC_INPUTERROR;
        }

        // 新規明細数の取得
        String strNewmeisaisuu = getInput("#newmeisaisuu");
        int newmeisaisuu = 0;
        try {
            newmeisaisuu = Integer.parseInt(strNewmeisaisuu);
        } catch (NumberFormatException nfe) {
            Debug.println(nfe);
            errorlist.add("NEWMEISAISUUが不正です。");
            result = RC_INPUTERROR;
            return;
        }

        for (int i = 0; i < newmeisaisuu; i++) {
            String strInd = String.valueOf(i);
            String shouhin_id = getInput("#shouhin_idnew"+strInd);
            // 商品IDが取得できた列のみ処理
            if (shouhin_id != null && !shouhin_id.equals("")) {
                HashMap newmeisaiMap = new HashMap();
                newmeisaiMap.put("shouhin_id", shouhin_id);

                String suuryoustr = getInput("#suuryounew"+strInd);
                if (suuryoustr == null || suuryoustr.equals("")) {
                    errorlist.add("数量を入力してください。");
                    result = RC_INPUTERROR;
                    return;
                }
                Float suuryou = null;
                try {
                    suuryou = new Float(suuryoustr);
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add("数量が不正です。");
                    result = RC_INPUTERROR;
                    continue;
                }
                newmeisaiMap.put("suuryou", suuryou);

                String tani = getInput("#taninew"+strInd);
                if (tani == null || tani.equals("")) {
                    errorlist.add("単位を選択してください。");
                    result = RC_INPUTERROR;
                    return;
                }
                newmeisaiMap.put("tani", tani);

                String shukkajoukyoustr = getInput("#shukkajoukyounew"+strInd);
                if (shukkajoukyoustr == null || shukkajoukyoustr.equals("")) {
                    errorlist.add("出荷状況を選択してください。");
                    result = RC_INPUTERROR;
                    return;
                }
                Integer shukkajoukyou = null;
                try {
                    shukkajoukyou = new Integer(shukkajoukyoustr);
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add("出荷状況が不正です。");
                    result = RC_INPUTERROR;
                    return;
                }
                newmeisaiMap.put("shukkajoukyou", shukkajoukyou);

                newmeisaiList.add(newmeisaiMap);
            }
        }

        UserLocal user = (UserLocal)session.getAttribute("USER");
        user_id = user.getUser_id();
    }

    public void handleEvent(HashMap attr) {
        if (result == RC_INPUTERROR) { return; }
        java.util.Date now = new java.util.Date();
        try {
            ShukkayoteiLocalHome sylh = (ShukkayoteiLocalHome)
              ServiceLocator.getLocalHome(
              "java:comp/env/ejb/ShukkayoteiLocal");
            ShukkayoteiPK sypk = new ShukkayoteiPK(shukkayotei_id);
            ShukkayoteiLocal shukkayotei = sylh.findByPrimaryKey(sypk);
            shukkayotei_bg = shukkayotei.getShukkayotei_bg();
            // 出荷予定データの更新
            shukkayotei.setShukkokubun(shukkokubun);
            shukkayotei.setShukkayoteishubetsu(shukkayoteishubetsu);
            shukkayotei.setShukkayotei_date(shukkayotei_date);
            shukkayotei.setNouhinyotei_date(nouhinyotei_date);
            shukkayotei.setShukkamoto_id(shukkamoto_id);
            shukkayotei.setNouhinsaki_id(nouhinsaki_id);
            shukkayotei.setNouhinsakiname(nouhinsakimei);
            shukkayotei.setNouhinsakizip(nouhinsakiyuubin);
            shukkayotei.setNouhinsakiaddr(nouhinsakijuusho);
            shukkayotei.setNouhinsakitel(nouhinsakitel);
            shukkayotei.setNouhinsakifax(nouhinsakifax);
            shukkayotei.setBikou(bikou);
            shukkayotei.setUpdated(now);
            shukkayotei.setUpdatedby(user_id);

            String souko_id = "";

            ShukkayoteimeisaiLocalHome symlh = (ShukkayoteimeisaiLocalHome)
              ServiceLocator.getLocalHome(
                "java:comp/env/ejb/ShukkayoteimeisaiLocal");
            // 既存入荷予定明細の更新
            Iterator iter = meisaiList.iterator();
            while (iter.hasNext()) {
                HashMap meisaiMap = (HashMap)iter.next();
                Integer iMeisai_id = (Integer)meisaiMap.get("meisai_id");
                int meisai_id = iMeisai_id.intValue();
                String shouhin_id = (String)meisaiMap.get("shouhin_id");
                String tani = (String)meisaiMap.get("tani");
                Float fSuuryou = (Float)meisaiMap.get("suuryou");
                float suuryou = fSuuryou.floatValue();
                Integer iShukkajoukyou =
                  (Integer)meisaiMap.get("shukkajoukyou");
                int shukkajoukyou = iShukkajoukyou.intValue();
                ShukkayoteimeisaiPK sympk = new ShukkayoteimeisaiPK(meisai_id);
                ShukkayoteimeisaiLocal syml = symlh.findByPrimaryKey(sympk);
                souko_id = syml.getSouko_id();
                // 出荷予定データの更新
                syml.setShouhin_id(shouhin_id);
                syml.setTani(tani);
                syml.setShukkayoteisuuryou(suuryou);
                syml.setShukkajoukyou(shukkajoukyou);
            }
            // 新規入荷予定明細の入力
            iter = newmeisaiList.iterator();
            while (iter.hasNext()) {
                HashMap meisaiMap = (HashMap)iter.next();
                String shouhin_id = (String)meisaiMap.get("shouhin_id");
                String tani = (String)meisaiMap.get("tani");
                Float fSuuryou = (Float)meisaiMap.get("suuryou");
                float suuryou = fSuuryou.floatValue();
                Integer iShukkajoukyou =
                  (Integer)meisaiMap.get("shukkajoukyou");
                int shukkajoukyou = iShukkajoukyou.intValue();
                ShukkayoteimeisaiLocal shukkayoteimeisai = symlh.create(
                  shukkayotei_bg,
                  souko_id,
                  shouhin_id,
                  suuryou,
                  tani,
                  0.0f,
                  shukkajoukyou,
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
        } catch (FinderException fe) {
            Debug.println(fe);
            result = RC_INPUTERROR;
            errorlist.add("システムエラー・CreateException");
            setRollbackOnly();
            return;
        }
    }

    public void postHandle(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("KOUSHINSHUKKAYOTEI_BG", shukkayotei_bg);
        session.setAttribute("KOUSHINSHUKKAYOTEI_ID", strShukkayotei_id);
    }
}
