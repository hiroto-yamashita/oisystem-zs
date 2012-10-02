package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.TransactionEvent;
import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemzs.ejb.NyuukoPK;
import com.oisix.oisystemzs.ejb.NyuukoLocal;
import com.oisix.oisystemzs.ejb.NyuukoLocalHome;
import com.oisix.oisystemzs.ejb.ShouhinData;
import com.oisix.oisystemzs.ejb.ShiiresakiLocalHome;
import com.oisix.oisystemzs.ejb.ShiiresakiLocal;
import com.oisix.oisystemzs.ejb.ShiiresakiData;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import javax.servlet.http.HttpServletRequest;
import javax.naming.NamingException;
import javax.ejb.FinderException;

public class NyuukoshousaiEvent extends TransactionEvent {

    private String nyuukoidstr;
    private String shiiresaki_id;
    private NyuukoLocal nyuuko;
    private ShiiresakiLocal shiiresakip;
    private ShiiresakiData shiiresaki;
    private ShouhinData shouhin;
    private java.util.Date nyuuko_date;
    private java.util.Date shoumikigen;
    private java.util.Date shukkakigen;

    public NyuukoLocal getNyuuko() { return nyuuko; }
    public ShouhinData getShouhin() {return shouhin; }
    public ShiiresakiData getShiiresaki() {return shiiresaki; }

    public void init(HttpServletRequest request) {
        nyuukoidstr = request.getParameter("nyuuko_id");
    }

    public void handleEvent(HashMap attr) {
        int nyuuko_id;
        try {
            nyuuko_id = Integer.parseInt(nyuukoidstr);
        } catch (NumberFormatException nfe) {
            errorlist.add("システムエラー。idが数値に変換できません");
            result = RC_INPUTERROR;
            return;
        }
        try {
            NyuukoPK pk = new NyuukoPK(nyuuko_id);
            NyuukoLocalHome nklh = (NyuukoLocalHome)
              ServiceLocator.getLocalHome("java:comp/env/ejb/NyuukoLocal");
            nyuuko = nklh.findByPrimaryKey(pk);
            nyuuko_date = nyuuko.getNyuuko_date();
            shoumikigen=nyuuko.getShoumikigen();
            shukkakigen=nyuuko.getShukkakigen();
            shouhin=nyuuko.getShouhin().getShouhinData();
            shiiresaki_id = nyuuko.getShiiresaki_id();
            if((shiiresaki_id != null) && (!shiiresaki_id.equals(""))){
                ShiiresakiLocalHome slh = (ShiiresakiLocalHome)
                  ServiceLocator.getLocalHome("java:comp/env/ejb/ShiiresakiLocal");
                shiiresakip = slh.findByPrimaryKey(shiiresaki_id);
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
}
