package com.oisix.oisystemfr.taglib;

import com.oisix.oisystemfr.UrlUtil;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletContext;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public abstract class UrlTagBase extends BodyTagSupport {

    protected String tourl;

    public int doAfterBody() throws JspException {
        HttpServletRequest request =
          (HttpServletRequest)pageContext.getRequest();
        HttpServletResponse response =
          (HttpServletResponse)pageContext.getResponse();
        String encodedurl = UrlUtil.encode(request, response, tourl);
        if (bodyContent != null) {
            try {
                JspWriter out = getPreviousOut();
                out.print(makeTagBody(encodedurl));
            } catch (IOException ioe) {
                throw new JspException(ioe.toString());
            }
        }
        return (SKIP_BODY);
    }

    protected abstract String makeTagBody(String encodedurl);
}
