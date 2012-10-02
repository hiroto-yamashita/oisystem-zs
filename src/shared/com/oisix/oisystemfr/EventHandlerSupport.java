package com.oisix.oisystemfr;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class EventHandlerSupport
  implements Serializable, EventHandler {
    protected HttpServletRequest request;
    protected HttpSession session;
    protected HashMap inputval;

    public void preInit(HttpServletRequest request) {
        this.request = request;
        session = request.getSession();
        inputval = (HashMap)session.getAttribute("INPUTVALUE");
    }

    public abstract void init(HttpServletRequest request);

    public void postInit() {
        request = null;
        session = null;
    }

    public abstract void handleEvent(HashMap attr);

    protected String getInput(String key) {
        if (inputval == null) {
            return null;
        }
        String[] str = (String[])inputval.get(key);
        if (str == null) {
            return null;
        }
        return str[0];
    }

    public void postHandle(HttpServletRequest request) {}
}
