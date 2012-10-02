package com.oisix.oisystemfr.taglib;

import com.oisix.oisystemfr.Debug;
import java.util.Collection;
import java.util.Iterator;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public abstract class CollectionTagBase extends BodyTagSupport {

    public static final String COLSTART="COLSTART";
    protected Iterator iter;
    protected int startind = 0;  //開始位置
    protected int size = -1;     //表示件数(-1 全て)
    protected int numitems;      //総件数
    protected boolean hasprev = false;
    protected boolean hasnext = false;
    public void setSize(String str) {
        try {
            size = Integer.parseInt(str);
        } catch (NumberFormatException e) {}
    }
    public int getStartind() { return startind; }
    public int size() { return size; }
    public int numitems() { return numitems; }
    public boolean hasPrev() { return hasprev; }
    public boolean hasNext() { return hasnext; }

    private void setStartind() {
        HttpServletRequest request =
          (HttpServletRequest)pageContext.getRequest();
        try {
            String startindstr = request.getParameter(COLSTART);
            startind = Integer.parseInt(startindstr);
        } catch (NumberFormatException e) {}
    }

    public int doStartTag() throws JspException {
        Debug.println("collectiontag start", this);
        setStartind();
        Collection collection = null;
        try {
            collection = findCollection();
            if ((collection == null) ||
               ((collection != null)&&(collection.size() == 0))) {
                return (SKIP_BODY);
            }
        } catch (Exception e) {
            Debug.println(e);
            collection = null;
            return (SKIP_BODY);
        }
        iter = collection.iterator();
        //return (EVAL_BODY_TAG);
        //return (EVAL_BODY_AGAIN);
        return (EVAL_BODY_INCLUDE);
    }

    /*
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
    */

    public Iterator getIterator() {
        return iter;
    }

    protected abstract Collection findCollection() throws Exception;
}
