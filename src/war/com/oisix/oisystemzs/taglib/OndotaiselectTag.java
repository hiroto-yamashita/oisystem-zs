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
import com.oisix.oisystemzs.ejb.OndotaiData;
import com.oisix.oisystemzs.objectmap.OndotaiMap;
import java.util.Iterator;

public class OndotaiselectTag extends TagSupport {

    private String name;
    private String option;
    private String defaultval;
    private String selected;

    public void setName(String str) { name = str; }
    public void setOption(String str) { option = str; }
    public void setDefaultval(String str) { defaultval = str; }
    public void setSelected(String str) { selected = str; }
    public void setSelected(int val) { selected = String.valueOf(val); }

    public int doStartTag() throws JspTagException {
        HttpServletRequest request =
          (HttpServletRequest)pageContext.getRequest();
        int param = 0;
        String paramstr = request.getParameter(name);
        if (paramstr == null) {
            HttpSession session = request.getSession();
            HashMap inputval = (HashMap)session.getAttribute("INPUTVALUE");
            if (inputval != null) {
                String[] params = (String[])inputval.get(name);
                if (params != null) paramstr = params[0];
            }
        }
        if (paramstr == null) {
            paramstr = selected;
        }
        if (paramstr != null) {
            try {
                param = Integer.parseInt(paramstr);
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }
        }
        TreeMap ondotaiMap = OndotaiMap.getOndotaiMap();
        Iterator iter = null;
        if (ondotaiMap != null) iter = ondotaiMap.values().iterator();
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
            if (defaultval == null) {
                defaultval = "";
            }
            out.println(defaultval);
            while (iter != null && iter.hasNext()) {
                OndotaiData od = (OndotaiData)iter.next();
                int val = od.getOndotai_id();
                out.print("<option value=\"");
                out.print(val);
                out.print("\" ");
                if (val == param) {
                    out.print("selected ");
                }
                if (option != null) {
                    out.print(option);
                }
                out.print(">");
                out.print(od.getOndotai());
            }
            out.println("</select>");
        } catch (IOException ioe) {
            throw new JspTagException("error printing ondotaiselecttag");
        }

        return (SKIP_BODY);
    }

}
