package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.TransactionEvent;
import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemzs.Names;
import com.oisix.oisystemzs.ejb.NyuukoLocal;
import com.oisix.oisystemzs.ejb.NyuukoLocalHome;
import com.oisix.oisystemzs.ejb.ShukkoLocal;
import com.oisix.oisystemzs.ejb.ShukkoLocalHome;
import com.oisix.oisystemzs.ejb.UserLocal;
import com.oisix.oisystemzs.ejb.SoukoData;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.LinkedList;
import java.util.Iterator;
import java.text.DateFormat;
import java.text.ParseException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.naming.NamingException;
import javax.ejb.FinderException;
import javax.ejb.CreateException;
import com.oisix.oisystemzs.ejb.exception.HaraidashiException;
import com.oisix.oisystemzs.ejb.exception.UkeireException;

public class DotanaoroshiinputEvent extends TransactionEvent {

    private int shukkokubun = 0;
    private java.util.Date tanaoroshidate;
    private TreeMap alltanaoroshilist = new TreeMap();
    private int page_id;
    private int next_page_id;
    private String shorikubun;

//    private final int onePageColumn = 20;
    private final int onePageColumn = 10;
    public int countTanaoroshi() {
        return alltanaoroshilist.size();
    }
    private int firstIndex() {
        return onePageColumn * page_id + 1;
    }
    private int lastIndex() {
        int last = onePageColumn * (page_id + 1);
        if (last > countTanaoroshi()) {
            last = countTanaoroshi();
        }
        return last;
    }

    private int user_id;
    private String souko_id;

    public void init(HttpServletRequest request) {
        // 出荷予定明細リストの取得
        alltanaoroshilist =
          (TreeMap)session.getAttribute("#TANAOROSHILIST");
        if (alltanaoroshilist == null) {
            errorlist.add("SystemError/TANAOROSHILISTが取得できません。");
            result = RC_INPUTERROR;
            return;
        }

        // ページ番号の取得
        Integer page_idint = (Integer)session.getAttribute("#TANAOROSHIPAGE");
        if (page_idint == null) {
            page_idint = new Integer(0);
        }
        try {
            page_id = page_idint.intValue();
        } catch (NumberFormatException nfe) {
            result = RC_INPUTERROR;
            errorlist.add("SystemError/ページ番号が取得できません。");
            return;
        }

        // 棚卸日の取得
        tanaoroshidate =
          (java.util.Date)session.getAttribute("#TANAOROSHIDATE");
        if (tanaoroshidate == null) {
            result = RC_INPUTERROR;
            errorlist.add("SystemError/棚卸日が取得できません");
            return;
        }

        // 処理区分
        shorikubun = getInput("#shorikubun");
        if (shorikubun == null || shorikubun.equals("")) {
            errorlist.add("SystemError/SHORIKUBUNが取得できません。");
            result = RC_INPUTERROR;
            return;
        }

        TreeMap newtanaoroshilist = new TreeMap();
        // 棚卸に関する入力情報を取得
        for (int i = firstIndex(); i <= lastIndex(); i++) {
            String strInd = String.valueOf(i);
            Integer intInd = new Integer(i);
            HashMap tanaoroshi = (HashMap)alltanaoroshilist.get(intInd);
            if (tanaoroshi == null) {
                errorlist.add("SystemError/" + strInd + "番目の" +
                  "棚卸データが取得できません。");
                result = RC_INPUTERROR;
                continue;
            }

            // 賞味期限の取得
            java.util.Date shoumikigen = null;
            String shoumikigenstr = getInput("#shoumikigen" + strInd);
            if (shoumikigenstr == null || shoumikigenstr.equals("")) {
                tanaoroshi.put("SHOUMIKIGEN", null);
            } else {
                try {
                    DateFormat df = DateFormat.getDateInstance();
                    shoumikigen = df.parse(shoumikigenstr);
                    tanaoroshi.put("SHOUMIKIGEN", shoumikigen);
                } catch (ParseException pe) {
                    Debug.println(pe);
                    errorlist.add("SystemError/" + strInd + "番目の" +
                      "SHOUMIKIGENが不正です。");
                    result = RC_INPUTERROR;
                    continue;
                }
            }

            // 出荷期限の取得
            java.util.Date shukkakigen = null;
            String shukkakigenstr = getInput("#shukkakigen" + strInd);
            if (shukkakigenstr == null || shukkakigenstr.equals("")) {
                tanaoroshi.put("SHUKKAKIGEN", null);
            } else {
                try {
                    DateFormat df = DateFormat.getDateInstance();
                    shukkakigen = df.parse(shukkakigenstr);
                    tanaoroshi.put("SHUKKAKIGEN", shukkakigen);
                } catch (ParseException pe) {
                    Debug.println(pe);
                    errorlist.add("SystemError/" + strInd + "番目の" +
                      "SHUKKAKIGENが不正です。");
                    result = RC_INPUTERROR;
                    continue;
                }
            }

            // 数量の取得
            String suuryoustr = getInput("#suuryou" + strInd);
            Float suuryou = null;
            if (suuryoustr == null || suuryoustr.equals("")) {
                errorlist.add(strInd + "番目の数量が未入力です。");
                result = RC_INPUTERROR;
                tanaoroshi.put("SUURYOU", "");
            } else {
                try {
                    suuryou = new Float(suuryoustr);
                    if (suuryou.floatValue() < 0) {
                        errorlist.add(strInd + "番目の数量が負です。");
                        result = RC_INPUTERROR;
                    }
                    tanaoroshi.put("SUURYOU", suuryou);
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add(strInd + "番目の数量が不正です。");
                    result = RC_INPUTERROR;
                    tanaoroshi.put("SUURYOU", suuryoustr);
                }
            }
            newtanaoroshilist.put(intInd, tanaoroshi);
        }

        if (result == RC_INPUTERROR) {
            next_page_id = page_id;
            return;
        }
        // 入力漏れやミスはなし
        alltanaoroshilist.putAll(newtanaoroshilist);
        if (shorikubun.equals("PREV")) {
            next_page_id = page_id - 1;
        }
        else if (shorikubun.equals("NEXT")) {
            next_page_id = page_id + 1;
        }
        if (!shorikubun.equals("INPUT")) {
            result = RC_INPUTERROR;
        } else {
            // ユーザの取得
            UserLocal user = (UserLocal)session.getAttribute("USER");
            user_id = user.getUser_id();
            SoukoData souko = (SoukoData)session.getAttribute("SOUKO");
            souko_id = souko.getSouko_id();
        }
    }

    public void handleEvent(HashMap attr) {
        if (result == RC_INPUTERROR) { return; }
        // 以下、出庫データ作成
        int i = 0;
        try {
            NyuukoLocalHome nklh = (NyuukoLocalHome)
              ServiceLocator.getLocalHome("java:comp/env/ejb/NyuukoLocal");
            ShukkoLocalHome sklh = (ShukkoLocalHome)
              ServiceLocator.getLocalHome("java:comp/env/ejb/ShukkoLocal");
            for (i = 1; i <= alltanaoroshilist.size(); i++) {
                Integer intInd = new Integer(i);
                HashMap tanaoroshi = (HashMap)alltanaoroshilist.get(intInd);
                float suuryou =
                  ((Float)tanaoroshi.get("SUURYOU")).floatValue();
                float motosuuryou =
                  ((Float)tanaoroshi.get("MOTOSUURYOU")).floatValue();
                if (suuryou > motosuuryou) {
                    // 棚卸調整の入庫
                    float newsuuryou = suuryou - motosuuryou;
                    NyuukoLocal newnyuuko = nklh.create(
                      tanaoroshidate,
                      souko_id,
                      (String)tanaoroshi.get("SHOUHIN_ID"),
                      newsuuryou,
                      (String)tanaoroshi.get("TANI"),
                      ((Float)tanaoroshi.get("TANKA")).floatValue(),
                      newsuuryou,
                      (String)tanaoroshi.get("TANI"),
                      ((Float)tanaoroshi.get("TANKA")).floatValue(),
                      (java.util.Date)tanaoroshi.get("SHOUMIKIGEN"),
                      (java.util.Date)tanaoroshi.get("SHUKKAKIGEN"),
                      4, // NYUUKOKUBUN
                      null,  // SHIIRESAKI_ID
                      null,  // NOUHINSAKI_ID
                      Names.NONE_ID,  // 入荷予定コード
                      Names.OFF,      // 訂正フラグ
                      Names.NONE_ID,  // 訂正出庫コード
                      user_id
                    );
                } else if (suuryou < motosuuryou) {
                    // 棚卸減耗の出庫
                    float newsuuryou = motosuuryou - suuryou;
                    float tanka =
                      ((Float)tanaoroshi.get("TANKA")).floatValue();
                    float kingaku = newsuuryou * tanka;
                    ShukkoLocal newshukko = sklh.create(
                      18, // SHUKKOKUBUN
                      null,  // NOUHINSAKI_ID
                      souko_id,
                      (String)tanaoroshi.get("SHOUHIN_ID"),
                      Names.DUMMY_LOCATION_ID,
                      tanaoroshidate,
                      (java.util.Date)tanaoroshi.get("SHOUMIKIGEN"),
                      newsuuryou,
                      (String)tanaoroshi.get("TANI"),
                      0, // 単価は自動計算
                      tanka,
                      kingaku,
                      Names.NONE_ID,  // 出荷予定明細コード
                      Names.OFF,      // 訂正フラグ
                      Names.NONE_ID,  // 訂正出庫コード
                      user_id,   // 標準売価
                      0
                    );
                }
            }
        } catch (NamingException ne) {
            Debug.println(ne);
            result = RC_INPUTERROR;
            errorlist.add("システムエラー・NamingException");
            setRollbackOnly();
            next_page_id = (int)((i - 1) / onePageColumn);
            return;
        } catch (CreateException ce) {
            Debug.println(ce);
            result = RC_INPUTERROR;
            errorlist.add("システムエラー・CreateException");
            setRollbackOnly();
            next_page_id = (int)((i - 1) / onePageColumn);
            return;
        } catch (UkeireException ue) {
            Debug.println(ue);
            result = RC_INPUTERROR;
            errorlist.add("システムエラー・UkeireException");
            setRollbackOnly();
            next_page_id = (int)((i - 1) / onePageColumn);
            return;
        } catch (HaraidashiException he) {
            Debug.println(he);
            result = RC_INPUTERROR;
            errorlist.add("システムエラー・HaraidashiException");
            setRollbackOnly();
            next_page_id = (int)((i - 1) / onePageColumn);
            return;
        }
    }

    public void postHandle(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (result == RC_INPUTERROR) {
            session.setAttribute("#TANAOROSHILIST", alltanaoroshilist);
            session.setAttribute("#TANAOROSHIPAGE", new Integer(next_page_id));
        }
    }
}
