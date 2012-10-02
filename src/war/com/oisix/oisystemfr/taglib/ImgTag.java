package com.oisix.oisystemfr.taglib;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class ImgTag extends TagSupport {

    private String src;
    private String width;
    private String height;
    private String alt;
    private String option;

    public void setSrc(String str) { src = str; }
    public void setWidth(String str) { width = str; }
    public void setHeight(String str) { height = str; }
    public void setAlt(String str) { alt = str; }
    public void setOption(String str) { option = str; }

    public int doStartTag() throws JspTagException {
        HttpServletRequest request =
          (HttpServletRequest)pageContext.getRequest();
        String contextpath = request.getContextPath();
        try {
            JspWriter out = pageContext.getOut();
            out.print("<img src=\"" + contextpath + "/" + src + "\" ");
            if (width != null) out.print("width=\"" + width + "\" ");
            if (height != null) out.print("height=\"" + height + "\" ");
            if (alt != null) out.print("alt=\"" + alt + "\" ");
            //if (option != null) out.print("option=\"" + option + "\" ");
            if (option != null) out.print(option + " ");
            out.println(">");
        } catch (IOException ioe) {
            throw new JspTagException("error printing inputtag");
        }

        return (SKIP_BODY);
    }

}
