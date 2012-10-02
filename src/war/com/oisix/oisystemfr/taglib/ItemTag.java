package com.oisix.oisystemfr.taglib;

import java.util.HashMap;
import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class ItemTag extends TagSupport {

    protected Object item;
    protected String field;
    protected String dateformat;
    public void setField(String str) { field = str; }
    public void setDateformat(String str) { dateformat = str; }

    public int doStartTag() throws JspTagException {
        ItemsTag it = (ItemsTag)findAncestorWithClass(this, ItemsTag.class);
        if (it == null) {
            throw new JspTagException("ItemsTag not found");
        }
        item = it.getCurrentItem();

        //HashMap以外も扱えるよう拡張予定
        HashMap itemmap = (HashMap)item;
        String value = null;
        if (field != null) {
            Object valueobj = itemmap.get(field);
            if (valueobj != null) {
                if ((valueobj instanceof java.util.Date) 
                 && (dateformat != null)) {
                    // 日付表示形式の指定
                    SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
                    value = sdf.format(valueobj);
                } else {
                    value = valueobj.toString();
                }
            }
        }

        try {
            JspWriter out = pageContext.getOut();
            if (value != null) { out.print(value); }
        } catch (IOException ioe) {
            throw new JspTagException("error printing item value");
        }

        return (SKIP_BODY);
    }
}
