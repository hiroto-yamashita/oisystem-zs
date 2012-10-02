package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.TransactionEvent;
import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemzs.ejb.HacchuuPK;
import com.oisix.oisystemzs.ejb.HacchuuLocal;
import com.oisix.oisystemzs.ejb.HacchuuLocalHome;
import com.oisix.oisystemzs.ejb.UserLocal;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.naming.NamingException;
import javax.ejb.FinderException;

public class HacchuusakujoEvent extends TransactionEvent {

    private int hacchuu_id;
    private int user_id;

    public void init(HttpServletRequest request) {
        String hacchuu_idstr = request.getParameter("id");
        try {
            hacchuu_id = Integer.parseInt(hacchuu_idstr);
        } catch (NumberFormatException nfe) {
            Debug.println(nfe);
            errorlist.add("HACCHUU_IDが不正です。");
            result = RC_INPUTERROR;
            return;
        }
        UserLocal user = (UserLocal)session.getAttribute("USER");
        user_id = user.getUser_id();
    }

    public void handleEvent(HashMap attr) {
        if (result == RC_INPUTERROR) { return; }
        java.util.Date now = new java.util.Date();
        try {
            HacchuuLocalHome hh = (HacchuuLocalHome)
              ServiceLocator.getLocalHome("java:comp/env/ejb/HacchuuLocal");
            HacchuuPK hpk = new HacchuuPK(hacchuu_id);
            HacchuuLocal hacchuu = hh.findByPrimaryKey(hpk);
            hacchuu.sakujo(user_id);
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
