package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.TransactionEvent;
import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemzs.ejb.NyuukoPK;
import com.oisix.oisystemzs.ejb.NyuukoLocal;
import com.oisix.oisystemzs.ejb.NyuukoLocalHome;
import com.oisix.oisystemzs.ejb.NyuukoData;
import com.oisix.oisystemzs.ejb.ShouhinLocal;
import com.oisix.oisystemzs.ejb.ShiiresakiLocalHome;
import com.oisix.oisystemzs.ejb.ShiiresakiLocal;
import com.oisix.oisystemzs.ejb.ShiiresakiData;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.naming.NamingException;
import javax.ejb.FinderException;

public class NyuukoteiseiEvent extends TransactionEvent {

    private String strNyuuko_id;
    private String shiiresaki_id;
    private NyuukoData nyuuko;
    private ShouhinLocal shouhin;
    private ShiiresakiLocal shiiresakip;
    private ShiiresakiData shiiresaki;

    public NyuukoData getNyuuko() { return nyuuko; }
    public ShouhinLocal getShouhin() { return shouhin; }
    public ShiiresakiData getShiiresaki() {return shiiresaki; }
    
    public void init(HttpServletRequest request) {
        strNyuuko_id = getInput("#nyuuko_id");
        shiiresaki_id = getInput("#shiiresaki_id");
    }

    public void handleEvent(HashMap attr) {
        int id;
        try {
            id=Integer.parseInt(strNyuuko_id);
        }catch (NumberFormatException nfe) {
            errorlist.add("システムエラー。idを数値に変換できません");
            result = RC_INPUTERROR;
            return;
        }
        try {
            NyuukoPK pk = new NyuukoPK(id);
            NyuukoLocalHome nlh = (NyuukoLocalHome)
              ServiceLocator.getLocalHome("java:comp/env/ejb/NyuukoLocal");
            NyuukoLocal nl = nlh.findByPrimaryKey(pk);
            nyuuko = nl.getNyuukoData();
            shouhin = nl.getShouhin();
            if((shiiresaki_id != null) && (!shiiresaki_id.equals("")) &&
               (!shiiresaki_id.equals("nll"))){
                ShiiresakiLocalHome sslh = (ShiiresakiLocalHome)
                  ServiceLocator.getLocalHome(
                  "java:comp/env/ejb/ShiiresakiLocal");
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
    }
}
