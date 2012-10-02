package com.oisix.oisystemfr.taglib;

import java.io.IOException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class StartindTag extends TagSupport {

    public int doStartTag() throws JspTagException {
        CollectionTagBase ct = (CollectionTagBase)
          findAncestorWithClass(this, CollectionTagBase.class);
        if (ct == null) {
            throw new JspTagException("CollectionTag not found");
        }
        int startind = ct.getStartind() + 1;

        try {
            JspWriter out = pageContext.getOut();
            out.print(startind);
        } catch (IOException ioe) {
            throw new JspTagException("error printing startind");
        }

        return (SKIP_BODY);
    }
}
