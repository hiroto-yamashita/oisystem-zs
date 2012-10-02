package com.oisix.oisystemfr;

import com.oisix.oisystemfr.ejb.ControllerEjbLocal;
import com.oisix.oisystemfr.ejb.ControllerEjbLocalHome;
import com.oisix.oisystemfr.pdf.PdfObjectBase;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.ServletContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ejb.CreateException;
import javax.ejb.NoSuchObjectLocalException;
import javax.naming.Context;
import javax.naming.NamingException;

public class ControllerServlet extends HttpServlet {

    private boolean debug = false;
    private String handlerPackage = "com.oisix.oisystemfr.eventhandler.";
    private String warInitializer =
      "com.oisix.oisystemfr.DefaultWarInitializer";
    private RequestProcessor rp;
    private ScreenManager sm;
    private static final String TRANSACTIONSTARTURI = "/transactionstart";
    private int urisuffixlen = 3;
    public static final String PATH_INFO = "PATH_INFO";
    public static final String CONTROLLEREJB = "CONTROLLEREJB";

    public void init() throws ServletException {
        initializeParameters();
        rp = new RequestProcessor();
        rp.init(handlerPackage, urisuffixlen);
        sm = new ScreenManager();
        try {
            URL smxmlurl = getServletContext().getResource(
              "/WEB-INF/oisystemfr-xml/screendef.xml");
            sm.init(smxmlurl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String dwname = getServletContext().getInitParameter("warInitializer");
        if (dwname == null) { dwname = warInitializer; }
        try {
            DefaultWarInitializer dw = (DefaultWarInitializer)
              Class.forName(dwname).newInstance();
            dw.init(getServletContext());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    
    public void destroy() {
        debug("Finalizing controller");
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
        debug("Processing a " + request.getMethod() +
           " for " + request.getServletPath());

        HttpSession session = request.getSession(true);
        if (null == session) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
              "Failed to create http session");
            return;
        }
        //SFSBはExceptionをthrowするとコンテナから削除されてしまい、
        //その後referenceはあってもメソッドコール時にNoSuchObjectException
        //をthrowするようになる。これを避けるためコンテナから削除されたあと
        //HttpSessionから初期化する。
        ControllerEjbLocal cejb = (ControllerEjbLocal)
          session.getAttribute(ControllerServlet.CONTROLLEREJB);
        if (cejb != null) {
            try {
                boolean alive = cejb.isAlive();
            } catch (NoSuchObjectLocalException nsoe) {
                session.invalidate();
                session = request.getSession(true);
                if (null == session) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                      "Failed to create http session");
                    return;
                }
            }
        }
        if (session.isNew()) {
            try {
                ControllerEjbLocalHome cejbh = (ControllerEjbLocalHome)
                  ServiceLocator.getLocalHome(
                  "java:comp/env/ControllerEjbLocal");
                cejb = cejbh.create(ServiceLocator.getDataSourceName());
                session.setAttribute(CONTROLLEREJB, cejb);
            } catch (NamingException ne) {
                ne.printStackTrace();
            } catch (CreateException ce) {
                ce.printStackTrace();
            }
        }

        // 入力内容の保存
        HashMap inputval = (HashMap)session.getAttribute("INPUTVALUE");
        if (inputval == null) {
            inputval = new HashMap();
        }
        Enumeration params = request.getParameterNames();
        String key = null;
        String[] values = null;
        while (params.hasMoreElements()) {
            key = (String)params.nextElement();
            if (key.startsWith("#")) {
                values = request.getParameterValues(key);
                if ((values.length == 1) && (values[0].equals(""))) {
                    // チェックボックスリセットの場合にinputval要素を削除
                    // チェックボックスには同じキーでhiddenの""をつける
                    inputval.remove(key);
                } else {
                    int ind = 0;
                    String[] newval = new String[values.length];
                    for (int i=0; i<values.length; i++) {
                        if (!values[i].equals("")) {
                            //空文字は省く
                            //チェックボックスでは入力内容削除のためhiddenで
                            //空文字を送信しているが、それが必ず要素[0]で来ると
                            //は限らないため。
                            newval[ind] = values[i];
                            ind++;
                        }
                    }
                    inputval.put(key, newval);
                }
            }
        }
        session.setAttribute("INPUTVALUE", inputval);

        // リクエストの処理
        String uri = request.getRequestURI();
        if (uri != null) {
            String ctxpath = request.getContextPath();
            int ctxpathlen = 0;
            if (ctxpath != null) {
                ctxpathlen = ctxpath.length();
            }
            uri = uri.substring(ctxpathlen + 1, uri.length() - urisuffixlen);
        }
        request.setAttribute(PATH_INFO, uri); //JSPで取得できるようにする
        if ((uri != null) && (uri.equals(UrlUtil.TRANSACTION_START_URL))) {
            // ダブルクリック防止サーブレットにforward
            try {
                RequestDispatcher rd =
                  getServletContext().getRequestDispatcher(
                    UrlUtil.TRANSACTION_START_SERVLET);
                rd.forward(request, response);
                return;
            } catch (Exception e) {
                //システムエラー
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                  "Failed to forward to transactionstart");
                return;
            }
        }

        // 画面の決定
        String template = sm.setNextScreen(request);
        debug("template="+template);
        // ログインチェック
        if (request.getAttribute("NEEDLOGIN") != null) {
            String loginStatus = (String)session.getAttribute("LOGINSTATUS");
            if ((loginStatus == null) || (!loginStatus.equals("LOGIN"))) {
                String loginRedirectURI = sm.getLoginRedirectURI();
                if (loginRedirectURI == null) {
                    session.setAttribute("LOGINREDIRECTURI", uri);
                } else {
                    session.setAttribute("LOGINREDIRECTURI", loginRedirectURI);
                }
                response.sendRedirect(UrlUtil.encode(
                  request, response, sm.getLoginURI() + UrlUtil.getSuffix()));
                return;
            }
        }

        try {
            rp.process(request);
        } catch (Exception e) {
            e.printStackTrace();
            PrintWriter pw = new PrintWriter(new BufferedWriter(
              new OutputStreamWriter(response.getOutputStream(),"SJIS")));
            response.setContentType("text/html; charset=SJIS");
            response.setHeader("Expires", "Mon, 26 Jul 1997 05:00:00 GMT"); 
            response.setHeader(
              "Last-Modified", "Mon, 26 Jul 1997 05:00:00 GMT");
            response.setHeader("Cache-Control","no-cache, must-revalidate");
            response.setHeader("Pragma","no-cache");
            pw.println("<html><body>");
            pw.println("System error occured while processing request");
            pw.println("</body></html>");
            pw.flush();
            pw.close();
            return;
        }

        if ((uri != null) && (uri.equals(UrlUtil.TRANSACTION_URL))) {
            // リロード防止サーブレットにforward
            try {
                RequestDispatcher rd =
                  getServletContext().getRequestDispatcher(
                    UrlUtil.TRANSACTION_SERVLET);
                rd.forward(request, response);
                return;
            } catch (Exception e) {
                //システムエラー
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                       "Failed to forward to transaction");
                return;
            }
        }

        //トランザクション結果beanをsessionから削除
        Object event = inputval.get(TransactionServlet.RESULTKEY);
        if (event != null) {
            request.setAttribute(TransactionServlet.RESULTKEY, event);
            inputval.remove(TransactionServlet.RESULTKEY);
        }
        session.setAttribute("INPUTVALUE", inputval);

        try {
            RequestDispatcher rd =
              getServletContext().getRequestDispatcher("/"+template);
            if (rd == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                       "Failed to find template "+template);
                return;
            }
            rd.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                   "Failed to forward to JSP");
            return;
        }
    }

    /**
     * Initialize web context parameters
     */
    protected void initializeParameters() {
        ServletContext context = getServletContext();
        String value = context.getInitParameter("handlerPackage");
        if (value != null) {
            log("contextparam handlerPackage="+value);
            handlerPackage = value;
        }
        value = context.getInitParameter("debug");
        if (value != null) {
            log("contextparam debug="+value);
            debug = Boolean.valueOf(value).booleanValue();
        }
        Debug.setFlg(debug);
        value = context.getInitParameter("datasource");
        if (value != null) {
            log("contextparam datasource="+value);
            context.setAttribute("DATASOURCE", value);
            ServiceLocator.setDataSourceName("java:comp/env/" + value);
        }
        value = context.getInitParameter("urisuffix");
        if (value != null) {
            log("contextparam urisuffix="+value);
            UrlUtil.setSuffix(value);
            urisuffixlen = value.length();
        }
        value = context.getInitParameter("urlrewriting");
        if (value != null) {
            log("contextparam urlrewriting="+value);
            UrlUtil.setUrlRewriting(value.equals("true"));
        }
        value = context.getInitParameter("pdfbasepath");
        if (value != null) {
            log("contextparam pdfBasePath="+value);
            PdfObjectBase.setPdfBasePath(value);
        }
        value = context.getInitParameter("testtime");
        if (value != null) {
            log("contextparam testtime="+value);
            try {
                int testtime = Integer.parseInt(value);
                DateUtil.setTesttime(testtime);
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }
        }
        debug("Initializing controller ");
    }

    private void debug(String msg) {
        if (debug) {
            log(msg);
        }
    }

    private void debug(Exception e) {
        if (debug) {
            e.printStackTrace();
        }
    }
}
