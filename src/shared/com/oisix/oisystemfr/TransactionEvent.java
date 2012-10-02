package com.oisix.oisystemfr;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class TransactionEvent
  extends EventHandlerSupport implements Serializable {
    private boolean rollbackOnly = false;
    protected LinkedList errorlist = new LinkedList();
    protected int result = RC_SUCCEED;
    public static int RC_SUCCEED = 0;
    public static int RC_INPUTERROR = 1;

    protected void setRollbackOnly() {
        rollbackOnly = true;
    }

    public boolean getRollbackOnly() {
        return rollbackOnly;
    }

    public LinkedList getErrorlist() { return errorlist; }
    public int getResult() { return result; }

    public abstract void init(HttpServletRequest request);

    public abstract void handleEvent(HashMap attr);
}
