package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.TransactionEvent;
import com.oisix.oisystemzs.Names;
import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemzs.ejb.NyuukoLocal;
import com.oisix.oisystemzs.ejb.NyuukoLocalHome;
import com.oisix.oisystemzs.ejb.NyuukoData;
import com.oisix.oisystemzs.ejb.NyuukayoteimeisaiLocal;
import com.oisix.oisystemzs.ejb.NyuukayoteimeisaiLocalHome;
import com.oisix.oisystemzs.ejb.HacchuuLocal;
import com.oisix.oisystemzs.ejb.HacchuuLocalHome;
import com.oisix.oisystemzs.ejb.ShouhinLocal;
import com.oisix.oisystemzs.ejb.ShouhinLocalHome;
import com.oisix.oisystemzs.ejb.TaniLocal;
import com.oisix.oisystemzs.ejb.TaniLocalHome;
import com.oisix.oisystemzs.ejb.TaniPK;
import com.oisix.oisystemzs.ejb.UserLocal;
import com.oisix.oisystemzs.ejb.SoukoData;
import com.oisix.oisystemzs.ejb.exception.UkeireException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.naming.NamingException;
import javax.ejb.FinderException;
import javax.ejb.CreateException;

public class DonyuukoinputEvent extends TransactionEvent {

    private Collection nyuukoList = new LinkedList();
    private Collection resultNyuukoList = new LinkedList();
    private java.util.Date nyuuko_date;

    public Collection getResultNyuukoList() {
        return resultNyuukoList;
    }

    private int user_id;
    private String souko_id;

    public void init(HttpServletRequest request) {
        // 入庫日の取得
        int nyuukoyear = 0;
        int nyuukomonth = 0;
        int nyuukoday = 0;
        String nyuukoyearstr = getInput("#nyuukoyear");
        String nyuukomonthstr = getInput("#nyuukomonth");
        String nyuukodatestr = getInput("#nyuukodate");
        try {
            nyuukoyear = Integer.parseInt(nyuukoyearstr);
            nyuukomonth = Integer.parseInt(nyuukomonthstr);
            nyuukoday = Integer.parseInt(nyuukodatestr);
            Calendar cal = Calendar.getInstance();
            cal.clear();
            cal.set(nyuukoyear, nyuukomonth - 1, nyuukoday);
            nyuuko_date = cal.getTime();
        } catch (NumberFormatException nfe) {
            Debug.println(nfe);
            errorlist.add("入庫日が不正です。");
            result = RC_INPUTERROR;
        }
        for (int i = 0; i < 10; i++) {
            String strInd = String.valueOf(i);
            String shouhin_id = getInput("#shouhin_id" + strInd);
            // 商品IDが取得できた列のみ処理
            if (shouhin_id != null && !shouhin_id.equals("")) {
                HashMap nyuukoMap = new HashMap();
                nyuukoMap.put("shouhin_id", shouhin_id);
                // 発注番号の取得
                String hacchuu_bg = getInput("#hacchuu_bg"+strInd);
                nyuukoMap.put("hacchuu_bg", hacchuu_bg);
                //仕入先コードの取得
                String shiiresaki_id = getInput("#shiiresaki_id"+strInd);
                if (shiiresaki_id != null && !shiiresaki_id.equals("")) {
                    try {
                        int shiireint = Integer.parseInt(shiiresaki_id);
                    } catch (NumberFormatException nfe) {
                        Debug.println(nfe);
                        errorlist.add("仕入先コードが不正です。");
                        result = RC_INPUTERROR;
                        continue;
                    }
                }
                nyuukoMap.put("shiiresaki_id", shiiresaki_id);
                // 賞味期限の取得
                int shoumiyear = 0;
                int shoumimonth = 0;
                int shoumiday = 0;
                String shoumiyearstr =
                  getInput("#shoumiyear" + strInd);
                String shoumimonthstr =
                  getInput("#shoumimonth" + strInd);
                String shoumidatestr =
                  getInput("#shoumidate" + strInd);
                try {
                    shoumiyear = Integer.parseInt(shoumiyearstr);
                    shoumimonth = Integer.parseInt(shoumimonthstr);
                    shoumiday = Integer.parseInt(shoumidatestr);
                    Calendar shoumical = Calendar.getInstance();
                    shoumical.clear();
                    shoumical.set(shoumiyear, shoumimonth - 1, shoumiday);
                    java.util.Date shoumikigen = shoumical.getTime();
                    nyuukoMap.put("shoumikigen", shoumikigen);
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add("賞味期限が不正です。");
                    result = RC_INPUTERROR;
                    continue;
                }
                
                // 出荷期限の取得
                int shukkayear = 0;
                int shukkamonth = 0;
                int shukkaday = 0;
                String shukkayearstr =
                  getInput("#shukkayear" + strInd);
                String shukkamonthstr =
                  getInput("#shukkamonth" + strInd);
                String shukkadatestr =
                  getInput("#shukkadate" + strInd);
                try {
                    shukkayear = Integer.parseInt(shukkayearstr);
                    shukkamonth = Integer.parseInt(shukkamonthstr);
                    shukkaday = Integer.parseInt(shukkadatestr);
                    Calendar shukkacal = Calendar.getInstance();
                    shukkacal.clear();
                    shukkacal.set(shukkayear, shukkamonth - 1, shukkaday);
                    java.util.Date shukkakigen = shukkacal.getTime();
                    nyuukoMap.put("shukkakigen", shukkakigen);
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add("出荷期限が不正です。");
                    result = RC_INPUTERROR;
                    continue;
                }
                
                // 入庫数量の取得
                String nyuukosuuryoustr = getInput("#nyuukosuuryou" + strInd);
                Float nyuukosuuryou = null;
                if (nyuukosuuryoustr == null || nyuukosuuryoustr.equals("")) {
                    errorlist.add("商品コード" +
                      shouhin_id + "の入庫数量を入力して下さい。");
                    result = RC_INPUTERROR;
                    continue;
                }
                try {
                    nyuukosuuryou = new Float(nyuukosuuryoustr);
                    nyuukoMap.put("nyuukosuuryou", nyuukosuuryou);
                } catch (NullPointerException npe) {
                    errorlist.add("商品コード" +
                      shouhin_id + "の入庫数量を入力してください。");
                    result = RC_INPUTERROR;
                    continue;
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add("入庫数量が不正です。");
                    result = RC_INPUTERROR;
                    continue;
                }
                // 入庫単位の取得
                String nyuukotani = getInput("#nyuukotani" + strInd);
                nyuukoMap.put("nyuukotani", nyuukotani);
                // 仕入数量の取得
                String shiiresuuryoustr = getInput("#shiiresuuryou" + strInd);
                Float shiiresuuryou = null;
                if (shiiresuuryoustr == null || shiiresuuryoustr.equals("")) {
                    errorlist.add("商品コード" + 
                      shouhin_id + "の仕入数量を入力して下さい。");
                    result = RC_INPUTERROR;
                    continue;
                }
                try {
                    shiiresuuryou = new Float(shiiresuuryoustr);
                    nyuukoMap.put("shiiresuuryou", shiiresuuryou);
                } catch (NullPointerException npe) {
                    errorlist.add("商品コード" +
                      shouhin_id + "の仕入数量を入力してください。");
                    result = RC_INPUTERROR;
                    continue;
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add("仕入数量が不正です。");
                    result = RC_INPUTERROR;
                    continue;
                }
                // 仕入単位の取得
                String shiiretani = getInput("#shiiretani" + strInd);
                nyuukoMap.put("shiiretani", shiiretani);
                // 仕入単価の取得
                String tankastr = getInput("#shiiretanka" + strInd);
                Float shiiretanka = null;
                try {
                    shiiretanka = new Float(tankastr);
                    nyuukoMap.put("shiiretanka", shiiretanka);
                } catch (NullPointerException npe) {
                    errorlist.add("単価を入力してください。");
                    result = RC_INPUTERROR;
                    continue;
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add("単価が不正です。");
                    result = RC_INPUTERROR;
                    continue;
                }
                // 入庫区分の取得
                String nyuukokubunstr = getInput("#nyuukokubun"+strInd);
                Integer nyuukokubun = null;
                if (nyuukokubunstr == null || nyuukokubunstr.equals("")) {
                    errorlist.add("商品コード" +
                      shouhin_id + "の入庫区分を選択して下さい");
                    result = RC_INPUTERROR;
                    continue;
                }
                try {
                    nyuukokubun = new Integer(nyuukokubunstr);
                    nyuukoMap.put("nyuukokubun", nyuukokubun);
                } catch (NullPointerException npe) {
                    errorlist.add("入庫区分を選択してください。");
                    result = RC_INPUTERROR;
                    continue;
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add("入庫区分を選択してください。");
                    result = RC_INPUTERROR;
                    continue;
                }
                //入庫単価の計算
                Float nyuukotanka = null;
                try{
                    float flshiiretanka = shiiretanka.floatValue();
                    float flshiiresuuryou = shiiresuuryou.floatValue();
                    float flnyuukosuuryou = nyuukosuuryou.floatValue();
                    float flnyuukotanka =
                      flshiiresuuryou * flshiiretanka / flnyuukosuuryou ;
                    nyuukotanka = new Float(flnyuukotanka);
                    nyuukoMap.put("nyuukotanka",nyuukotanka);
                } catch (NullPointerException npe) {
                    errorlist.add(
                      "仕入単価、仕入数量、入庫数量を選択してください。");
                    result = RC_INPUTERROR;
                    continue;
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add("仕入単価、仕入数量、入庫数量が不正です。");
                    result = RC_INPUTERROR;
                    continue;
                } catch(Exception e) {
                    errorlist.add("入庫単価計算が不正です。");
                    result = RC_INPUTERROR;
                    continue;
                }
                nyuukoList.add(nyuukoMap);
            }
        }

        if (nyuukoList.size() == 0) {
            errorlist.add("商品が１つも入力されていません。");
            result = RC_INPUTERROR;
        }

        // ユーザの取得
        UserLocal user = (UserLocal)session.getAttribute("USER");
        user_id = user.getUser_id();
        SoukoData souko = (SoukoData)session.getAttribute("SOUKO");
        souko_id = souko.getSouko_id();
    }

    public void handleEvent(HashMap attr) {
        if (result == RC_INPUTERROR) { return; }
        try {
            NyuukoLocalHome nklh = (NyuukoLocalHome)
              ServiceLocator.getLocalHome("java:comp/env/ejb/NyuukoLocal");
            NyuukayoteimeisaiLocalHome nymlh =
              (NyuukayoteimeisaiLocalHome)ServiceLocator.getLocalHome(
              "java:comp/env/ejb/NyuukayoteimeisaiLocal");
            HacchuuLocalHome hlh = (HacchuuLocalHome)
              ServiceLocator.getLocalHome("java:comp/env/ejb/HacchuuLocal");
            ShouhinLocalHome slh = (ShouhinLocalHome)
              ServiceLocator.getLocalHome("java:comp/env/ejb/ShouhinLocal");
            TaniLocalHome tlh = (TaniLocalHome)
              ServiceLocator.getLocalHome("java:comp/env/ejb/TaniLocal");
            Iterator iter = nyuukoList.iterator();
            while (iter.hasNext()) {
                HashMap nyuukoMap = (HashMap)iter.next();
                String shouhin_id = (String)nyuukoMap.get("shouhin_id");
                ShouhinLocal sl = slh.findByPrimaryKey(shouhin_id);
                java.util.Date shoumikigen = null;
                java.util.Date shukkakigen = null;
                //賞味期限管理の判定
                if (sl.getShoumikigen_flg() == Names.ON) {
                    shoumikigen = (java.util.Date)nyuukoMap.get("shoumikigen");
                    shukkakigen = (java.util.Date)nyuukoMap.get("shukkakigen");
                }
                String hacchuu_bg = (String)nyuukoMap.get("hacchuu_bg");
                int nyuukokubun =
                  ((Integer)nyuukoMap.get("nyuukokubun")).intValue();
                int nyuukayotei_id = 0;
                int nyuukajoukyou = 0;
                String shiiresaki_id = (String)nyuukoMap.get("shiiresaki_id");
                if (hacchuu_bg != null && !hacchuu_bg.equals("")) {
                    Collection nymcol =
                      nymlh.findByHacchuu_bgAndShouhin_id(
                      hacchuu_bg, shouhin_id);
                    Iterator nymiter = nymcol.iterator();
                    while (nymiter.hasNext()) {
                        NyuukayoteimeisaiLocal nyml =
                          (NyuukayoteimeisaiLocal)nymiter.next();
                        nyuukayotei_id = nyml.getNyuukayotei_id();
                        nyuukajoukyou = nyml.getNyuukajoukyou();
                        sl = nyml.getShouhin();
                    }
                    if (nymcol.isEmpty()) {
                        result = RC_INPUTERROR;
                        errorlist.add(
                          "発注番号「" + hacchuu_bg + "」の入荷予定に" +
                          "商品コード「" + shouhin_id + "」の商品は" +
                          "存在しません");
                        setRollbackOnly();
                        return;
                    }
                    //発注データの仕入先を取得
                    if (shiiresaki_id == null || shiiresaki_id.equals("")) {
                        HacchuuLocal hl =
                          (HacchuuLocal)hlh.findByHacchuu_bg(hacchuu_bg);
                        shiiresaki_id = hl.getShiiresaki_id();
                    }
                }
                //仕入先コードの取得
                if (shiiresaki_id == null || shiiresaki_id.equals("")) {
                    shiiresaki_id = sl.getShiiresaki_id();
                }
                //入庫単位の取得
                String nyuukotani = (String)nyuukoMap.get("nyuukotani");
                if (nyuukotani == null || nyuukotani.equals("")) {
                    int intnyuukotani = sl.getTani();
                    TaniPK npk = new TaniPK(intnyuukotani);
                    TaniLocal tl = tlh.findByPrimaryKey(npk);
                    nyuukotani = tl.getTani();
                }
                //仕入単位の取得
                String shiiretani = (String)nyuukoMap.get("shiiretani");
                if (shiiretani == null || shiiretani.equals("")) {
                    int intshiiretani = sl.getHacchuutani();
                    TaniPK spk = new TaniPK(intshiiretani);
                    TaniLocal tl = tlh.findByPrimaryKey(spk);
                    shiiretani = tl.getTani();
                } 
                NyuukoLocal newnyuuko = nklh.create(
                  nyuuko_date,
                  souko_id,
                  shouhin_id,
                  ((Float)nyuukoMap.get("shiiresuuryou")).floatValue(),
                  shiiretani,
                  ((Float)nyuukoMap.get("shiiretanka")).floatValue(),
                  ((Float)nyuukoMap.get("nyuukosuuryou")).floatValue(),
                  nyuukotani,
                  ((Float)nyuukoMap.get("nyuukotanka")).floatValue(),
                  shoumikigen,
                  shukkakigen,
                  nyuukokubun,
                  shiiresaki_id,
                  null,
                  nyuukayotei_id,
                  Names.OFF,
                  Names.NONE_ID,
                  user_id
                );
                // 結果表示用のデータ作成
                HashMap item = new HashMap();
                item.put("nyuuko_id", new Integer(newnyuuko.getNyuuko_id()));
                item.put("nyuuko_date",newnyuuko.getNyuuko_date());
                item.put("nyuuko_bg", newnyuuko.getNyuuko_bg());
                item.put("shouhin_id", newnyuuko.getShouhin_id());
                item.put("shouhinmei", newnyuuko.getShouhin().getShouhin());
                item.put("nyuukosuuryou",
                  new Float(newnyuuko.getNyuukosuuryou()));
                resultNyuukoList.add(item);
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
        } catch (UkeireException he) {
            Debug.println(he);
            result = RC_INPUTERROR;
            errorlist.add("システムエラー・UkeireException");
            setRollbackOnly();
            return;
        }
    }
}
