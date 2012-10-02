package com.oisix.oisystemzs.taglib;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.DateUtil;
import com.oisix.oisystemfr.ServiceLocator;
import java.io.IOException;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Iterator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import javax.sql.DataSource;
import javax.naming.NamingException;

public class ShouhindaibunruiTag extends TagSupport {

    private String name;
    private String type = "select";
    private String defaultval;
    private String tsuika;

    public void setName(String str) { name = str; }
    public void setType(String str) { type = str; }
    public void setDefaultval(String str) { defaultval = str; }
    public void setTsuika(String str) { tsuika = str; }

    public int doStartTag() throws JspTagException {
        DataSource ds = null;
        try {
            ds = ServiceLocator.getDataSource();
        } catch (NamingException ne) {
            ne.printStackTrace();
            throw new JspTagException("NamingException finding DataSource");
        }
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        LinkedList bunruilist = new LinkedList();
        try {
            con = ds.getConnection();
            String sql = "select distinct daibunrui from zm_shouhin " +
                         "order by daibunrui";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String bunrui = rs.getString(1);
                if (bunrui != null) {
                    bunruilist.add(bunrui);
                }
            }
            ps.close();
            rs.close();
            con.close();
            if (tsuika != null) {
                bunruilist.add(tsuika);
            }
        } catch (SQLException se) {
            se.printStackTrace();
            throw new JspTagException("SQLException");
        } finally {
            try {
                if (rs != null) { rs.close(); }
                if (ps != null) { ps.close(); }
                if (con != null) { con.close(); }
            } catch (SQLException se) {
                se.printStackTrace();
                throw new JspTagException("SQLException");
            }
        }

        HttpServletRequest request =
          (HttpServletRequest)pageContext.getRequest();
        String param = request.getParameter(name);
        if (param == null) {
            HttpSession session = request.getSession();
            HashMap inputval = (HashMap)session.getAttribute("INPUTVALUE");
            if (inputval != null) {
                String[] params = (String[])inputval.get(name);
                if (params != null) param = params[0];
            }
        }
        if (param == null) {
            param = defaultval;
        }

        try {
            JspWriter out = pageContext.getOut();
            if (type.equals("select")) {
                out.print("<select name=\"");
                out.print(name);
                out.print("\">");
                Iterator iter = bunruilist.iterator();
                while (iter.hasNext()) {
                    String value = (String)iter.next();
                    //java.util.logging.Logger.global.info("value:"+value);
                    out.print("<option value=\"");
                    out.print(value);
                    out.print("\" ");
                    //if (value.equals(defaultval)) {
                    if (value.equals(param)) {
                        out.print("selected ");
                    }
                    out.print(">");
                    out.print(value);
                }
                out.println("</select>");
            } else {
                Iterator iter = bunruilist.iterator();
                while (iter.hasNext()) {
                    String value = (String)iter.next();
                    out.print("<input type=\"checkbox\" name=\"");
                    out.print(name);
                    out.print("\" value=\"");
                    out.print(value);
                    out.print("\" ");
                    //if (value.equals(defaultval)) {
                    if (value.equals(param)) {
                        out.print("checked ");
                    }
                    out.print(">");
                    out.print(value);
                    out.print("<br>");
                }
            }
        } catch (IOException ioe) {
            throw new JspTagException("error printing taniselecttag");
        }

        return (SKIP_BODY);
    }

}
