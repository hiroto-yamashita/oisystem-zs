package com.oisix.oisystemfr.taglib;

import com.oisix.oisystemfr.Debug;
import java.util.HashMap;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;
import javax.servlet.jsp.JspTagException;

public class AnchorTag extends UrlTagBase {

    private String target;
    private String option;
    private String urlencode;
    private String itemkey;

    public void setHref(String str) { tourl = str; }
    public String getHref() { return tourl; }

    public void setTarget(String str) { target = str; }
    public String getTarget() { return target; }

    public void setOption(String str) { option = str; }
    public String getOption() { return option; }

    public void setUrlencode(String str) { urlencode = str; }
    public String getUrlencode() { return urlencode; }

    public void setItemkey(String str) { itemkey = str; }
    public String getItemkey() { return itemkey; }

    protected String makeTagBody(String encodedurl) {
        StringBuffer sb = new StringBuffer(300);
        sb.append("<a href=\"").append(encodedurl);
        if (itemkey != null) {
            ItemsTag it = (ItemsTag)findAncestorWithClass(this,ItemsTag.class);
            if (it != null) {
                HashMap item = (HashMap)it.getCurrentItem();
                sb.append(item.get(itemkey));
            } else {
                Debug.println("Items not found.ignoring.");
            }
        }
        if (urlencode != null) {
            try {
                sb.append(URLEncoder.encode(urlencode, "UTF-8"));
            } catch (UnsupportedEncodingException uee) {
                uee.printStackTrace();
            }
        }
        sb.append("\"");
        if (target != null) {
            sb.append(" target=\"").append(target).append("\"");
        }
        if (option !=null) {
            sb.append(" ").append(option);
        }
        sb.append(">");
        sb.append(bodyContent.getString());
        sb.append("</a>");

        return sb.toString();
    }
}
