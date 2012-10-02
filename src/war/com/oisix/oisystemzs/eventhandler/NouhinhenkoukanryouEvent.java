package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.TransactionEvent;
import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemzs.ejb.NyuukayoteimeisaiPK;
import com.oisix.oisystemzs.ejb.NyuukayoteimeisaiLocal;
import com.oisix.oisystemzs.ejb.NyuukayoteimeisaiLocalHome;
import com.oisix.oisystemzs.ejb.UserLocal;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.naming.NamingException;
import javax.ejb.FinderException;

public class NouhinhenkoukanryouEvent extends TransactionEvent {

    private HashMap input = new HashMap();
    private int user_id;

    public void init(HttpServletRequest request) {
        Enumeration params = request.getParameterNames();
        Calendar cal = Calendar.getInstance();
        while (params.hasMoreElements()) {
            String key = (String)params.nextElement();
            if (key.startsWith("year")) {
                //request�̃L�[�� year[nyuukayotei_id]�̌`
                String idstr = key.substring(4, key.length());
                Integer id = null;
                try {
                    id = new Integer(idstr);
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add("NYUUKAYOTEI_ID���s���ł��B");
                    result = RC_INPUTERROR;
                    return;
                }
                String yearstr = request.getParameter(key);
                String monthstr = request.getParameter("month" + idstr);
                String datestr = request.getParameter("date" + idstr);
                int year = 0;
                int month = 0;
                int day = 0;
                try {
                    year = Integer.parseInt(yearstr);
                    month = Integer.parseInt(monthstr);
                    day = Integer.parseInt(datestr);
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add("���t���s���ł��B");
                    result = RC_INPUTERROR;
                    return;
                }
                cal.clear();
                cal.set(year, month - 1, day);
                java.util.Date date = cal.getTime();
                String touchakustr = request.getParameter("touchaku" + idstr);
                int touchaku = 0;
                try {
                    touchaku = Integer.parseInt(touchakustr);
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add("�������Ԃ��s���ł��B");
                    result = RC_INPUTERROR;
                    return;
                }
                HashMap inputitem = new HashMap();
                inputitem.put("date", date);
                inputitem.put("touchaku", new Integer(touchaku));
                input.put(id, inputitem);
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
                Integer id = (Integer)iter.next();
                NyuukayoteimeisaiPK pk = new NyuukayoteimeisaiPK(
                  id.intValue());
                NyuukayoteimeisaiLocal nm = nh.findByPrimaryKey(pk);
                //java.util.Date date = (java.util.Date)input.get(id);
                HashMap inputitem = (HashMap)input.get(id);
                java.util.Date date = (java.util.Date)inputitem.get("date");
                int touchaku = ((Integer)inputitem.get("touchaku")).intValue();
                //nm.setNyuukayotei_date(date);
                //nm.setUpdated(now);
                //nm.setUpdatedby(user_id);
                nm.setTouchakujikan(touchaku);
                nm.changeNyuukayotei_date(date, user_id);
            }
        } catch (NamingException ne) {
            Debug.println(ne);
            result = RC_INPUTERROR;
            errorlist.add("�V�X�e���G���[�ENamingException");
            setRollbackOnly();
            return;
        } catch (FinderException fe) {
            Debug.println(fe);
            errorlist.add("�V�X�e���G���[�EFinderException");
            setRollbackOnly();
            return;
        }
    }
}
