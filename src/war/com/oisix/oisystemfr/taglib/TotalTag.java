package com.oisix.oisystemfr.taglib;

import java.io.IOException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class TotalTag extends TagSupport {

    public int doStartTag() throws JspTagException {
        CollectionTagBase ct = (CollectionTagBase)
          findAncestorWithClass(this, CollectionTagBase.class);
        if (ct == null) {
            throw new JspTagException("CollectionTag not found");
        }
        int total = ct.numitems();

        try {
            JspWriter out = pageContext.getOut();
            out.print(total);
        } catch (IOException ioe) {
            throw new JspTagException("error printing total");
        }

        return (SKIP_BODY);
    }
}
