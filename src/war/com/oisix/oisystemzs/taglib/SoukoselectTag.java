package com.oisix.oisystemzs.taglib;

import com.oisix.oisystemfr.Debug;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import com.oisix.oisystemzs.ejb.SoukoData;
import com.oisix.oisystemzs.objectmap.SoukoMap;
import java.util.Iterator;

public class SoukoselectTag extends TagSupport {

    private String name;
    private String option;

    public void setName(String str) { name = str; }
    public void setOption(String str) { option = str; }

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
        HashMap soukoMap = SoukoMap.getSoukoMap();
        Iterator iter = null;
        if (soukoMap != null) iter = soukoMap.values().iterator();
        try {
            JspWriter out = pageContext.getOut();
            out.print("<select name=\"");
            out.print(name);
            out.println("\">");
            out.print("<option value=\"\"");
            if (option != null) {
                out.print(option);
            }
            out.println(">（デフォルト倉庫）");
            while (iter != null && iter.hasNext()) {
                SoukoData sl = (SoukoData)iter.next();
                String value = String.valueOf(sl.getSouko_id());
                out.print("<option value=\"");
                out.print(value);
                out.print("\" ");
                if (value.equals(param)) {
                    out.print("selected ");
                }
                if (option != null) {
                    out.print(option);
                }
                out.print(">");
                out.print(sl.getName());
            }
            out.println("</select>");
        } catch (IOException ioe) {
            throw new JspTagException("error printing taniselecttag");
        }

        return (SKIP_BODY);
    }

}
