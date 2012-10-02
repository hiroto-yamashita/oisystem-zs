package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.TransactionEvent;
import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemzs.ejb.UserLocalHome;
import com.oisix.oisystemzs.ejb.UserLocal;
import com.oisix.oisystemzs.ejb.SoukoData;
import com.oisix.oisystemzs.ejb.OfficeData;
import com.oisix.oisystemzs.objectmap.SoukoMap;
import com.oisix.oisystemzs.objectmap.OfficeMap;
import java.util.HashMap;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.naming.NamingException;
import javax.ejb.FinderException;

public class DologinEvent extends TransactionEvent {

    private String password;
    private String souko_id;
    private String office_id;
    private UserLocal user;
    private SoukoData souko;
    private OfficeData office;

    public UserLocal getUser() { return user; }
    public SoukoData getSouko() { return souko; }
    public OfficeData getOffice() { return office; }

    public void init(HttpServletRequest request) {
        password = getInput("#password");
        souko_id = getInput("#souko_id");
        office_id = getInput("#office_id");
    }

    public void handleEvent(HashMap attr) {
        if ((password == null) || (password.equals(""))) {
            errorlist.add("パスワードを入力してください");
            result = RC_INPUTERROR;
            return;
        }
        try {
            UserLocalHome uh = (UserLocalHome)
              ServiceLocator.getLocalHome("java:comp/env/ejb/UserLocal");
            Collection users = uh.findByPassword(password);
            if ((users == null) || (users.isEmpty())) {
                errorlist.add("password not found");
                result = RC_INPUTERROR;
                return;
            }
            Iterator useriter = users.iterator();
            user = (UserLocal)useriter.next();
        } catch (NamingException ex) {
            Debug.println(ex);
            errorlist.add("System Error Naming Exception");
            result = RC_INPUTERROR;
            return;
        } catch (FinderException ex) {
            errorlist.add("password not found");
            result = RC_INPUTERROR;
            return;
        }
        if ((souko_id == null) || (souko_id.equals(""))) {
            souko_id = user.getSouko_id();
        }
        if ((souko_id == null) || (souko_id.equals(""))) {
            errorlist.add("倉庫が設定されていません");
            result = RC_INPUTERROR;
            return;
        }
        souko = SoukoMap.getSouko(souko_id);
        if (souko == null) {
            errorlist.add("倉庫が存在しません");
            result = RC_INPUTERROR;
            return;
        }
        if ((office_id == null) || (office_id.equals(""))) {
            office_id = user.getOffice_id();
        }
        if ((office_id == null) || (office_id.equals(""))) {
            errorlist.add("事務所が設定されていません");
            result = RC_INPUTERROR;
            return;
        }
        office = OfficeMap.getOffice(office_id);
        if (office == null) {
            errorlist.add("事務所が存在しません");
            result = RC_INPUTERROR;
            return;
        }
    }

    public void postHandle(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("LOGINSTATUS", "LOGIN");
        session.setAttribute("USER", user);
        session.setAttribute("SOUKO", souko);
        session.setAttribute("OFFICE", office);
    }

}
