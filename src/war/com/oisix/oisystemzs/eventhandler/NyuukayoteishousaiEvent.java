package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.TransactionEvent;
import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemzs.ejb.HacchuuPK;
import com.oisix.oisystemzs.ejb.HacchuuLocal;
import com.oisix.oisystemzs.ejb.HacchuuLocalHome;
import com.oisix.oisystemzs.ejb.ShouhinLocal;
import com.oisix.oisystemzs.ejb.ShouhinLocalHome;
import com.oisix.oisystemzs.ejb.NyuukayoteimeisaiPK;
import com.oisix.oisystemzs.ejb.NyuukayoteimeisaiLocal;
import com.oisix.oisystemzs.ejb.NyuukayoteimeisaiLocalHome;
import com.oisix.oisystemzs.ejb.NyuukayoteimeisaiData;
import com.oisix.oisystemzs.ejb.ShiiresakiLocalHome;
import com.oisix.oisystemzs.ejb.ShiiresakiLocal;
import com.oisix.oisystemzs.ejb.ShiiresakiData;
import com.oisix.oisystemzs.ejb.OndotaiLocalHome;
import com.oisix.oisystemzs.ejb.OndotaiLocal;
import com.oisix.oisystemzs.ejb.OndotaiData;
import com.oisix.oisystemzs.ejb.OndotaiPK;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import javax.servlet.http.HttpServletRequest;
import javax.naming.NamingException;
import javax.ejb.FinderException;

public class NyuukayoteishousaiEvent extends TransactionEvent {

    private String hacchuuidstr;
    private String shouhin_id;
    private String shiiresaki_id;
    private String nyuukayotei_idstr;
    private HacchuuLocal hacchuu;
    private ShouhinLocal shouhin;
    private OndotaiLocal ondo;
    private NyuukayoteimeisaiLocal nyuukayoteimeisai;
    private ShiiresakiLocal shiiresakip;
    private ShiiresakiData shiiresaki;
    private OndotaiData ondotaiData;
    private java.util.Date hacchuuday;
    private Collection nyuukayoteimeisaicol;

    public HacchuuLocal getHacchuu() { return hacchuu; }
    public Collection getNyuukayoteimeisai() {
        return nyuukayoteimeisaicol;
    }
    public ShiiresakiData getShiiresaki() {return shiiresaki; }
    
    public void init(HttpServletRequest request) {
        hacchuuidstr = request.getParameter("hacchuu_id");
        shouhin_id = request.getParameter("shouhin_id");
        shiiresaki_id = request.getParameter("shiiresaki_id");
        nyuukayotei_idstr = request.getParameter("nyuukayotei_id");
    }

    public void handleEvent(HashMap attr) {
        int hacchuu_id;
        int nyuukayotei_id;
        try {
            hacchuu_id = Integer.parseInt(hacchuuidstr);
            nyuukayotei_id = Integer.parseInt(nyuukayotei_idstr);
        } catch (NumberFormatException nfe) {
            errorlist.add("システムエラー。idが数値に変換できません");
            result = RC_INPUTERROR;
            return;
        }
        try {
            HacchuuPK pk = new HacchuuPK(hacchuu_id);
            HacchuuLocalHome hclh = (HacchuuLocalHome)
              ServiceLocator.getLocalHome("java:comp/env/ejb/HacchuuLocal");
            hacchuu = hclh.findByPrimaryKey(pk);
            ShouhinLocalHome slh = (ShouhinLocalHome)
              ServiceLocator.getLocalHome("java:comp/env/ejb/ShouhinLocal");
            shouhin = slh.findByPrimaryKey(shouhin_id);
            OndotaiLocalHome olh = (OndotaiLocalHome)
              ServiceLocator.getLocalHome("java:comp/env/ejb/OndotaiLocal");
            hacchuuday = hacchuu.getHacchuu_date();
            NyuukayoteimeisaiPK nmpk = new NyuukayoteimeisaiPK(nyuukayotei_id);
            NyuukayoteimeisaiLocalHome nmlh = (NyuukayoteimeisaiLocalHome)
              ServiceLocator.getLocalHome("java:comp/env/ejb/NyuukayoteimeisaiLocal");
            nyuukayoteimeisai = nmlh.findByPrimaryKey(nmpk);
            NyuukayoteimeisaiData nyd = nyuukayoteimeisai.getNyuukayoteimeisaiData();
            nyuukayoteimeisaicol = new LinkedList();
            int ondotai = nyd.getOndotai();
            OndotaiPK opk = new OndotaiPK(ondotai);
            ondo = olh.findByPrimaryKey(opk);
            ondotaiData = ondo.getOndotaiData();
            HashMap meisailine = new HashMap();
            meisailine.put("NYUUKAYOTEIMEISAI", nyd);
            meisailine.put("SHOUHIN", shouhin);
            meisailine.put("ONDOTAI",ondotaiData);
            nyuukayoteimeisaicol.add(meisailine);
            if((shiiresaki_id != null) && (!shiiresaki_id.equals(""))){
                ShiiresakiLocalHome sslh = (ShiiresakiLocalHome)
                  ServiceLocator.getLocalHome("java:comp/env/ejb/ShiiresakiLocal");
                shiiresakip = sslh.findByPrimaryKey(shiiresaki_id);
                shiiresaki = shiiresakip.getShiiresakiData();
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
