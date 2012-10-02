package com.oisix.oisystemfr;

import com.oisix.oisystemfr.TransactionEvent;
import com.oisix.oisystemfr.exception.ProcessingException;
import com.oisix.oisystemfr.ejb.ControllerEjbLocal;
import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.JNDIUtil;
import java.util.StringTokenizer;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RequestProcessor {

    private String handlerPackage;
    private int urisuffixlen = 3;
    public static final String EVENTKEY = "event";

    public void init(String hp, int len) {
        handlerPackage = hp;
        urisuffixlen = len;
    }

    public void process(HttpServletRequest request) throws ProcessingException{
        HttpSession session = request.getSession();
        if (session == null) {
            System.out.println("session null in RequestProcessor.process()");
            throw new ProcessingException();
        }
        ControllerEjbLocal cejb = (ControllerEjbLocal)
          session.getAttribute(ControllerServlet.CONTROLLEREJB);
        try {
            EventHandler handler = getEventHandler(request);
            if (handler != null) {
                if (handler instanceof TransactionEvent) {
                    //handler��TransactionEvent�̃T�u�N���X�Ȃ�App Tier��
                    Debug.println("handling event in apptier "+handler);
                    handler = cejb.handleEvent((TransactionEvent)handler);
                    Debug.println(handler.toString());
                } else {
                    //Web Tier��event�����s
                    handler.handleEvent(null);
                }
                handler.postHandle(request);
                request.setAttribute(EVENTKEY, handler);
            }
        } catch (LinkageError le) {
            //Debug.println(le.getMessage());
            //throw new ProcessingException(le.getMessage());
            //jdk1.4��chained exeption facility���g��
            throw new ProcessingException(le);
        } catch (IllegalAccessException iae) {
            //Debug.println(iae);
            //throw new ProcessingException(iae.getMessage());
            throw new ProcessingException(iae);
        } catch (InstantiationException ie) {
            //Debug.println(ie);
            //throw new ProcessingException(ie.getMessage());
            throw new ProcessingException(ie);
        } catch (SecurityException se) {
            //Debug.println(se);
            //Throwable t = new ProcessingException(se.getMessage());
            //t = t.fillInStackTrace();
            //throw (ProcessingException)t;
            throw new ProcessingException(se);
        }
    }

    protected EventHandler getEventHandler(HttpServletRequest request)
      throws LinkageError, ExceptionInInitializerError, IllegalAccessException,
      InstantiationException, SecurityException {
        EventHandler handler = null;
        //String uri = request.getPathInfo();
        String uri = (String)request.getAttribute(ControllerServlet.PATH_INFO);
        if (uri.equals(UrlUtil.TRANSACTION_URL)) {
            //URI��TRANSACTIONURL�Ȃ�request�Ɏw�肳�ꂽ�C�x���g
            HttpSession session = request.getSession();
            HashMap inputval = (HashMap)session.getAttribute("INPUTVALUE");
            uri = (String)inputval.get(UrlUtil.TRANSACTION_KEY);
            if (uri != null) {
                uri = uri.substring(0, uri.length() - urisuffixlen);
            }
            Debug.println(uri, this);
        } else {
            //�ʏ��URI�̖�������C�x���g�𐶐�
            StringTokenizer st = new StringTokenizer(uri, "/");
            while (st.hasMoreTokens()) {
                uri = st.nextToken();Debug.println(uri, this);
            }
        }
        uri = uri.toUpperCase().substring(0,1) + uri.substring(1);
        String handlerstr = handlerPackage + uri + "Event";
        try {
            handler = (EventHandler)Class.forName(handlerstr).newInstance();
            Debug.println("EventHandler "+handlerstr+" created", this);
            handler.preInit(request);
            handler.init(request);
            handler.postInit();
        } catch (ClassNotFoundException ex) {
        //�N���X��������Ȃ�������EventHandler�͂Ȃ�
            handler = null;
            Debug.println("EventHandler isn't created.JSP only", this);
        }
        //throw new SecurityException();
        return handler;
    }

}
