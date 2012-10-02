package com.oisix.oisystemfr;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

public class UrlUtil {
    public static final String TRANSACTION_START_URL="transactionstart";
    public static final String
      TRANSACTION_START_SERVLET="/TransactionStartServlet";
    public static final String TRANSACTION_KEY="TRANSACTION";
    public static final String TRANSACTION_RESULT_URL="TRANSACTIONRESULT";
    public static final String TRANSACTION_SOURCE_URL="TRANSACTIONSOURCE";
    public static final String TRANSACTION_URL="transaction";
    public static final String TRANSACTION_SERVLET="/TransactionServlet";
    private static String suffix = ".ev";
    private static boolean urlRewriting = false;

    protected static void setSuffix(String s) { suffix = s; }

    public static String getSuffix() { return suffix; }

    protected static void setUrlRewriting(boolean b) {
        urlRewriting = b;
    }

    public static String encode(
      HttpServletRequest request, HttpServletResponse response, String tourl) {
        String servername = request.getServerName();
        int serverport = request.getServerPort();
        String contextpath = request.getContextPath();

        String baseurl = "http://" + servername;
        if (serverport != 80) {
            baseurl += ":" + String.valueOf(serverport);
        }
        baseurl += contextpath + "/";

        String encodedurl = baseurl + tourl;
        if (urlRewriting) {
            encodedurl = response.encodeURL(encodedurl);
        }

        return encodedurl;
    }

}
