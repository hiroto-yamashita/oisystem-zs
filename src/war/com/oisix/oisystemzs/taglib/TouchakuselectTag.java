package com.oisix.oisystemzs.taglib;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemzs.objectmap.TouchakuMap;
import com.oisix.oisystemzs.ejb.TouchakuData;
import java.io.IOException;
import java.util.HashMap;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.Iterator;

public class TouchakuselectTag extends TagSupport {

    private String name;
    private String option;
    private String defaultval;

    public void setName(String str) { name = str; }
    public void setOption(String str) { option = str; }
    public void setDefaultval(String str) { defaultval = str; }

    public int doStartTag() throws JspTagException {
        HttpServletRequest request =
          (HttpServletRequest)pageContext.getRequest();
        String param = request.getParameter(name);
        if (param == null) {
            HttpSession session = request.getSession();
            HashMap inputval = (HashMap)session.getAttribute("INPUTVALUE");
            if (inputval != null) {
                String[] params = (String[])inputval.get(name);
                if (params != null) param = params[0];
            }
        }
        if (param == null) {
            param = defaultval;
        }
        TreeMap touchakuMap = TouchakuMap.getTouchakuMap();
        Iterator iter = null;
        if (touchakuMap != null) iter =
          touchakuMap.keySet().iterator();
        try {
            JspWriter out = pageContext.getOut();
            out.print("<select name=\"");
            out.print(name);
            out.println("\">");
            while (iter != null && iter.hasNext()) {
                Integer key = (Integer)iter.next();
                TouchakuData td = (TouchakuData)touchakuMap.get(key);
                out.print("<option value=\"");
                out.print(key);
                out.print("\" ");
                if (String.valueOf(key).equals(param)) {
                    out.print("selected ");
                }
                if (option != null) {
                    out.print(option);
                }
                out.print(">");
                out.print(td.getTouchaku());
            }
            out.println("</select>");
        } catch (IOException ioe) {
            throw new JspTagException("error printing touchakuselect tag");
        }

        return (SKIP_BODY);
    }

}
