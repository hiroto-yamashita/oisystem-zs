package com.oisix.oisystemfr.taglib;

import com.oisix.oisystemfr.Debug;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class OptionTag extends TagSupport {

    private String name;
    private String value;
    private String option;
    private String defaultval;

    public void setName(String str) { name = str; }
    public void setValue(String str) { value = str; }
    public void setOption(String str) { option = str; }
    public void setDefaultval(String str) { defaultval = str; }
    public void setDefaultval(int val) { defaultval = String.valueOf(val); }

    public int doStartTag() throws JspTagException {
        HttpServletRequest request =
          (HttpServletRequest)pageContext.getRequest();
        String param = request.getParameter(name);
        if (param == null) {
            HttpSession session = request.getSession();
            //HashMap inputval = (HashMap)request.getAttribute("INPUTVALUE");
            HashMap inputval = (HashMap)session.getAttribute("INPUTVALUE");
            if (inputval != null) {
                String[] params = (String[])inputval.get(name);
                if (params != null) {
                    param = params[0];
                }
            }
        }
        if (param == null) {
            param = defaultval;
        }
        try {
            JspWriter out = pageContext.getOut();
            out.print("<option ");
            if (value != null) {
                out.print("value=\""+value+"\" ");
                if (value.equals(param)) {
                    out.print("selected ");
                }
            }
            if (option != null) {
                out.print(option);
            }
            out.println(">");
        } catch (IOException ioe) {
            throw new JspTagException("error printing optiontag");
        }

        return (SKIP_BODY);
    }

}
