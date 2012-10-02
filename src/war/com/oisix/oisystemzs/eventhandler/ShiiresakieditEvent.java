package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.TransactionEvent;
import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemzs.ejb.ShiiresakiLocal;
import com.oisix.oisystemzs.ejb.ShiiresakiData;
import com.oisix.oisystemzs.ejb.ShiiresakiLocalHome;
import com.oisix.oisystemzs.ejb.ShiiresakiData;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.naming.NamingException;
import javax.ejb.FinderException;

public class ShiiresakieditEvent extends TransactionEvent {

    private String idstr;
    private ShiiresakiData shiire;

    public ShiiresakiData getShiiresaki() { return shiire; }

    public void init(HttpServletRequest request) {
        idstr = request.getParameter("#shiiresaki_id0");
    }

    public void handleEvent(HashMap attr) {
        try {
            ShiiresakiLocalHome shome = (ShiiresakiLocalHome)
              ServiceLocator.getLocalHome("java:comp/env/ejb/ShiiresakiLocal");
            ShiiresakiLocal shiirel = shome.findByPrimaryKey(idstr);
            shiire = shiirel.getShiiresakiData();
        } catch (NamingException ne) {
            ne.printStackTrace();
            errorlist.add("システムエラーです。NamingException");
            result = RC_INPUTERROR;
            return;
        } catch (FinderException fe) {
            fe.printStackTrace();
            errorlist.add("該当する仕入先がありません。");
            result = RC_INPUTERROR;
            return;
        }
    }

    public void postHandle(HttpServletRequest request) {
    }
}
