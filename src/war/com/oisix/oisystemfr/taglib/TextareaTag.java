package com.oisix.oisystemfr.taglib;

import com.oisix.oisystemfr.Debug;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class TextareaTag extends BodyTagSupport {

    private String name;
    private String cols;
    private String rows;
    private String wrap;
    private String option;

    public void setName(String str) { name = str; }
    public void setCols(String str) { cols = str; }
    public void setRows(String str) { rows = str; }
    public void setWrap(String str) { wrap = str; }
    public void setOption(String str) { option = str; }

    public int doEndTag() throws JspException {
        StringBuffer sb = new StringBuffer(1024);
        sb.append("<textarea");
        sb.append(" name=\"");
        sb.append(name);
        sb.append("\"");
        if (null != cols) {
            sb.append(" cols=\"");
            sb.append(cols);
            sb.append("\"");
        }
        if (null != rows) {
            sb.append(" rows=\"");
            sb.append(rows);
            sb.append("\"");
        }
        if (null != wrap) {
            sb.append(" wrap=\"");
            sb.append(wrap);
            sb.append("\"");
        }
        if (null != option) {
            sb.append(" ");
            sb.append(option);
        }
        sb.append(">");

        HttpServletRequest request =
          (HttpServletRequest)pageContext.getRequest();
        String param = request.getParameter(name);
        if (null == param) {
            HttpSession session = request.getSession();
            HashMap inputval = (HashMap)session.getAttribute("INPUTVALUE");
            if (inputval != null) {
                String[] params = (String[])inputval.get(name);
                if (params != null) {
                    param = params[0];
                }
            }
        }
        if (null == param && null != bodyContent) {
            param = bodyContent.getString();
        }
        if (null != param) {
            sb.append(param);
        }

        sb.append("</textarea>");

        try {
            JspWriter out = pageContext.getOut();
            out.print(sb.toString());
        } catch (IOException ioe) {
            throw new JspException("error printing textareatag");
        }
        return (EVAL_PAGE);
    }
}
