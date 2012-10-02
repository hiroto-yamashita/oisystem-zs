package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.TransactionEvent;
import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemzs.ejb.HacchuuPK;
import com.oisix.oisystemzs.ejb.HacchuuLocal;
import com.oisix.oisystemzs.ejb.HacchuuLocalHome;
import com.oisix.oisystemzs.ejb.HacchuuData;
import com.oisix.oisystemzs.ejb.ShouhinLocal;
import com.oisix.oisystemzs.ejb.ShouhinData;
import com.oisix.oisystemzs.ejb.NyuukayoteimeisaiLocal;
import com.oisix.oisystemzs.ejb.NyuukayoteimeisaiData;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import javax.servlet.http.HttpServletRequest;
import javax.naming.NamingException;
import javax.ejb.FinderException;

public class HacchuushousaiEvent extends TransactionEvent {

    private String idstr;
    //private HacchuuLocal hacchuu;
    private HacchuuData hacchuu;
    private java.util.Date hacchuuday;
    private Collection nyuukayoteimeisai;

    //public HacchuuLocal getHacchuu() { return hacchuu; }
    public HacchuuData getHacchuu() { return hacchuu; }
    public Collection getNyuukayoteimeisai() {
        return nyuukayoteimeisai;
    }

    public void init(HttpServletRequest request) {
        idstr = request.getParameter("id");
    }

    public void handleEvent(HashMap attr) {
        int id;
        try {
            id = Integer.parseInt(idstr);
        } catch (NumberFormatException nfe) {
            errorlist.add("システムエラー。idが数値に変換できません");
            result = RC_INPUTERROR;
            return;
        }
        try {
            HacchuuPK pk = new HacchuuPK(id);
            HacchuuLocalHome hclh = (HacchuuLocalHome)
              ServiceLocator.getLocalHome("java:comp/env/ejb/HacchuuLocal");
            HacchuuLocal hacchuul = hclh.findByPrimaryKey(pk);
            hacchuu = hacchuul.getHacchuuData();
            hacchuuday = hacchuu.getHacchuu_date();
            Collection meisai = hacchuul.getNyuukayoteimeisai();
            Iterator meisaiiter = meisai.iterator();
            nyuukayoteimeisai = new LinkedList();
            while (meisaiiter.hasNext()) {
                NyuukayoteimeisaiLocal nym = (NyuukayoteimeisaiLocal)
                  meisaiiter.next();
                NyuukayoteimeisaiData nyd = nym.getNyuukayoteimeisaiData();
                ShouhinLocal sh = nym.getShouhin();
                ShouhinData shd = sh.getShouhinData();
                java.util.Date date = nyd.getNyuukayotei_date();
                HashMap meisailine = new HashMap();
                meisailine.put("NYUUKAYOTEIMEISAI", nyd);
                meisailine.put("SHOUHIN", shd);
                nyuukayoteimeisai.add(meisailine);
            }
        } catch (NamingException ne) {
            ne.printStackTrace();
            errorlist.add("システムエラーです。");
            result = RC_INPUTERROR;
            return;
        } catch (FinderException fe) {
            fe.printStackTrace();
            errorlist.add("システムエラーです。");
            result = RC_INPUTERROR;
            return;
        }
    }

    public void postHandle(HttpServletRequest request) {
        request.setAttribute("hacchuu", hacchuuday);
    }
}
