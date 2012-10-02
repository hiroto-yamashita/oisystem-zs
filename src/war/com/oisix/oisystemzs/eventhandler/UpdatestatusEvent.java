package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.TransactionEvent;
import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemzs.ejb.NyuukayoteimeisaiLocal;
import com.oisix.oisystemzs.ejb.NyuukayoteimeisaiLocalHome;
import com.oisix.oisystemzs.ejb.UserLocal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.naming.NamingException;
import javax.ejb.FinderException;

public class UpdatestatusEvent extends TransactionEvent {

    private HashMap input = new HashMap();
    private int user_id;

    public void init(HttpServletRequest request) {
        Enumeration params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String key = (String)params.nextElement();
            if (key.startsWith("status_")) {
                //requestのキーは status_[hacchuu_bg]の形
                String hacchuu_bg = key.substring(7);
                String statusstr = request.getParameter(key);
                int status = 0;
                try {
                    status = Integer.parseInt(statusstr);
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add("NYUUKAYOTEI_IDが不正です。");
                    result = RC_INPUTERROR;
                    return;
                }
                input.put(hacchuu_bg, new Integer(status));
            }
        }
        UserLocal user = (UserLocal)session.getAttribute("USER");
        user_id = user.getUser_id();
    }

    public void handleEvent(HashMap attr) {
        if (result == RC_INPUTERROR) { return; }
        Iterator iter = input.keySet().iterator();
        java.util.Date now = new java.util.Date();
        try {
            NyuukayoteimeisaiLocalHome nh = (NyuukayoteimeisaiLocalHome)
              ServiceLocator.getLocalHome(
                "java:comp/env/ejb/NyuukayoteimeisaiLocal");
            while (iter.hasNext()) {
                String bg = (String)iter.next();
                int status = ((Integer)input.get(bg)).intValue();
                Collection nyuukayotei = nh.findByHacchuu_bg(bg);
                Iterator nyuukaiter = nyuukayotei.iterator();
                while (nyuukaiter.hasNext()) {
                    NyuukayoteimeisaiLocal nm =
                      (NyuukayoteimeisaiLocal)nyuukaiter.next();
                    nm.setStatus(status);
                    nm.setUpdated(now);
                    nm.setUpdatedby(user_id);
                }
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
