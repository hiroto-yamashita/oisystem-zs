package com.oisix.oisystemfr;

import com.oisix.oisystemfr.TransactionEvent;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Enumeration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TransactionServlet extends HttpServlet {

    public static final String RESULTKEY = "RESULTKEY";
    public static final String KEY = "KEY";
    public static final String ERRORKEY = "ERRORKEY";

    public void init() throws ServletException {
        Debug.println("Initializing" ,this);
    }
    
    public void destroy() {
        Debug.println("Finalizing", this);
    }

    public void doGet(HttpServletRequest request,
                  HttpServletResponse response)
        throws IOException, ServletException {
        process(request, response);
    }


    public void doPost(HttpServletRequest request,
               HttpServletResponse response)
        throws IOException, ServletException {
        process(request, response);
    }

    protected void process(HttpServletRequest request,
               HttpServletResponse response)
        throws IOException, ServletException {

        Debug.println("Processing" ,this);

        TransactionEvent event =
            (TransactionEvent)request.getAttribute(RequestProcessor.EVENTKEY);
        if (event == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                               "EventHandler not found");
            return;
        }

        HttpSession session = request.getSession();
        String transactionresult;
        HashMap inputval = null;
        if (event.getResult() == TransactionEvent.RC_SUCCEED) {
            inputval = (HashMap)session.getAttribute("INPUTVALUE");
            transactionresult =
              (String)inputval.get(UrlUtil.TRANSACTION_RESULT_URL);
            //ì¸óÕì‡óeÇÕè¡ãé
            inputval = new HashMap();
        } else {
            inputval = (HashMap)session.getAttribute("INPUTVALUE");
            transactionresult =
              (String)inputval.get(UrlUtil.TRANSACTION_SOURCE_URL);
            //ì¸óÕì‡óeÇÕï€éù
        }
        String action = UrlUtil.encode(request, response, transactionresult);

        //eventé¿çsåãâ ï€ë∂
        inputval.put(RESULTKEY, event);
        session.setAttribute("INPUTVALUE", inputval);

        response.sendRedirect(action);
    }
}
