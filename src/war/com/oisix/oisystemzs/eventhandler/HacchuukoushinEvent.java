package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.TransactionEvent;
import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemzs.Names;
import com.oisix.oisystemzs.ejb.HacchuuPK;
import com.oisix.oisystemzs.ejb.HacchuuLocal;
import com.oisix.oisystemzs.ejb.HacchuuLocalHome;
import com.oisix.oisystemzs.ejb.NyuukayoteimeisaiPK;
import com.oisix.oisystemzs.ejb.NyuukayoteimeisaiLocal;
import com.oisix.oisystemzs.ejb.NyuukayoteimeisaiLocalHome;
import com.oisix.oisystemzs.ejb.ShouhinLocal;
import com.oisix.oisystemzs.ejb.ShouhinLocalHome;
import com.oisix.oisystemzs.ejb.SoukoData;
import com.oisix.oisystemzs.ejb.UserLocal;
import com.oisix.oisystemzs.objectmap.TaniMap;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.naming.NamingException;
import javax.ejb.CreateException;
import javax.ejb.FinderException;

public class HacchuukoushinEvent extends TransactionEvent {

    private LinkedList input = new LinkedList();
    private LinkedList meisaiList = new LinkedList();
    private int hacchuu_id;
    private java.util.Date hacchuu_date;
    private String  shiiresaki_id;
    private String  nouhinsaki_id;
    private int format;
    private int user_id;
    private String souko_id;

    public void init(HttpServletRequest request) {
        String hacchuu_idstr = request.getParameter("hacchuu_id");
        try {
            hacchuu_id = Integer.parseInt(hacchuu_idstr);
        } catch (NumberFormatException nfe) {
            Debug.println(nfe);
            errorlist.add("HACCHUU_IDが不正です。");
            result = RC_INPUTERROR;
            return;
        }
        String yearstr = request.getParameter("year");
        String monthstr = request.getParameter("month");
        String datestr = request.getParameter("date");
        int year = 0;
        int month = 0;
        int day = 0;
        try {
            year = Integer.parseInt(yearstr);
            month = Integer.parseInt(monthstr);
            day = Integer.parseInt(datestr);
        } catch (NumberFormatException nfe) {
            Debug.println(nfe);
            errorlist.add("発注日が不正です。");
            result = RC_INPUTERROR;
            return;
        }
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(year, month - 1, day);
        hacchuu_date = cal.getTime();

        shiiresaki_id = request.getParameter("shiiresaki_id");
        if ((shiiresaki_id == null) || (shiiresaki_id.equals(""))) {
            errorlist.add("仕入先コードを入力してください。");
            result = RC_INPUTERROR;
            return;
        }
        nouhinsaki_id = request.getParameter("nouhinsaki_id");
        if ((nouhinsaki_id == null) || (nouhinsaki_id.equals(""))) {
            errorlist.add("納品先コードを入力してください。");
            result = RC_INPUTERROR;
            return;
        }

        String formatstr = request.getParameter("hacchuusho");
        try {
            format = Integer.parseInt(formatstr);
        } catch (NumberFormatException nfe) {
            Debug.println(nfe);
            errorlist.add("発注書フォーマットが不正です。");
            result = RC_INPUTERROR;
            return;
        }

        Enumeration params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String key = (String)params.nextElement();
            if (key.startsWith("suuryou")) {
                HashMap inputline = new HashMap();
                //requestのキーは suuryou[nyuukayotei_id]の形
                String idstr = key.substring(7, key.length());
                Integer id = null;
                try {
                    id = new Integer(idstr);
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add("NYUUKAYOTEI_IDが不正です。");
                    result = RC_INPUTERROR;
                    return;
                }
                inputline.put("id", id);

                String suuryoustr = request.getParameter(key);
                Float suuryou = null;
                try {
                    suuryou = new Float(suuryoustr);
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add("数量が不正です。");
                    result = RC_INPUTERROR;
                    return;
                }
                inputline.put("suuryou", suuryou);

                String hacchuutani = request.getParameter(
                  "hacchuutani" + idstr);
                inputline.put("hacchuutani", hacchuutani);

                String tankastr = request.getParameter("tanka" + idstr);
                Float tanka = null;
                try {
                    tanka = new Float(tankastr);
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add("単価が不正です。");
                    result = RC_INPUTERROR;
                    return;
                }
                inputline.put("hacchuutanka", tanka);

                String nyuukasuuryoustr = request.getParameter(
                  "nyuukasuuryou" + idstr);
                Float nyuukasuuryou = null;
                try {
                    nyuukasuuryou = new Float(nyuukasuuryoustr);
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add("入荷数量が不正です。");
                    result = RC_INPUTERROR;
                    return;
                }
                inputline.put("nyuukasuuryou", nyuukasuuryou);

                String nyuukatani = request.getParameter(
                  "nyuukatani" + idstr);
                inputline.put("nyuukatani", nyuukatani);

                yearstr = request.getParameter("year" + idstr);
                monthstr = request.getParameter("month" + idstr);
                datestr = request.getParameter("date" + idstr);
                try {
                    year = Integer.parseInt(yearstr);
                    month = Integer.parseInt(monthstr);
                    day = Integer.parseInt(datestr);
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add("納品日が不正です。");
                    result = RC_INPUTERROR;
                    return;
                }
                cal.clear();
                cal.set(year, month - 1, day);
                java.util.Date date = cal.getTime();
                inputline.put("nyuukayotei_date", date);

                String touchakustr = request.getParameter("touchaku" + idstr);
                Integer touchaku = null;
                try {
                    touchaku = new Integer(touchakustr);
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add("到着時間が不正です。");
                    result = RC_INPUTERROR;
                    return;
                }
                inputline.put("touchaku", touchaku);

                String hacchuukubunstr = request.getParameter(
                  "hacchuukubun" + idstr);
                Integer hacchuukubun = null;
                try {
                    hacchuukubun = new Integer(hacchuukubunstr);
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add("発注区分が不正です。");
                    result = RC_INPUTERROR;
                    return;
                }
                inputline.put("hacchuukubun", hacchuukubun);

                String nyuukajoukyoustr = request.getParameter(
                  "nyuukajoukyou" + idstr);
                Integer nyuukajoukyou = null;
                try {
                    nyuukajoukyou = new Integer(nyuukajoukyoustr);
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add("入荷状況が不正です。");
                    result = RC_INPUTERROR;
                    return;
                }
                inputline.put("nyuukajoukyou", nyuukajoukyou);

                String statusstr = request.getParameter("status" + idstr);
                Integer status = null;
                try {
                    status = new Integer(statusstr);
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add("ステータスが不正です。");
                    result = RC_INPUTERROR;
                    return;
                }
                inputline.put("status", status);

                input.add(inputline);
            }
        }

        int ind = 0;
        while (getInput("#hacchuukubun" + ind) != null) {
            String shouhin_id = getInput("#shouhin_id" + ind);
            // 商品IDが取得できた列のみ処理
            if (shouhin_id != null && !shouhin_id.equals("")) {
                HashMap meisaiMap = new HashMap();
                meisaiMap.put("shouhin_id", shouhin_id);

                String suuryoustr = getInput("#suuryou" + ind);
                Float suuryou = null;
                try {
                    if (suuryoustr == null) { suuryoustr = "0"; }
                    suuryou = new Float(suuryoustr);
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add(shouhin_id + "の数量が不正です。");
                    result = RC_INPUTERROR;
                    ind++;
                    continue;
                }
                meisaiMap.put("suuryou", suuryou);

                String hacchuutanistr = getInput("#hacchuutani" + ind);
                String hacchuutani = null;
                if ((hacchuutanistr != null) && (!hacchuutanistr.equals(""))) {
                    try {
                        int tani_id = Integer.parseInt(hacchuutanistr);
                        hacchuutani = TaniMap.getTani(tani_id).getTani();
                    } catch (NumberFormatException nfe) {
                        Debug.println(nfe);
                        errorlist.add(shouhin_id + "の単位が不正です。");
                        result = RC_INPUTERROR;
                        ind++;
                        continue;
                    }
                }
                meisaiMap.put("hacchuutani", hacchuutani);

                String tankastr = getInput("#tanka" + ind);
                Float tanka = null;
                try {
                    tanka = new Float(tankastr);
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add(shouhin_id + "の単価が不正です。");
                    result = RC_INPUTERROR;
                    ind++;
                    continue;
                } catch (NullPointerException npe) {
                    Debug.println(npe);
                    errorlist.add(shouhin_id + "の単価が不正です。");
                    result = RC_INPUTERROR;
                    ind++;
                    continue;
                }
                meisaiMap.put("tanka", tanka);

                String nyuukasuuryoustr = getInput("#nyuukasuuryou" + ind);
                Float nyuukasuuryou = null;
                if ((nyuukasuuryoustr != null) &&
                  (!nyuukasuuryoustr.equals(""))) {
                    try {
                        nyuukasuuryou = new Float(nyuukasuuryoustr);
                    } catch (NumberFormatException nfe) {
                        Debug.println(nfe);
                        errorlist.add(shouhin_id + "の入荷数量が不正です。");
                        result = RC_INPUTERROR;
                        ind++;
                        continue;
                    }
                }
                meisaiMap.put("nyuukasuuryou", nyuukasuuryou);

                String nyuukatanistr = getInput("#nyuukatani" + ind);
                String nyuukatani = null;
                if ((nyuukatanistr != null) && (!nyuukatanistr.equals(""))) {
                    try {
                        int tani_id = Integer.parseInt(nyuukatanistr);
                        nyuukatani = TaniMap.getTani(tani_id).getTani();
                    } catch (NumberFormatException nfe) {
                        Debug.println(nfe);
                        errorlist.add(shouhin_id + "の入荷単位が不正です。");
                        result = RC_INPUTERROR;
                        ind++;
                        continue;
                    }
                }
                meisaiMap.put("nyuukatani", nyuukatani);

                yearstr = getInput("#year" + ind);
                monthstr = getInput("#month" + ind);
                datestr = getInput("#date" + ind);
                java.util.Date nouhin_date = null;
                try {
                    year = Integer.parseInt(yearstr);
                    month = Integer.parseInt(monthstr);
                    day = Integer.parseInt(datestr);
                    cal = Calendar.getInstance();
                    cal.clear();
                    cal.set(year, month - 1, day);
                    nouhin_date = cal.getTime();
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add(shouhin_id + "の納品日が不正です。");
                    result = RC_INPUTERROR;
                    ind++;
                    continue;
                }
                meisaiMap.put("nouhin_date", nouhin_date);

                String touchakustr = getInput("#touchaku" + ind);
                Integer touchaku;
                try {
                    touchaku = new Integer(touchakustr);
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add(shouhin_id + "の到着時間が不正です。");
                    result = RC_INPUTERROR;
                    ind++;
                    continue;
                }
                meisaiMap.put("touchaku", touchaku);

                String hacchuukubunstr = getInput("#hacchuukubun" + ind);
                Integer hacchuukubun;
                try {
                    hacchuukubun = new Integer(hacchuukubunstr);
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add(shouhin_id + "の発注区分が不正です。");
                    result = RC_INPUTERROR;
                    ind++;
                    continue;
                }
                meisaiMap.put("hacchuukubun", hacchuukubun);

                meisaiList.add(meisaiMap);
            }
            ind++;
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
            HacchuuLocalHome hh = (HacchuuLocalHome)
              ServiceLocator.getLocalHome("java:comp/env/ejb/HacchuuLocal");
            HacchuuPK hpk = new HacchuuPK(hacchuu_id);
            HacchuuLocal hacchuu = hh.findByPrimaryKey(hpk);
            hacchuu.setHacchuu_date(hacchuu_date);
            hacchuu.setShiiresaki_id(shiiresaki_id);
            hacchuu.setNouhinsaki_id(nouhinsaki_id);
            hacchuu.setFormat(format);
            hacchuu.setUpdated(now);
            hacchuu.setUpdatedby(user_id);
            String hacchuu_bg = hacchuu.getHacchuu_bg();

            NyuukayoteimeisaiLocalHome nh = (NyuukayoteimeisaiLocalHome)
              ServiceLocator.getLocalHome(
                "java:comp/env/ejb/NyuukayoteimeisaiLocal");
            Iterator iter = input.iterator();
            while (iter.hasNext()) {
                HashMap inputline = (HashMap)iter.next();
                Integer id = (Integer)inputline.get("id");
                NyuukayoteimeisaiPK pk = new NyuukayoteimeisaiPK(
                  id.intValue());
                NyuukayoteimeisaiLocal nm = nh.findByPrimaryKey(pk);
                Float suuryou = (Float)inputline.get("suuryou");
                nm.setHacchuusuuryou(suuryou.floatValue());
                nm.setHacchuutani((String)inputline.get("hacchuutani"));
                Float tanka = (Float)inputline.get("hacchuutanka");
                nm.setHacchuutanka(tanka.floatValue());
                Float nyuukasuuryou = (Float)inputline.get("nyuukasuuryou");
                nm.setNyuukasuuryou(nyuukasuuryou.floatValue());
                nm.setNyuukatani((String)inputline.get("nyuukatani"));
                java.util.Date nyuukayotei_date = (java.util.Date)
                  inputline.get("nyuukayotei_date");
                nm.changeNyuukayotei_date(nyuukayotei_date, user_id);
                Integer touchaku = (Integer)inputline.get("touchaku");
                nm.setTouchakujikan(touchaku.intValue());
                Integer hacchuukubun = (Integer)inputline.get("hacchuukubun");
                nm.setHacchuukubun(hacchuukubun.intValue());
                Integer nyuukajoukyou = (Integer)inputline.get(
                  "nyuukajoukyou");
                nm.setNyuukajoukyou(nyuukajoukyou.intValue());
                Integer status = (Integer)inputline.get("status");
                nm.setStatus(status.intValue());
            }
            ShouhinLocalHome shome = (ShouhinLocalHome)
              ServiceLocator.getLocalHome("java:comp/env/ejb/ShouhinLocal");
            if (meisaiList.size() > 0) {
                Iterator meisaiiter = meisaiList.iterator();
                while (meisaiiter.hasNext()) {
                    HashMap meisaiMap = (HashMap)meisaiiter.next();
                    String shouhin_id = (String)meisaiMap.get("shouhin_id");
                    ShouhinLocal shouhin = shome.findByPrimaryKey(shouhin_id);
                    Float fSuuryou = (Float)meisaiMap.get("suuryou");
                    float suuryou = fSuuryou.floatValue();
                    String hacchuutani = (String)meisaiMap.get("hacchuutani");
                    if (hacchuutani == null) {
                        hacchuutani = TaniMap.getTani(
                          shouhin.getHacchuutani()).getTani();
                    }
                    Float fTanka = (Float)meisaiMap.get("tanka");
                    float tanka = fTanka.floatValue();
                    Float fNyuukasuuryou =
                      (Float)meisaiMap.get("nyuukasuuryou");
                    float nyuukasuuryou = 0;
                    if (fNyuukasuuryou == null) {
                        //入荷数量未入力の場合 発注数量×入り数
                        nyuukasuuryou = suuryou * shouhin.getIrisuu();
                    } else {
                        nyuukasuuryou = fNyuukasuuryou.floatValue();
                    }
                    String nyuukatani = (String)meisaiMap.get("nyuukatani");
                    if (nyuukatani == null) {
                        nyuukatani =
                          TaniMap.getTani(shouhin.getTani()).getTani();
                    }
                    java.util.Date nouhin_date = (java.util.Date)
                      meisaiMap.get("nouhin_date");
                    Integer iTouchaku = (Integer)meisaiMap.get("touchaku");
                    int touchaku = iTouchaku.intValue();
                    Integer iHacchuukubun =
                      (Integer)meisaiMap.get("hacchuukubun");
                    int hacchuukubun = iHacchuukubun.intValue();
                    java.util.Date shoumikigen = null;
                    java.util.Date shukkakigen = null;
                    if (shouhin.getShoumikigen_flg() == Names.ON) {
                        Calendar cal = Calendar.getInstance();
                        //cal.setTime(nouhin_date);
                        cal.setTime(hacchuu_date);
                        cal.add(Calendar.DATE, shouhin.getShoumikigennissuu());
                        shoumikigen = cal.getTime();
                        //cal.setTime(nouhin_date);
                        cal.setTime(hacchuu_date);
                        cal.add(Calendar.DATE, shouhin.getShukkakigennissuu());
                        shukkakigen = cal.getTime();
                    }

                    NyuukayoteimeisaiLocal nyuukayoteimeisai = nh.create(
                      hacchuu_bg,
                      //nouhinsaki_id,
                      souko_id,
                      shouhin_id,
                      suuryou,
                      hacchuutani,
                      tanka,
                      nyuukasuuryou,
                      nyuukatani,
                      nouhin_date,
                      touchaku,
                      shouhin.getOndotai(),
                      hacchuukubun,
                      shoumikigen,
                      shukkakigen,
                      1, //入庫区分=仕入
                      null, //備考
                      0.0f, //実入荷数
                      1, //入荷状況=未入荷
                      user_id
                    );
                }
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
            errorlist.add("システムエラー・FinderException");
            setRollbackOnly();
            return;
        }
    }

    public void postHandle(HttpServletRequest request) {
        inputval.clear();
    }
}
