package com.oisix.oisystemzs.taglib;

import com.oisix.oisystemfr.Debug;
import java.io.IOException;
import java.util.TreeMap;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import com.oisix.oisystemzs.ejb.TaniData;
import com.oisix.oisystemzs.objectmap.TaniMap;
import java.util.Iterator;

public class TanimeiselectTag extends TagSupport {

    private String name;
    private String option;
    private String defaultval;
    private String selected;

    public void setName(String str) { name = str; }
    public void setOption(String str) { option = str; }
    public void setDefaultval(String str) { defaultval = str; }
    public void setSelected(String str) { selected = str; }

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
            param = selected;
        }
        TreeMap taniMap = TaniMap.getTaniMap();
        Iterator iter = null;
        if (taniMap != null) iter = taniMap.values().iterator();
        try {
            JspWriter out = pageContext.getOut();
            out.print("<select name=\"");
            out.print(name);
            out.println("\">");
            out.print("<option value=\"\"");
            if (option != null) {
                out.print(option);
            }
            out.print(">");
            if (defaultval != null) {
                out.print(defaultval);
            }
            while (iter != null && iter.hasNext()) {
                TaniData td = (TaniData)iter.next();
                String tani = td.getTani();
                out.print("<option value=\"");
                out.print(tani);
                out.print("\" ");
                if (tani.equals(param)) {
                    out.print("selected ");
                }
                if (option != null) {
                    out.print(option);
                }
                out.print(">");
                out.print(tani);
            }
            out.println("</select>");
        } catch (IOException ioe) {
            throw new JspTagException("error printing taniselecttag");
        }

        return (SKIP_BODY);
    }

}
