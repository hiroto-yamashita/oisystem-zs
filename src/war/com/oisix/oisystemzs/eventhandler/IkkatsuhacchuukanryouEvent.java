package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.DateUtil;
import com.oisix.oisystemfr.TransactionEvent;
import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemzs.Names;
import com.oisix.oisystemzs.ejb.HacchuuLocal;
import com.oisix.oisystemzs.ejb.HacchuuLocalHome;
import com.oisix.oisystemzs.ejb.NyuukayoteimeisaiLocal;
import com.oisix.oisystemzs.ejb.NyuukayoteimeisaiLocalHome;
import com.oisix.oisystemzs.ejb.ShouhinLocal;
import com.oisix.oisystemzs.ejb.ShouhinLocalHome;
import com.oisix.oisystemzs.ejb.UserLocal;
import com.oisix.oisystemzs.ejb.SoukoData;
import com.oisix.oisystemzs.objectmap.TaniMap;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.naming.NamingException;
import javax.ejb.CreateException;
import javax.ejb.FinderException;

public class IkkatsuhacchuukanryouEvent extends TransactionEvent {

    private String nouhinsaki_id;
    private int hacchuusho = 1; //発注書フォーマットは加工品
    private int hacchuukubun;
    private int user_id;
    private HashMap inputList = new HashMap();
    private java.util.Date nouhin_date;
    private boolean inputnouhindate = false;

    private LinkedList resultList = new LinkedList();

    public LinkedList getResultList() { return resultList; }

    public void init(HttpServletRequest request) {
        // 納品先コードの取得
        HttpSession session = request.getSession();
        SoukoData souko = (SoukoData)session.getAttribute("SOUKO");
        nouhinsaki_id = souko.getNouhinsaki_id();

        // 発注区分の取得
        String hacchuukubunstr = request.getParameter("hacchuukubun");
        if (hacchuukubunstr == null || hacchuukubunstr.equals("")) {
            errorlist.add("発注区分が未選択です。");
            result = RC_INPUTERROR;
        } else {
            try {
                hacchuukubun = Integer.parseInt(hacchuukubunstr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("発注区分が不正です。");
                result = RC_INPUTERROR;
            }
        }

        Enumeration params = request.getParameterNames();
        String key = null;
        String value = null;
        while (params.hasMoreElements()) {
            key = (String)params.nextElement();
            if (key.startsWith("id")) {
                String shouhin_id = key.substring(2, key.length());
                value = request.getParameter(key);
                //Integer suuryou = null;
                Float suuryou = null;
                try {
                    suuryou = new Float(value);
                } catch (NumberFormatException nfe) {
                    result = RC_INPUTERROR;
                    errorlist.add("商品コード " + shouhin_id +
                      "の数量が不正です:" + value);
                }
                //if (suuryou.intValue() > 0) {
                if (suuryou.floatValue() > 0) {
                    String shiiresaki_id = request.getParameter("shiiresaki" + 
                      shouhin_id);
                    HashMap hacchuumap = (HashMap)inputList.get(shiiresaki_id);
                    if (hacchuumap == null) {
                        hacchuumap = new HashMap();
                    }
                    hacchuumap.put(shouhin_id, suuryou);
                    inputList.put(shiiresaki_id, hacchuumap);
                }
            }
        }

        String year = request.getParameter("year");
        String month = request.getParameter("month");
        String date = request.getParameter("date");
        if ((year != null) && (!year.equals(""))) {
            inputnouhindate = true;
            Calendar cal = DateUtil.getCalendar(year, month, date);
            nouhin_date = cal.getTime();
        }

        UserLocal user = (UserLocal)session.getAttribute("USER");
        user_id = user.getUser_id();
    }

    public void handleEvent(HashMap attr) {
        if (result == RC_INPUTERROR) { return; }
        java.util.Date today = DateUtil.getDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        java.util.Date hacchuu_date = cal.getTime();
        try {
            HacchuuLocalHome hhome = (HacchuuLocalHome)
              ServiceLocator.getLocalHome("java:comp/env/ejb/HacchuuLocal");
            NyuukayoteimeisaiLocalHome nyhome = (NyuukayoteimeisaiLocalHome)
              ServiceLocator.getLocalHome(
              "java:comp/env/ejb/NyuukayoteimeisaiLocal");
            ShouhinLocalHome shome = (ShouhinLocalHome)
              ServiceLocator.getLocalHome("java:comp/env/ejb/ShouhinLocal");
            Set keys = inputList.keySet();
            Iterator iter = keys.iterator();
            while (iter.hasNext()) {
                String shiiresaki_id = (String)iter.next();
                HacchuuLocal hacchuu = hhome.create(
                  shiiresaki_id,
                  nouhinsaki_id,
                  hacchuu_date,
                  hacchuusho,
                  null,
                  user_id
                );
                int hacchuu_id = hacchuu.getHacchuu_id();
                String hacchuu_bg = hacchuu.getHacchuu_bg();
                HashMap hacchuumap = (HashMap)inputList.get(shiiresaki_id);
                Set hkeys = hacchuumap.keySet();
                Iterator hiter = hkeys.iterator();
                while (hiter.hasNext()) {
                    String shouhin_id = (String)hiter.next();
                    //Integer suuryou = (Integer)hacchuumap.get(shouhin_id);
                    //int isuuryou = suuryou.intValue();
                    Float suuryou = (Float)hacchuumap.get(shouhin_id);
                    float fsuuryou = suuryou.floatValue();
                    ShouhinLocal shouhin = shome.findByPrimaryKey(shouhin_id);
                    int ihacchuutani = shouhin.getHacchuutani();
                    String hacchuutani =
                      TaniMap.getTani(ihacchuutani).getTani();
                    float tanka = shouhin.getTanka();
                    //int irisuu = shouhin.getIrisuu();
                    float irisuu = shouhin.getIrisuu();
                    float nyuukasuuryou = fsuuryou * irisuu;
                    int shiireleadtime = shouhin.getShiireleadtime();
                    int inyuukatani = shouhin.getTani();
                    String nyuukatani =
                      TaniMap.getTani(inyuukatani).getTani();
                    if (!inputnouhindate) {
                        cal.setTime(today);
                        cal.set(Calendar.HOUR, 0);
                        cal.set(Calendar.MINUTE, 0);
                        cal.set(Calendar.SECOND, 0);
                        cal.set(Calendar.MILLISECOND, 0);
                        cal.add(Calendar.DATE, shiireleadtime);
                        nouhin_date = cal.getTime();
                    }
                    java.util.Date shoumikigen = null;
                    java.util.Date shukkakigen = null;
                    if (shouhin.getShoumikigen_flg() == Names.ON) {
                        int shoumikigennisuu = shouhin.getShoumikigennissuu();
                        //cal.setTime(hacchuu_date);
                        cal.setTime(nouhin_date);
                        cal.add(Calendar.DATE, shoumikigennisuu);
                        shoumikigen = cal.getTime();
                        int shukkakigennissuu = shouhin.getShukkakigennissuu();
                        //cal.setTime(hacchuu_date);
                        cal.setTime(nouhin_date);
                        cal.add(Calendar.DATE, shukkakigennissuu);
                        shukkakigen = cal.getTime();
                    }
                    NyuukayoteimeisaiLocal nm = nyhome.create(
                      hacchuu_bg,
                      nouhinsaki_id,
                      shouhin_id,
                      fsuuryou,
                      hacchuutani,
                      tanka,
                      nyuukasuuryou,
                      nyuukatani,
                      nouhin_date,
                      //99, //到着時間 = 指定なし
                      12, //到着時間 = 午前中
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
                    HashMap result = new HashMap();
                    result.put("hacchuu_id", new Integer(hacchuu_id));
                    result.put("hacchuu_bg", hacchuu_bg);
                    result.put("shiiresaki_id", shiiresaki_id);
                    result.put("shouhin_id", shouhin_id);
                    result.put("shouhin", shouhin.getShouhin());
                    result.put("suuryou", suuryou);
                    result.put("hacchuutani", hacchuutani);
                    resultList.add(result);
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
            result = RC_INPUTERROR;
            errorlist.add("商品コードが間違っています。");
            setRollbackOnly();
            return;
        }
    }

    public void postHandle(HttpServletRequest request) {
    }
}
