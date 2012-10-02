package com.oisix.oisystemfr.taglib;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class InputTag extends TagSupport {

    private String type;
    private String name;
    private String value;
    private String size;
    private String option;
    private String defaultval;
    private static final String YEAR = "_year";
    private static final String MONTH = "_month";
    private static final String DATE = "_date";

    public void setType(String str) { type = str; }
    public void setName(String str) { name = str; }
    //こうしないとvalue=""がデフォルトfloatになってしまう
    //public void setValue(float val) { value = String.valueOf(val); }
    //public void setValue(int val) { value = String.valueOf(val); }
    //引数の型が異なるsetメソッドを作るときは必ずStringを一番下にする
    //そうしないとStringが渡されたとき、JSPのソースが生成されない
    public void setValue(String str) { value = str; }
    public void setSize(String str) { size = str; }
    public void setOption(String str) { option = str; }
    //public void setDefaultval(int val) { defaultval = String.valueOf(val); }
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
            out.print("<input type=\"");
            out.print(type);
            out.print("\" name=\"");
            out.print(name);
            out.print("\" ");
            if (type.equals("text")) {
                if (value != null) {
                    if ((param != null) && !param.equals("")) {
                        out.print("value=\""+param+"\" ");
                    } else if (value.equals(YEAR)) {
                        Calendar cal = Calendar.getInstance();
                        out.print("value=\"" + cal.get(Calendar.YEAR) + "\" ");
                    } else if (value.equals(MONTH)) {
                        Calendar cal = Calendar.getInstance();
                        out.print("value=\""+(cal.get(Calendar.MONTH)+1)+"\" ");
                    } else if (value.equals(DATE)) {
                        Calendar cal = Calendar.getInstance();
                        out.print("value=\"" + cal.get(Calendar.DATE) + "\" ");
                    } else {
                        out.print("value=\""+value+"\" ");
                    }
                }
            } else if (type.equals("radio") || type.equals("checkbox")) {
                if (value != null) {
                    out.print("value=\""+value+"\" ");
                    if (value.equals(param)) {
                        out.print("checked ");
                    }
                }
            }
            if (size != null) {
                out.print("size=\"" + size + "\" ");
            }
            if (option != null) {
                out.print(option);
            }
            out.println(">");
            if (type.equals("checkbox")) {
                // チェックボックスがチェックされていないとrequestにキー自体が
                // 送信されないため、チェックをリセットしても保持したinputvalue
                // がリセットされない。これを防止する。
                out.print("<input type=\"hidden\" name=\"");
                out.print(name);
                out.print("\" value=\"\">");
            }
        } catch (IOException ioe) {
            throw new JspTagException("error printing inputtag");
        }

        return (SKIP_BODY);
    }

}
