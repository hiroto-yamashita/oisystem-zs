package com.oisix.oisystemfr;

import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TransactionStartServlet extends HttpServlet {

    public void init() throws ServletException {
        Debug.println("Initializing" ,this);
    }
    
    public void destroy() {
        Debug.println("Finalizing", this);
    }

    public void doGet(HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {
        process(request, response);
    }


    public void doPost(HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {
        process(request, response);
    }

    protected void process(HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {
        Debug.println("Processing" ,this);

        HttpSession session = request.getSession();
        HashMap inputval = (HashMap)session.getAttribute("INPUTVALUE");
        inputval.put(UrlUtil.TRANSACTION_KEY,
          request.getParameter(UrlUtil.TRANSACTION_KEY));
        inputval.put(UrlUtil.TRANSACTION_RESULT_URL,
          request.getParameter(UrlUtil.TRANSACTION_RESULT_URL));
        inputval.put(UrlUtil.TRANSACTION_SOURCE_URL,
          request.getParameter(UrlUtil.TRANSACTION_SOURCE_URL));
        session.setAttribute("INPUTVALUE", inputval);

        //キャッシュの無効化
        response.setHeader("Expires", "Mon, 26 Jul 1997 05:00:00 GMT"); 
        response.setHeader("Last-Modified", "Mon, 26 Jul 1997 05:00:00 GMT");
        response.setHeader("Cache-Control","no-cache, must-revalidate");
        response.setHeader("Pragma","no-cache");

        String action = UrlUtil.encode(
          request, response, UrlUtil.TRANSACTION_URL + UrlUtil.getSuffix());

        //HTML出力
        PrintWriter pw = new PrintWriter(new BufferedWriter(
          new OutputStreamWriter(response.getOutputStream(),"SJIS")));
        response.setContentType("text/html; charset=SJIS");
        pw.println("<html><head>");
        pw.println("</head>");
        pw.println("<body>");
        pw.println("<noscript>Java ScriptをONにしてください</noscript>");
        pw.println("</body></html>");
        pw.println("<script>location.href=\""+action+"\";</script>");

        pw.flush();
        pw.close();
        Debug.println("end Processing" ,this);
    }

}
