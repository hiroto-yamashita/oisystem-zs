package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.TransactionEvent;
import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemzs.ejb.ShukkoPK;
import com.oisix.oisystemzs.ejb.ShukkoLocal;
import com.oisix.oisystemzs.ejb.ShukkoLocalHome;
import com.oisix.oisystemzs.ejb.ShukkoData;
import com.oisix.oisystemzs.ejb.ShouhinLocal;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.naming.NamingException;
import javax.ejb.FinderException;

public class ShukkoteiseiEvent extends TransactionEvent {

    private String strShukko_id;
    private ShukkoData shukko;
    private ShouhinLocal shouhin;

    public ShukkoData getShukko() { return shukko; }
    public ShouhinLocal getShouhin() { return shouhin; }

    public void init(HttpServletRequest request) {
        strShukko_id = request.getParameter("shukko_id");
        if (strShukko_id == null) {
            strShukko_id = (String)session.getAttribute("TEISEISHUKKO_ID");
            session.setAttribute("TEISEISHUKKO_ID", null);
        }
    }

    public void handleEvent(HashMap attr) {
        int id;
        try {
            id = Integer.parseInt(strShukko_id);
        } catch (NumberFormatException nfe) {
            errorlist.add("システムエラー。idを数値に変換できません");
            result = RC_INPUTERROR;
            return;
        }
        try {
            ShukkoPK pk = new ShukkoPK(id);
            ShukkoLocalHome sklh = (ShukkoLocalHome)
              ServiceLocator.getLocalHome("java:comp/env/ejb/ShukkoLocal");
            ShukkoLocal skl = sklh.findByPrimaryKey(pk);
            shukko = skl.getShukkoData();
            shouhin = skl.getShouhin();
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
