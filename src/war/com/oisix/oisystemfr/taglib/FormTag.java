package com.oisix.oisystemfr.taglib;

import com.oisix.oisystemfr.UrlUtil;
import com.oisix.oisystemfr.ControllerServlet;
import javax.servlet.http.HttpServletRequest;

public class FormTag extends UrlTagBase {

    private String method;
    private String option;
    private String transaction;
    private String result;
    public static final String REQUESTTIME = "REQUESTTIME";

    public void setAction(String str) { tourl = str; }
    public String getAction() { return tourl; }

    public void setMethod(String str) { method = str; }
    public String getMethod() { return method; }

    public void setOption(String str) { option = str; }
    public String getOption() { return option; }

    public void setTransaction(String str) {
        transaction = str;
        tourl = UrlUtil.TRANSACTION_START_URL + UrlUtil.getSuffix();
    }
    public String getTransaction() { return transaction; }

    public void setResult(String str) {
        result = str;
    }
    public String getResult() { return result; }

    protected String makeTagBody(String encodedurl) {
        StringBuffer sb = new StringBuffer(500);
        sb.append("<form");
        if (tourl != null) {
            sb.append(" action=\"").append(encodedurl).append("\"");
        }
        if (method != null) {
            sb.append(" method=\"").append(method).append("\"");
        }
        if (option !=null) {
            sb.append(" ").append(option);
        }
        sb.append(">");
        if (transaction != null) {
            appendTransaction(sb);
        }
        sb.append(bodyContent.getString());
        sb.append("<input type=\"hidden\" name=\"");
        sb.append(REQUESTTIME);
        sb.append("\" value=\"").append((new java.util.Date()).getTime());
        sb.append("\">");
        sb.append("</form>");

        return sb.toString();
    }

    private void appendTransaction(StringBuffer sb) {
        sb.append("<input type=\"hidden\" name=\"");
        sb.append(UrlUtil.TRANSACTION_KEY);
        sb.append("\" value=\"");
        sb.append(transaction);
        sb.append("\">");
        sb.append("<input type=\"hidden\" name=\"");
        sb.append(UrlUtil.TRANSACTION_RESULT_URL);
        sb.append("\" value=\"");
        sb.append(result);
        sb.append("\">");
        HttpServletRequest request =
          (HttpServletRequest)pageContext.getRequest();
        String url = (String)request.getAttribute(ControllerServlet.PATH_INFO)
          + UrlUtil.getSuffix();
        sb.append("<input type=\"hidden\" name=\"");
        sb.append(UrlUtil.TRANSACTION_SOURCE_URL);
        sb.append("\" value=\"");
        sb.append(url);
        sb.append("\">");
    }
}
