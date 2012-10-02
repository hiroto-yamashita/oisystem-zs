package com.oisix.oisystemfr.taglib;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.UrlUtil;
import java.io.IOException;
import java.util.Enumeration;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class PrevTag extends BodyTagSupport {

    private String action;
    private int startind;
    private int size;
    public void setAction(String str) { action = str; }

    public int doStartTag() throws JspTagException {
        CollectionTagBase ct = (CollectionTagBase)
          findAncestorWithClass(this, CollectionTagBase.class);
        if (ct == null) {
            throw new JspTagException("CollectionTagBase not found");
        }
        if (!ct.hasPrev()) {
            return (SKIP_BODY);
        }
        startind = ct.getStartind();
        size = ct.size();
        return (EVAL_BODY_BUFFERED);
    }

    public int doEndTag() {
        if (bodyContent != null) {
            HttpServletRequest request =
              (HttpServletRequest)pageContext.getRequest();
            HttpServletResponse response =
              (HttpServletResponse)pageContext.getResponse();
            String encodedurl = UrlUtil.encode(request, response, action);
            try {
                JspWriter out = getPreviousOut();
                out.print("<a ");
                out.print("href=\"" + encodedurl + "?");
                Enumeration params = request.getParameterNames();
                String key = null;
                while (params.hasMoreElements()) {
                    key = (String)params.nextElement();
                    if (!key.equals(CollectionTagBase.COLSTART)) {
                        out.print(URLEncoder.encode(key, "UTF-8"));
                        out.print("=");
                        out.print(request.getParameter(key));
                        out.println("&");
                    }
		}
                out.print(CollectionTagBase.COLSTART);
                out.print("=");
                out.print(startind - size);
                out.println("\">");
                out.println(bodyContent.getString());
                out.print("</a>");
            } catch (IOException ioe) {
                Debug.println("error in prev tag", this);
            }
        }
        return (EVAL_PAGE);
    }

}
