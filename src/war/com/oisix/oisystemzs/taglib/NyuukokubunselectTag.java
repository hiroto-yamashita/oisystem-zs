package com.oisix.oisystemzs.taglib;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemzs.objectmap.NyuukokubunMap;
import com.oisix.oisystemzs.ejb.NyuukokubunData;
import java.io.IOException;
import java.util.HashMap;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.Iterator;

public class NyuukokubunselectTag extends TagSupport {

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
        TreeMap formatMap =
          NyuukokubunMap.getNyuukokubunMap();
        Iterator iter = null;
        if (formatMap != null) iter = formatMap.keySet().iterator();
        try {
            JspWriter out = pageContext.getOut();
            out.print("<select name=\"");
            out.print(name);
            out.println("\">");
            while (iter != null && iter.hasNext()) {
                Integer key = (Integer)iter.next();
                NyuukokubunData nd = (NyuukokubunData)formatMap.get(key);
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
                out.print(nd.getNyuukokubun());
            }
            out.println("</select>");
        } catch (IOException ioe) {
            throw new JspTagException("error printing nyuukokubunselect tag");
        }

        return (SKIP_BODY);
    }

}
