package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.TransactionEvent;
import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemzs.ejb.ShukkoPK;
import com.oisix.oisystemzs.ejb.ShukkoLocal;
import com.oisix.oisystemzs.ejb.ShukkoLocalHome;
import com.oisix.oisystemzs.ejb.ShukkokubunData;
import com.oisix.oisystemzs.ejb.UserLocal;
import com.oisix.oisystemzs.objectmap.ShukkokubunMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.naming.NamingException;
import javax.ejb.FinderException;

public class ShukkokubunkoushinEvent extends TransactionEvent {

    private LinkedList resultdetail = new LinkedList();
    private int user_id;
    private String[] shukko_ids;
    private int shukkokubun;

    public Collection getResultdetail() {
        return resultdetail;
    }

    public void init(HttpServletRequest request) {
        shukko_ids = request.getParameterValues("shukko_id");
        if (shukko_ids == null) {
            errorlist.add("出庫を選択してください。");
            result = RC_INPUTERROR;            
        }
        String shukkokubunstr = request.getParameter("shukkokubun");
        try {
            shukkokubun = Integer.parseInt(shukkokubunstr);
        } catch(NumberFormatException nfe) {
            errorlist.add("出庫区分を選択してください。");
            result = RC_INPUTERROR;
        }
        UserLocal user = (UserLocal)session.getAttribute("USER");
        user_id = user.getUser_id();
    }

    public void handleEvent(HashMap attr) {
        if (result == RC_INPUTERROR) { return; }
        List shukko_idlist = Arrays.asList(shukko_ids);
        Iterator iter = shukko_idlist.iterator();
        java.util.Date now = new java.util.Date();
        try {
            ShukkoLocalHome sklh = (ShukkoLocalHome)ServiceLocator.getLocalHome(
              "java:comp/env/ejb/ShukkoLocal");
            while (iter.hasNext()) {
                String shukko_id = (String)iter.next();
                int id = 0;
                try {
                    id = Integer.parseInt(shukko_id);
                } catch (NumberFormatException nfe) {
                    result = RC_INPUTERROR;
                    return;
                }
                ShukkoPK pk = new ShukkoPK(id);
                ShukkoLocal skl = sklh.findByPrimaryKey(pk);
                HashMap resultline = new HashMap();
                resultline.put("SHUKKO_BG", skl.getShukko_bg());
                ShukkokubunData shukkokubunData = ShukkokubunMap.getShukkokubun(
                  skl.getShukkokubun());
                resultline.put("BEFORE", shukkokubunData.getShukkokubun());
                skl.setShukkokubun(shukkokubun);
                skl.setUpdatedby(user_id);
                skl.setUpdated(now);
                shukkokubunData = ShukkokubunMap.getShukkokubun(
                  skl.getShukkokubun());
                resultline.put("AFTER", shukkokubunData.getShukkokubun());
                resultdetail.add(resultline);
            }
        } catch (NamingException ne) {
            Debug.println(ne);
            result = RC_INPUTERROR;
            errorlist.add("システムエラー・NamingException");
            setRollbackOnly();
            return;
        } catch (FinderException fe) {
            Debug.println(fe);
            errorlist.add("システムエラー・FinderException");
            setRollbackOnly();
            return;
        }

    }
}
