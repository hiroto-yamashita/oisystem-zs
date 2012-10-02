package com.oisix.oisystemfr.taglib;

import com.oisix.oisystemfr.Debug;
import java.util.Iterator;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class ItemsTag extends BodyTagSupport {

    protected Iterator iter;
    protected Object item;

    public Object getCurrentItem() {
        return item;
    }

    public int doStartTag() throws JspTagException {
        CollectionTagBase ct = (CollectionTagBase)
          findAncestorWithClass(this, CollectionTagBase.class);
        if (ct == null) {
            throw new JspTagException("CollectionTag not found");
        }
        iter = ct.getIterator();
        item = iter.next();
        HttpServletRequest request = (HttpServletRequest)
          pageContext.getRequest();
        request.setAttribute("ITEM", item);
        return (EVAL_BODY_INCLUDE);
    }

    public int doAfterBody() {
        if (iter.hasNext()) {
            item = iter.next();
            HttpServletRequest request = (HttpServletRequest)
              pageContext.getRequest();
            request.setAttribute("ITEM", item);
            return (EVAL_BODY_AGAIN);
        }
        return (SKIP_BODY);
    }

    public int doEndTag() {
        if (bodyContent != null) {
            try {
                JspWriter out = getPreviousOut();
                out.print(bodyContent.getString());
            } catch (IOException ioe) {
                Debug.println("error in collection tag", this);
            }
        }
        return (EVAL_PAGE);
    }
}
