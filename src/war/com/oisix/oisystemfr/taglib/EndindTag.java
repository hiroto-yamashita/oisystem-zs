package com.oisix.oisystemfr.taglib;

import java.io.IOException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class EndindTag extends TagSupport {

    public int doStartTag() throws JspTagException {
        CollectionTagBase ct = (CollectionTagBase)
          findAncestorWithClass(this, CollectionTagBase.class);
        if (ct == null) {
            throw new JspTagException("CollectionTag not found");
        }
        int endind = ct.getStartind() + ct.size();
        if (endind > ct.numitems()) {
            endind = ct.numitems();
        }

        try {
            JspWriter out = pageContext.getOut();
            out.print(endind);
        } catch (IOException ioe) {
            throw new JspTagException("error printing endind");
        }

        return (SKIP_BODY);
    }
}
