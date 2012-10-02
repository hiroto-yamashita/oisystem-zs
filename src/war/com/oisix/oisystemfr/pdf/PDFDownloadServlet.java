package com.oisix.oisystemfr.pdf;

import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PDFDownloadServlet extends HttpServlet {

    public void init() throws ServletException {
    }
    
    public void destroy() {
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
        //String path = request.getParameter("path");
        String uri = request.getRequestURI();
        if (uri == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
              "Failed to forward to transactionstart");
            return;
        }
        String ctxpath = request.getContextPath();
        int ctxpathlen = 0;
        if (ctxpath != null) {
            ctxpathlen = ctxpath.length();
        }
        String path = uri.substring(ctxpathlen + 1, uri.length());
        response.setContentType("application/pdf");
        ServletOutputStream out = response.getOutputStream();
        FileInputStream is = new FileInputStream(
          PdfObjectBase.getPdfBasePath() + path);
        response.setContentLength(is.available());
        byte[] buf = new byte[1024];
        int len;
        while ((len = is.read(buf, 0, buf.length)) != -1) {
            out.write(buf, 0, len);
        }
        out.close();
        is.close();

    }

}
