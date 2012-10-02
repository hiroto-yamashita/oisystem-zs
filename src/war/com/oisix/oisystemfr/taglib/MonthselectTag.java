package com.oisix.oisystemfr.taglib;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.DateUtil;
import java.util.Calendar;
import java.util.HashMap;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class MonthselectTag extends TagSupport {

    private String name;
    private String option;
    private String value;
    private String datekey;

    public void setName(String str) { name = str; }
    public void setOption(String str) { option = str; }
    public void setValue(String str) { value = str; }
    public void setDatekey(String str) { datekey = str; }

    public int doStartTag() throws JspTagException {
        java.util.Date date = DateUtil.getDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int currentmonth = cal.get(Calendar.MONTH) + 1;
        // request -> session -> value -> datekey -> ç°åªç› ÇÃèáÇ…óDêÊÇ≥ÇÍÇÈ
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
            param = value;
        }
        if (param == null) {
            if (datekey != null) {
                date = (java.util.Date)request.getAttribute(datekey);
                cal.setTime(date);
                param = String.valueOf(cal.get(Calendar.MONTH) + 1);
            }
        }
        int intparam = currentmonth;
        try {
            intparam = Integer.parseInt(param);
        } catch (NumberFormatException nfe) {}
        try {
            JspWriter out = pageContext.getOut();
            out.print("<select name=\"");
            out.print(name);
            out.println("\">");
            
            for (int i=1; i<=12; i++) {
                out.print("<option value=\"");
                out.print(i);
                out.print("\" ");
                if (i == intparam) {
                    out.print("selected ");
                }
                if (option != null) {
                    out.print(option);
                }
                out.print(">");
                out.print(i);
            }
            out.println("</select>");
        } catch (IOException ioe) {
            throw new JspTagException("error printing monthselecttag");
        }

        return (SKIP_BODY);
    }

}
