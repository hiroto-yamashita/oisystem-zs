package com.oisix.oisystemfr;

import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EncodingFilter implements Filter {

    private FilterConfig config = null;
    // default to JISAutoDetect
    private String targetEncoding = "JISAutoDetect";

    public void init(FilterConfig config) throws ServletException {
        this.config = config;
        this.targetEncoding = config.getInitParameter("encoding");
    }

    public void destroy() {
        config = null;
        targetEncoding = null;
    }

    public  void doFilter(ServletRequest srequest, ServletResponse  sresponse,
      FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest)srequest;
        request.setCharacterEncoding(targetEncoding);
        // move on to the next
        chain.doFilter(srequest,sresponse);
    }
}
