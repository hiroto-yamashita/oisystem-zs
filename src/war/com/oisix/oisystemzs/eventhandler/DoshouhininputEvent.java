package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.TransactionEvent;
import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemzs.Names;
import com.oisix.oisystemzs.ejb.ShouhinLocal;
import com.oisix.oisystemzs.ejb.ShouhinLocalHome;
import com.oisix.oisystemzs.ejb.UserLocal;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.naming.NamingException;
import javax.ejb.CreateException;
import javax.ejb.DuplicateKeyException;

public class DoshouhininputEvent extends TransactionEvent {

    private java.lang.String shouhin_id;
    private java.lang.String shouhin;
    private java.lang.String shouhinfurigana;
    private java.lang.String shiiresaki_id;
    private java.lang.String hacchuushouhin1;
    private java.lang.String hacchuushouhin2;
    private java.lang.String hacchuushouhin3;
    private java.lang.String kikaku;
    private int tani;
    private java.lang.String hacchuukikaku;
    private int hacchuuten;
    private int hacchuutani;
    //private int irisuu;
    private float irisuu;
    private int hacchuutanisuu;
    private int saiteihacchuusuu;
    private float tanka;
    private float hyoujuntanka;
    private int shiireleadtime;
    private int ondotai;
    private int shoumikigen_flg;
    private int shoumikigennissuu;
    private int shukkakigennissuu;
    private int kobetsuhacchuu_flg;
    private int youchuuihin_flg;
    private int youraberu_flg;
    private java.lang.String daibunrui;
    private java.lang.String pcode;
    private int zaikohyoukahouhou;
    private java.lang.String jancode;
    private java.lang.String kataban;
    private java.lang.String location_id1;
    private java.lang.String location_id2;
    private java.lang.String location_id3;
    private int shuubai_flg;
    private int user_id;
    private float hyoujunbaika;
    private String nisugata;
    private String hacchuucomment;
    private int hanbaikubun;
    private String shubetsu;
    private int mochikoshi_flg;
    private int shinyanouhin_flg;

    public void init(HttpServletRequest request) {
        shouhin_id = getInput("#shouhin_id");
        if ((shouhin_id == null) || (shouhin_id.length() ==0)){
            errorlist.add("商品コードが入力されていません");
            result = RC_INPUTERROR;
        }
        shouhin = getInput("#shouhin");
        if ((shouhin == null) || (shouhin.length() ==0)){
            errorlist.add("商品名が入力されていません");
            result = RC_INPUTERROR;
        }
        shouhinfurigana = getInput("#shouhinfurigana");
        if ((shouhinfurigana == null) || (shouhinfurigana.length() ==0)){
            errorlist.add("フリガナが入力されていません");
            result = RC_INPUTERROR;
        }
        shiiresaki_id = getInput("#shiiresaki_id");
        if ((shiiresaki_id == null) || (shiiresaki_id.length() ==0)){
            errorlist.add("仕入先コードが入力されていません");
            result = RC_INPUTERROR;
        }
        hacchuushouhin1 = getInput("#hacchuushouhin1");
        if ((hacchuushouhin1 == null) || (hacchuushouhin1.length() ==0)){
            errorlist.add("発注商品名1が入力されていません");
            result = RC_INPUTERROR;
        }
        hacchuushouhin2 = getInput("#hacchuushouhin2");
        hacchuushouhin3 = getInput("#hacchuushouhin3");
        kikaku = getInput("#kikaku");
        String tanistr = getInput("#tani");
        if ((tanistr == null) || (tanistr.length() == 0)) {
            errorlist.add("単位を選択してください");
            result = RC_INPUTERROR;
        } else {
            try {
                tani = Integer.parseInt(tanistr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("単位が不正です");
                result = RC_INPUTERROR;
            }
        }
        hacchuukikaku = getInput("#hacchuukikaku");
        if ((hacchuukikaku == null) || (hacchuukikaku.length() ==0)){
            errorlist.add("発注規格が入力されていません");
            result = RC_INPUTERROR;
        }
        String hacchuutenstr = getInput("#hacchuuten");
        if ((hacchuutenstr == null) || (hacchuutenstr.length() == 0)) {
            errorlist.add("発注点を入力してください");
            result = RC_INPUTERROR;
        } else {
            try {
                hacchuuten = Integer.parseInt(hacchuutenstr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("発注点が不正です");
                result = RC_INPUTERROR;
            }
        }
        String hacchuutanistr = getInput("#hacchuutani");
        if ((hacchuutanistr == null) || (hacchuutanistr.length() == 0)) {
            errorlist.add("発注単位を選択してください");
            result = RC_INPUTERROR;
        } else {
            try {
                hacchuutani = Integer.parseInt(hacchuutanistr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("発注単位が不正です");
                result = RC_INPUTERROR;
            }
        }
        String irisuustr = getInput("#irisuu");
        if ((irisuustr == null) || (irisuustr.length() == 0)) {
            errorlist.add("入り数を入力してください");
            result = RC_INPUTERROR;
        } else {
            try {
                //irisuu = Integer.parseInt(irisuustr);
                irisuu = Float.parseFloat(irisuustr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("入り数が不正です");
                result = RC_INPUTERROR;
            }
        }
        String hacchuutanisuustr = getInput("#hacchuutanisuu");
        if ((hacchuutanisuustr == null) || (hacchuutanisuustr.length() == 0)) {
            errorlist.add("発注単位数を入力してください");
            result = RC_INPUTERROR;
        } else {
            try {
                hacchuutanisuu = Integer.parseInt(hacchuutanisuustr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("発注単位数が不正です");
                result = RC_INPUTERROR;
            }
        }
        String saiteihacchuusuustr = getInput("#saiteihacchuusuu");
        if ((saiteihacchuusuustr == null) ||
          (saiteihacchuusuustr.length() == 0)) {
            errorlist.add("最低発注数を入力してください");
            result = RC_INPUTERROR;
        } else {
            try {
                saiteihacchuusuu = Integer.parseInt(saiteihacchuusuustr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("最低発注数が不正です");
                result = RC_INPUTERROR;
            }
        }
        String tankastr = getInput("#tanka");
        if ((tankastr == null) || (tankastr.length() == 0)) {
            errorlist.add("単価を入力してください");
            result = RC_INPUTERROR;
        } else {
            try {
                tanka = Float.parseFloat(tankastr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("単価が不正です");
                result = RC_INPUTERROR;
            }
        }
        String hyoujuntankastr = getInput("#hyoujuntanka");
        if (hyoujuntankastr != null) {
            try {
                hyoujuntanka = Float.parseFloat(hyoujuntankastr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("標準単価が不正です");
                result = RC_INPUTERROR;
            }
        }
        String shiireleadtimestr = getInput("#shiireleadtime");
        if ((shiireleadtimestr == null) || (shiireleadtimestr.length() == 0)) {
            errorlist.add("仕入リードタイムを入力してください");
            result = RC_INPUTERROR;
        } else {
            try {
                shiireleadtime = Integer.parseInt(shiireleadtimestr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("仕入リードタイムが不正です");
                result = RC_INPUTERROR;
            }
        }
        String ondotaistr = getInput("#ondotai");
        if ((ondotaistr == null) || (ondotaistr.length() == 0)) {
            errorlist.add("温度帯を選択してください");
            result = RC_INPUTERROR;
        } else {
            try {
                ondotai = Integer.parseInt(ondotaistr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("温度帯が不正です");
                result = RC_INPUTERROR;
            }
        }
        String shoumikigen_flgstr = getInput("#shoumikigen_flg");
        if (shoumikigen_flgstr != null) {
            try {
                shoumikigen_flg = Integer.parseInt(shoumikigen_flgstr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("賞味期限フラグが不正です");
                result = RC_INPUTERROR;
            }
        }
        if (shoumikigen_flg == Names.ON) {
            String shoumikigennissuustr =
               getInput("#shoumikigennissuu");
            if ((shoumikigennissuustr == null) ||
              (shoumikigennissuustr.length() == 0)) {
                errorlist.add("賞味期限日数を入力してください");
                result = RC_INPUTERROR;
            } else {
                try {
                    shoumikigennissuu = Integer.parseInt(shoumikigennissuustr);
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add("賞味期限日数が不正です");
                    result = RC_INPUTERROR;
                }
            }
            String shukkakigennissuustr =
               getInput("#shukkakigennissuu");
            try {
                shukkakigennissuu = Integer.parseInt(shukkakigennissuustr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("出荷期限日数が不正です");
                result = RC_INPUTERROR;
            }
        }
        String kobetsuhacchuu_flgstr =
          getInput("#kobetsuhacchuu_flg");
        if (kobetsuhacchuu_flgstr != null) {
            try {
                kobetsuhacchuu_flg = Integer.parseInt(kobetsuhacchuu_flgstr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("個別発注フラグが不正です");
                result = RC_INPUTERROR;
            }
        }
        String youchuuihin_flgstr = getInput("#youchuuihin_flg");
        if (youchuuihin_flgstr != null) {
            try {
                youchuuihin_flg = Integer.parseInt(youchuuihin_flgstr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("要注意区分が不正です");
                result = RC_INPUTERROR;
            }
        }
        String youraberu_flgstr = getInput("#youraberu_flg");
        if (youraberu_flgstr != null) {
            try {
                youraberu_flg = Integer.parseInt(youraberu_flgstr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("要ラベルフラグが不正です");
                result = RC_INPUTERROR;
            }
        }
        daibunrui = getInput("#daibunrui");
        pcode = getInput("#pcode");
        String zaikohyoukahouhoustr =
          getInput("#zaikohyoukahouhou");
        if ((zaikohyoukahouhoustr == null) ||
          (zaikohyoukahouhoustr.length() == 0)) {
            errorlist.add("在庫評価方法を選択してください");
            result = RC_INPUTERROR;
        } else {
            try {
                zaikohyoukahouhou = Integer.parseInt(zaikohyoukahouhoustr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("在庫評価方法が不正です");
                result = RC_INPUTERROR;
            }
        }
        jancode = getInput("#jancode");
        kataban = getInput("#kataban");
        location_id1 = getInput("#location_id1");
        location_id2 = getInput("#location_id2");
        location_id3 = getInput("#location_id3");
        String shuubai_flgstr = getInput("#shuubai_flg");
        if (shuubai_flgstr != null) {
            try {
                shuubai_flg = Integer.parseInt(shuubai_flgstr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("終売フラグが不正です");
                result = RC_INPUTERROR;
            }
        }

        //added 2003/07/24
        String hyoujunbaikastr = getInput("#hyoujunbaika");
        if (hyoujunbaikastr != null) {
            try {
                hyoujunbaika = Float.parseFloat(hyoujunbaikastr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("標準売価が不正です");
                result = RC_INPUTERROR;
            }
        }
        nisugata = getInput("#nisugata");
        hacchuucomment = getInput("#hacchuucomment");
        String hanbaikubunstr = getInput("#hanbaikubun");
        if (hanbaikubunstr != null) {
            try {
                hanbaikubun = Integer.parseInt(hanbaikubunstr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("販売区分が不正です");
                result = RC_INPUTERROR;
            }
        }
        shubetsu = getInput("#shubetsu");
        String mochikoshi_flgstr = getInput("#mochikoshi_flg");
        if (mochikoshi_flgstr != null) {
            try {
                mochikoshi_flg = Integer.parseInt(mochikoshi_flgstr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("持ち越しフラグが不正です");
                result = RC_INPUTERROR;
            }
        }
        String shinyanouhin_flgstr = getInput("#shinyanouhin_flg");
        if (shinyanouhin_flgstr != null) {
            try {
                shinyanouhin_flg = Integer.parseInt(shinyanouhin_flgstr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("深夜納品フラグが不正です");
                result = RC_INPUTERROR;
            }
        }

        UserLocal user = (UserLocal)session.getAttribute("USER");
        user_id = user.getUser_id();
    }

    public void handleEvent(HashMap attr) {
        if (result == RC_INPUTERROR) { return; }
        java.util.Date now = new java.util.Date();
        try {
            ShouhinLocalHome shome = (ShouhinLocalHome)
              ServiceLocator.getLocalHome("java:comp/env/ejb/ShouhinLocal");
            ShouhinLocal shouhinl = shome.create(
              shouhin_id,
              shouhin,
              shouhinfurigana,
              shiiresaki_id,
              hacchuushouhin1,
              hacchuushouhin2,
              hacchuushouhin3,
              kikaku,
              tani,
              hacchuukikaku,
              hacchuuten,
              hacchuutani,
              irisuu,
              hacchuutanisuu,
              saiteihacchuusuu,
              tanka,
              hyoujuntanka,
              shiireleadtime,
              ondotai,
              shoumikigen_flg,
              shoumikigennissuu,
              shukkakigennissuu,
              kobetsuhacchuu_flg,
              youchuuihin_flg,
              youraberu_flg,
              daibunrui,
              pcode,
              zaikohyoukahouhou,
              jancode,
              kataban,
              location_id1,
              location_id2,
              location_id3,
              shuubai_flg,
              user_id,
              hyoujunbaika,
              nisugata,
              hacchuucomment,
              0,
              hanbaikubun,
              shubetsu,
              mochikoshi_flg,
              shinyanouhin_flg
            );
        } catch (NamingException ne) {
            Debug.println(ne);
            result = RC_INPUTERROR;
            errorlist.add("システムエラー・NamingException");
            setRollbackOnly();
            return;
        } catch (DuplicateKeyException de) {
            Debug.println(de);
            result = RC_INPUTERROR;
            errorlist.add("商品コードが重複しています");
            setRollbackOnly();
            return;
        } catch (CreateException ce) {
            String message = ce.getMessage();
            System.out.println("message:"+message);
            //Oracle依存なエラー制御
            if (message.indexOf("ORA-01401") > 0) {
                errorlist.add("入力値が長すぎます。");
            } else {
                ce.printStackTrace();
                errorlist.add("システムエラー・CreateException");
            }
            result = RC_INPUTERROR;
            setRollbackOnly();
            return;
        }
    }

    public void postHandle(HttpServletRequest request) {
    }
}
