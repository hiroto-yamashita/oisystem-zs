package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.TransactionEvent;
import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemzs.Names;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.oisix.oisystemzs.ejb.ShukkoPK;
import com.oisix.oisystemzs.ejb.ShukkoLocal;
import com.oisix.oisystemzs.ejb.ShukkoLocalHome;
import com.oisix.oisystemzs.ejb.ShukkoData;
import com.oisix.oisystemzs.ejb.ShukkayoteimeisaiLocal;
import com.oisix.oisystemzs.ejb.ShukkayoteimeisaiLocalHome;
import com.oisix.oisystemzs.ejb.ShouhinLocal;
import com.oisix.oisystemzs.ejb.ShouhinLocalHome;
import com.oisix.oisystemzs.ejb.UserLocal;
import com.oisix.oisystemzs.ejb.SoukoData;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.naming.NamingException;
import javax.ejb.FinderException;
import javax.ejb.CreateException;
import com.oisix.oisystemzs.ejb.exception.HaraidashiException;

public class DoshukkoinputEvent extends TransactionEvent {

    private LinkedList shukkoList = new LinkedList();
    private LinkedList resultShukkoList = new LinkedList();
    private java.util.Date shukko_date;
    private LinkedList checklist = new LinkedList();
    public LinkedList getChecklist() { return checklist; }

    private final int HYOUKA_IDOUHEIKINTANKA = 1;
    private final int HYOUKA_HYOUJUNTANKA = 2;

    private int user_id;
    private String souko_id;

    public void init(HttpServletRequest request) {
        // ユーザの取得
        UserLocal user = (UserLocal)session.getAttribute("USER");
        user_id = user.getUser_id();
        SoukoData souko = (SoukoData)session.getAttribute("SOUKO");
        souko_id = souko.getSouko_id();

        // 出庫日の取得
        int year = 0;
        int month = 0;
        int day = 0;
        String shukko_yearstr = getInput("#shukko_year");
        String shukko_monthstr = getInput("#shukko_month");
        String shukko_datestr = getInput("#shukko_date");
        try {
            year = Integer.parseInt(shukko_yearstr);
            month = Integer.parseInt(shukko_monthstr);
            day = Integer.parseInt(shukko_datestr);
            Calendar cal = Calendar.getInstance();
            cal.clear();
            cal.set(year, month - 1, day);
            shukko_date = cal.getTime();
        } catch (NumberFormatException nfe) {
            Debug.println(nfe);
            errorlist.add("出庫日が不正です。");
            result = RC_INPUTERROR;
        }

        for (int i = 0; i < 10; i++) {
            String strInd = String.valueOf(i);
            String shouhin_id = getInput("#shouhin_id"+strInd);
            // 商品IDが取得できた列のみ処理
            ShouhinLocalHome slh;
            try {
                slh = (ShouhinLocalHome)ServiceLocator.
                  getLocalHome("java:comp/env/ejb/ShouhinLocal");
            } catch (NamingException ne) {
                Debug.println(ne);
                result = RC_INPUTERROR;
                errorlist.add("システムエラー・NamingException");
                setRollbackOnly();
                return;
            }
            if (shouhin_id != null && !shouhin_id.equals("")) {
                ShouhinLocal shouhin;
                try {
                    shouhin = slh.findByPrimaryKey(shouhin_id);
                } catch (FinderException fe) {
                    Debug.println(fe);
                    errorlist.add(shouhin_id + "：商品コードが不正です。");
                    result = RC_INPUTERROR;
                    continue;
                }
                HashMap shukkoMap = new HashMap();
                shukkoMap.put("shouhin_id", shouhin_id);

                // 単位の取得（未入力時は商品データから）
                String tani = getInput("#tani" + strInd);
                if (tani == null || tani.equals("")) {
                    try {
                        tani = shouhin.getTaniLocal().getTani();
                        setInput("#tani" + strInd, tani);
                        shukkoMap.put("tani", tani);
                    } catch (NamingException ne) {
                        Debug.println(ne);
                        errorlist.add("SystemError/商品コード" +
                          shouhin_id + "の単位が取得できません。");
                        result = RC_INPUTERROR;
                        continue;
                    } catch (FinderException fe) {
                        Debug.println(fe);
                        errorlist.add("SystemError/商品コード" + shouhin_id +
                          "の単位が商品データから取得できません。");
                        result = RC_INPUTERROR;
                        continue;
                    }
                } else {
                    shukkoMap.put("tani", tani);
                }

                session.setAttribute
                  ("shouhinmei" + strInd, shouhin.getShouhin());
                session.setAttribute
                  ("kikaku" + strInd, shouhin.getKikaku());

                // 出荷予定番号の取得
                String shukkayotei_bg = getInput("#shukkayotei_bg"+strInd);
                shukkoMap.put("shukkayotei_bg", shukkayotei_bg);

                // 数量の取得（入力必須）
                String suuryoustr = getInput("#suuryou" + strInd);
                if (suuryoustr == null || suuryoustr.equals("")) {
                    errorlist.add("商品コード" +
                      shouhin_id + "の数量を入力してください。");
                    result = RC_INPUTERROR;
                    continue;
                }
                Float suuryou = null;
                try {
                    suuryou = new Float(suuryoustr);
                    shukkoMap.put("suuryou", suuryou);
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add("商品コード" +
                      shouhin_id + "の数量が不正です。");
                    result = RC_INPUTERROR;
                    continue;
                }

                // 出庫区分の取得（選択必須）
                String shukkokubunstr = getInput("#shukkokubun"+strInd);
                if (shukkokubunstr == null || shukkokubunstr.equals("")) {
                    errorlist.add("商品コード" +
                      shouhin_id + "の出庫区分を選択してください。");
                    result = RC_INPUTERROR;
                    continue;
                }
                try {
                    Integer shukkokubun = new Integer(shukkokubunstr);
                    shukkoMap.put("shukkokubun", shukkokubun);
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add("SystemError/商品コード" +
                      shouhin_id + "の出庫区分が不正です。");
                    result = RC_INPUTERROR;
                    continue;
                }

                Connection con = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                // 賞味期限の取得（未入力時は在庫明細データから）
                String shoumikigenstr = getInput("#shoumikigen" + strInd);
                java.util.Date shoumikigen = null;
                int shoumikigen_flg = shouhin.getShoumikigen_flg();
                if (shoumikigen_flg == Names.NONE_ID) {
                    shukkoMap.put("shoumikigen", null);
                } else {
                    if (shoumikigenstr != null && !shoumikigenstr.equals("")) {
                        try {
                            DateFormat df = DateFormat.getDateInstance();
                            shoumikigen = df.parse(shoumikigenstr);
                            shukkoMap.put("shoumikigen", shoumikigen);
                        } catch (ParseException pe) {
                            Debug.println(pe);
                            errorlist.add("商品コード" + shouhin_id +
                              "の賞味期限が不正です。");
                            result = RC_INPUTERROR;
                            continue;
                        }
                    }
                    // 出庫日における在庫明細データの取得
                    try {
                        DataSource ds = ServiceLocator.getDataSource();
                        con = ds.getConnection();
                        String sql = makeZaikomeisaiSql(shouhin_id);
                        ps = con.prepareStatement(sql);
                        rs = ps.executeQuery();
                        if (rs == null) {
                            Debug.println("rs null", this);
                            errorlist.add("SystemError/ResultSet is null");
                            result = RC_INPUTERROR;
                            continue;
                        }
                        float zaikosuuryou = 0;
                        // 賞味期限未入力時は出荷可能最古在庫を取得
                        if (shoumikigen == null) {
                            if (rs.next()) {
                                int index = 1;
                                shoumikigen = rs.getDate(index++);
                                zaikosuuryou = rs.getFloat(index++);
                                SimpleDateFormat sdf = new
                                  SimpleDateFormat("yyyy/MM/dd");
                                setInput("#shoumikigen" + strInd,
                                  sdf.format(shoumikigen));
                            } else {
                                errorlist.add("商品コード" + shouhin_id +
                                  "の賞味期限が取得できません。");
                                result = RC_INPUTERROR;
                                continue;
                            }
                            checklist.add("商品コード" + shouhin_id +
                              "の賞味期限を入力しました。");
                            result = RC_INPUTERROR;
                        } else {
                            boolean findmeisai = false;
                            while (rs.next()) {
                                int index = 1;
                                java.util.Date zaikoshoumikigen = 
                                  rs.getDate(index++);
                                zaikosuuryou = rs.getFloat(index++);
                                if (zaikoshoumikigen.equals(shoumikigen)) {
                                    findmeisai = true;
                                    break;
                                }
                            }
                            if (!findmeisai) {
                                SimpleDateFormat sdf = new
                                  SimpleDateFormat("yyyy/MM/dd");
                                errorlist.add("商品コード" + shouhin_id +
                                  "、賞味期限" + sdf.format(shoumikigen) +
                                  "の在庫データがありません。");
                                result = RC_INPUTERROR;
                                continue;
                            }
                        }
                        float fsuuryou = suuryou.floatValue();
                        if (zaikosuuryou < fsuuryou) {
                            suuryou = new Float(zaikosuuryou);
                            setInput("#suuryou" + strInd,
                              suuryou.toString());
                            checklist.add("在庫不足のため、商品コード" +
                              shouhin_id + "の数量を修正しました。");
                            result = RC_INPUTERROR;
                        }
                    } catch (NamingException nme) {
                        Debug.println(nme);
                        errorlist.add("SystemError/NamingException");
                        result = RC_INPUTERROR;
                        continue;
                    } catch (SQLException sqle) {
                        Debug.println(sqle);
                        errorlist.add("SystemError/SQLException");
                        result = RC_INPUTERROR;
                        continue;
                    } finally {
                        try {
                            if (rs != null) { rs.close(); }
                            if (ps != null) { ps.close(); }
                            if (con != null) { con.close(); }
                        }
                        catch (SQLException sqle) { Debug.println(sqle); }
                    }
                }

                Float tanka = null;
                float zaikokingaku = 0;
                float zaikotanka = 0;
                float zaikosuuryou = 0;
                float suuryouflt = 0;
                // 現在庫の単価/金額/数量から、入力されるべき単価/金額の取得
                try {
                    DataSource ds = ServiceLocator.getDataSource();
                    con = ds.getConnection();

                    String sql = makeZaikoSql(shouhin_id);
                    ps = con.prepareStatement(sql);
                    rs = ps.executeQuery();
                    if (rs == null) {
                        Debug.println("rs null", this);
                        errorlist.add("SystemError/ResultSet is null");
                        result = RC_INPUTERROR;
                        continue;
                    }
                    if (rs.next()) {
                        int index = 1;
                        zaikotanka = rs.getFloat(index++);
                        zaikokingaku = rs.getFloat(index++);
                        zaikosuuryou = rs.getFloat(index++);
                    } else {
                        errorlist.add("商品コード" + shouhin_id +
                          "の在庫情報が取得できません。");
                        result = RC_INPUTERROR;
                        continue;
                    }
                    int hyoukahou = shouhin.getZaikohyoukahouhou();
                    if (hyoukahou == HYOUKA_IDOUHEIKINTANKA) {
                        tanka = new Float(zaikotanka);
                    } else if (hyoukahou == HYOUKA_IDOUHEIKINTANKA) {
                        tanka = new Float(shouhin.getHyoujuntanka());
                    }
                    suuryouflt = suuryou.floatValue();
                } catch (NamingException nme) {
                    Debug.println(nme);
                    errorlist.add("SystemError/NamingException");
                    result = RC_INPUTERROR;
                    continue;
                } catch (SQLException sqle) {
                    Debug.println(sqle);
                    errorlist.add("SystemError/SQLException");
                    result = RC_INPUTERROR;
                    continue;
                } finally {
                    try {
                        if (rs != null) { rs.close(); }
                        if (ps != null) { ps.close(); }
                        if (con != null) { con.close(); }
                    }
                    catch (SQLException sqle) { Debug.println(sqle); }
                }

                String tankastr = getInput("#tanka" + strInd);
                // 未入力時は在庫データor商品データから取得
                if (tankastr == null || tankastr.equals("")) {
                    checklist.add("商品コード" + shouhin_id +
                      "の単価を入力しました。");
                    result = RC_INPUTERROR;
                    setInput("#tanka" + strInd, tanka.toString());
                } else {
                    try {
                        tanka = new Float(tankastr);
                        shukkoMap.put("tanka", tanka);
                    } catch (NumberFormatException nfe) {
                        Debug.println(nfe);
                        errorlist.add("商品コード" + shouhin_id +
                          "の単価が不正です。");
                        result = RC_INPUTERROR;
                        continue;
                    }
                }

                Float kingaku = null;
                // 金額の取得（未入力時は数量×単価）
                String kingakustr = getInput("#kingaku"+strInd);
                if (kingakustr == null || kingakustr.equals("")) {
                    if (tanka != null && suuryou != null) {
                        kingaku = new Float(
                          tanka.floatValue() * suuryou.floatValue());
                        checklist.add("商品コード" + shouhin_id +
                          "の金額を入力しました。");
                        result = RC_INPUTERROR;
                        setInput("#kingaku" + strInd, kingaku.toString());
                    }
                } else {
                    try {
                        kingaku = new Float(kingakustr);
                        shukkoMap.put("kingaku", kingaku);
                    } catch (NumberFormatException nfe) {
                        Debug.println(nfe);
                        errorlist.add("商品コード" + shouhin_id +
                          "の金額が不正です。");
                        result = RC_INPUTERROR;
                        continue;
                    }
                }

                // 在庫数量と出庫数量が同じで金額が在庫金額と違う場合
                if (zaikosuuryou == suuryouflt) {
                    if (kingaku.floatValue() != zaikokingaku) {
                        kingaku = new Float(zaikokingaku);
                        setInput("#kingaku" + strInd, kingaku.toString());
                        checklist.add("在庫数量が0になるため、商品コード" +
                          shouhin_id + "の金額を修正しました。");
                        result = RC_INPUTERROR;
                    }
                }


                // 出庫入力リストへ追加
                shukkoList.add(shukkoMap);
            }
        }

        if (shukkoList.size() == 0) {
            errorlist.add("商品が１つも入力されていません。");
            result = RC_INPUTERROR;
        }
    }

    private void setInput(String key, String value) {
        HashMap inputval = (HashMap)session.getAttribute("INPUTVALUE");
        if (inputval == null) {
            return;
        }
        String[] values = (String[])inputval.get(key);
        if (values == null) {
            String[] newvalues = {value};
            inputval.put(key, newvalues);
        } else {
            values[0] = value;
        }
    }

    public void handleEvent(HashMap attr) {
        if (result == RC_INPUTERROR) { return; }
        java.util.Date now = new java.util.Date();
        try {
            ShukkoLocalHome sklh = (ShukkoLocalHome)
              ServiceLocator.getLocalHome("java:comp/env/ejb/ShukkoLocal");
            ShukkayoteimeisaiLocalHome symlh =
              (ShukkayoteimeisaiLocalHome)ServiceLocator.getLocalHome(
              "java:comp/env/ejb/ShukkayoteimeisaiLocal");
            Iterator iter = shukkoList.iterator();
            while (iter.hasNext()) {
                HashMap shukkoMap = (HashMap)iter.next();
                String shouhin_id = (String)shukkoMap.get("shouhin_id");
                String yotei_bg = (String)shukkoMap.get("shukkayotei_bg");
                int shukkayoteimeisai_id = 0;
                if (yotei_bg != null && !yotei_bg.equals("")) {
                    Collection symcol =
                      symlh.findByShukkayotei_bgAndShouhin_id(
                      yotei_bg, shouhin_id);
                    Iterator symiter = symcol.iterator();
                    if (!symiter.hasNext()) {
                        result = RC_INPUTERROR;
                        errorlist.add(
                          "出荷予定番号「" + yotei_bg + "」の出荷予定に" +
                          "商品コード「" + shouhin_id + "」の商品は" +
                          "存在しません");
                        setRollbackOnly();
                        return;
                    }
                    ShukkayoteimeisaiLocal syml =
                      (ShukkayoteimeisaiLocal)symiter.next();
                    shukkayoteimeisai_id = syml.getShukkayoteimeisai_id();
                }
                ShukkoLocal newshukko = sklh.create(
                  ((Integer)shukkoMap.get("shukkokubun")).intValue(),
                  null,
                  souko_id,
                  shouhin_id,
                  Names.DUMMY_LOCATION_ID,
                  shukko_date,
                  (java.util.Date)shukkoMap.get("shoumikigen"),
                  ((Float)shukkoMap.get("suuryou")).floatValue(),
                  (String)shukkoMap.get("tani"),
                  0, // 単価は自動計算
                  ((Float)shukkoMap.get("tanka")).floatValue(),
                  ((Float)shukkoMap.get("kingaku")).floatValue(),
                  shukkayoteimeisai_id,
                  Names.OFF,
                  Names.NONE_ID,
                  user_id,
                  0  // 標準売価
                );
                // 結果表示用のデータ作成
                HashMap item = new HashMap();
                item.put("shukko_id", new Integer(newshukko.getShukko_id()));
                item.put("shukko_bg", newshukko.getShukko_bg());
                item.put("shouhin_id", newshukko.getShouhin_id());
                item.put("shoumikigen", newshukko.getShoumikigen());
                item.put("shouhinmei", newshukko.getShouhin().getShouhin());
                item.put("suuryou", new Float(newshukko.getSuuryou()));
                resultShukkoList.add(item);
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
        } catch (CreateException ce) {
            Debug.println(ce);
            result = RC_INPUTERROR;
            errorlist.add("システムエラー・CreateException");
            setRollbackOnly();
            return;
        } catch (HaraidashiException he) {
            Debug.println(he);
            result = RC_INPUTERROR;
            errorlist.add("出庫処理時にエラーが発生しました。");
            setRollbackOnly();
            return;
        }
    }

    public void postHandle(HttpServletRequest request) {
        if (result == RC_INPUTERROR) { return; }
        HttpSession session = request.getSession();
        session.setAttribute("INPUTRESULTSHUKKOLIST", resultShukkoList);
    }

    public String makeZaikomeisaiSql(String shouhin_id) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(shukko_date);
        cal.add(Calendar.DATE, 1);
        String shukkobistr = sdf.format(cal.getTime());
        String sql =
          "SELECT " +
          "t2.SHOUMIKIGEN, " +
          "t2.SUURYOU " +
          "FROM ZT_ZAIKO t1, ZT_ZAIKOMEISAI t2 " +
          "WHERE t1.SHOUHIN_ID = '"+shouhin_id+"' " +
          "AND t1.SOUKO_ID = '"+souko_id+"' " +
          "AND t2.ZAIKO_ID = t1.ZAIKO_ID " +
          "AND t2.SUURYOU > 0 " +
          "AND t1.ZORDER = (" +
            "SELECT " +
            "MAX(t3.ZORDER) " +
            "FROM ZT_ZAIKO t3 " +
            "WHERE t3.SHOUHIN_ID = '"+shouhin_id+"' " +
            "AND t3.SOUKO_ID = '"+souko_id+"' " +
            "AND t3.ZAIKODATE<'"+shukkobistr+"' " +
            "GROUP BY t3.SHOUHIN_ID, t3.SOUKO_ID) " +
          "ORDER BY t2.SHUKKAKIGEN " +
          "";
        Debug.println(sql);
        return sql;
    }

    public String makeZaikoSql(String shouhin_id) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(shukko_date);
        cal.add(Calendar.DATE, 1);
        String shukkobistr = sdf.format(cal.getTime());
        String sql =
          "SELECT " +
          "t1.TANKA, " +
          "t1.KINGAKU, " +
          "t1.SUURYOU " +
          "FROM ZT_ZAIKO t1 " +
          "WHERE t1.SHOUHIN_ID = '"+shouhin_id+"' " +
          "AND t1.SOUKO_ID = '"+souko_id+"' " +
          "AND t1.ZORDER = (" +
            "SELECT " +
            "MAX(t2.ZORDER) " +
            "FROM ZT_ZAIKO t2 " +
            "WHERE t2.SHOUHIN_ID = '"+shouhin_id+"' " +
            "AND t2.SOUKO_ID = '"+souko_id+"' " +
            "AND t2.ZAIKODATE<'" + shukkobistr + "' " +
            "GROUP BY t2.SHOUHIN_ID, t2.SOUKO_ID) " +
          "";
        Debug.println(sql);
        return sql;
    }
}
