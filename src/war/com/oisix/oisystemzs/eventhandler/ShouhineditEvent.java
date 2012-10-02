package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.TransactionEvent;
import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemzs.ejb.ShouhinLocal;
import com.oisix.oisystemzs.ejb.ShouhinData;
import com.oisix.oisystemzs.ejb.ShouhinLocalHome;
import com.oisix.oisystemzs.ejb.ShouhinData;
import com.oisix.oisystemzs.ejb.ShiiresakiLocal;
import com.oisix.oisystemzs.ejb.ShiiresakiData;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.naming.NamingException;
import javax.ejb.FinderException;

public class ShouhineditEvent extends TransactionEvent {

    private String idstr;
    private ShouhinData shouhin;
    private ShiiresakiData shiiresaki;

    public ShouhinData getShouhin() { return shouhin; }
    public ShiiresakiData getShiiresaki() { return shiiresaki; }

    public void init(HttpServletRequest request) {
        inputval.clear();
        idstr = request.getParameter("#shouhin_id0");
    }

    public void handleEvent(HashMap attr) {
        ShouhinLocal shouhinl = null;
        try {
            ShouhinLocalHome shome = (ShouhinLocalHome)
              ServiceLocator.getLocalHome("java:comp/env/ejb/ShouhinLocal");
            shouhinl = shome.findByPrimaryKey(idstr);
            shouhin = shouhinl.getShouhinData();
        } catch (NamingException ne) {
            ne.printStackTrace();
            errorlist.add("システムエラーです。NamingException");
            result = RC_INPUTERROR;
            return;
        } catch (FinderException fe) {
            fe.printStackTrace();
            errorlist.add("該当する商品がありません。");
            result = RC_INPUTERROR;
            return;
        }
        if (shouhinl == null) {
            errorlist.add("システムエラー・shouhinl is null");
            result = RC_INPUTERROR;
        }
        try {
            ShiiresakiLocal shiirel = shouhinl.getShiiresaki();
            shiiresaki = shiirel.getShiiresakiData();
        } catch (NamingException ne) {
            ne.printStackTrace();
            errorlist.add("システムエラーです。NamingException 1");
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
