package com.oisix.oisystemzs.taglib;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.DateUtil;
import com.oisix.oisystemfr.taglib.DBCollectionTagBase;
import com.oisix.oisystemzs.ejb.SoukoData;
import com.oisix.oisystemzs.objectmap.TaniMap;
import com.oisix.oisystemzs.objectmap.YouchuuikubunMap;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class YoteizaikofusokuTag extends DBCollectionTagBase {

    protected Collection findDBCollection(Connection con) throws Exception {
        Debug.println("findDBCollection start", this);
        DecimalFormat df = new DecimalFormat("##########.##");
        Collection col = new LinkedList();
        HttpServletRequest request =
          (HttpServletRequest)pageContext.getRequest();
        if (request.getParameter("year") == null) {
            return null;
        }
        HttpSession session = request.getSession();
        SoukoData souko = (SoukoData)session.getAttribute("SOUKO");
        String souko_id = souko.getSouko_id();
        String[] daibunruis = request.getParameterValues("daibunrui");
        String daibunruiargs = "";
        if (daibunruis != null) {
            for (int i=0; i<daibunruis.length; i++) {
                if (i == 0) {
                    daibunruiargs += " and (daibunrui='" + daibunruis[0] + "'";
                } else {
                    daibunruiargs += " or daibunrui='" + daibunruis[i] + "'";
                }
            }
        }
        if (daibunruiargs.length() > 1) {
            daibunruiargs += ") ";
        }
        String sql = 
          "select " +
          "t1.daibunrui, " +
          "t1.shouhin_id, " +
          "t1.shouhin, " +
          "t1.kikaku, " +
          "t2.suuryou, " +
           "(select sum(nyuukasuuryou-jitsunyuukasuu) " +
            "from zt_nyuukayoteimeisai t3 " +
            "where t3.souko_id=t2.souko_id and t3.shouhin_id=t2.shouhin_id " +
            "and nyuukayotei_date<? and " +
            "(nyuukajoukyou=1 or nyuukajoukyou=2))," +
           "(select sum(shukkayoteisuuryou-jitsushukkasuuryou) " +
            "from zt_shukkayoteimeisai t4,zt_shukkayotei t5 " +
            "where t4.souko_id=t2.souko_id and t4.shouhin_id=t2.shouhin_id " +
            "and t4.shukkayotei_bg=t5.shukkayotei_bg and " +
            "t5.shukkayotei_date<? and " +
            "(t4.shukkajoukyou=1 or t4.shukkajoukyou=2))," +
           "(select sum(suuryou) from zt_zaikomeisai t6 " +
            "where t6.zaiko_id=t2.zaiko_id and shukkakigen<?), " +
          "(select sum(shukkayoteisuuryou-jitsushukkasuuryou) " +
           "from zt_shukkayoteimeisai t7,zt_shukkayotei t8 " +
           "where t7.souko_id=t2.souko_id and t7.shouhin_id=t2.shouhin_id " +
           "and t7.shukkayotei_bg=t8.shukkayotei_bg and " +
           "t8.shukkayotei_date=? and " +
           "(t7.shukkajoukyou=1 or t7.shukkajoukyou=2)) as shukkayoteisuu0," +
          "(select sum(shukkayoteisuuryou-jitsushukkasuuryou) " +
           "from zt_shukkayoteimeisai t9,zt_shukkayotei t10 " +
           "where t9.souko_id=t2.souko_id and t9.shouhin_id=t2.shouhin_id " +
           "and t9.shukkayotei_bg=t10.shukkayotei_bg and " +
           "t10.shukkayotei_date=? and " +
           "(t9.shukkajoukyou=1 or t9.shukkajoukyou=2)) as shukkayoteisuu1," +
          "t1.hacchuuten," +
          "t1.hacchuutani," +
          "t1.hacchuutanisuu," +
          "t1.saiteihacchuusuu, " +
          "t1.irisuu, " +
          "t1.kobetsuhacchuu_flg, " +
          "t1.youchuuihin_flg, " +
          "t1.shukkakigennissuu, " +
          "t1.shiiresaki_id, " +
          "t1.shuubai_flg " +
          "from zm_shouhin t1, zt_zaiko t2 " +
          "where t1.shouhin_id=t2.shouhin_id and " +
          "t2.next_zaiko_id=0 and " +
          "t2.souko_id = ? " +
          daibunruiargs +
          "order by t1.daibunrui,t1.shiiresaki_id";
        PreparedStatement ps = con.prepareStatement(sql);
        Calendar cal = null;
        try {
            String year = request.getParameter("year");
            String month = request.getParameter("month");
            String date = request.getParameter("date");
            cal = DateUtil.getCalendar(year, month, date);
        } catch (NumberFormatException nfe) {
            Debug.println(nfe);
            return null;
        }
        java.util.Date kensakudate = cal.getTime();
        java.sql.Date shukkakigen = new java.sql.Date(cal.getTimeInMillis());
        cal.add(Calendar.DATE, 1);
        java.sql.Date nyuushukkodate =
          new java.sql.Date(cal.getTimeInMillis());
        int index = 1;
        ps.setDate(index++, nyuushukkodate); //ì¸â◊ó\íË
        ps.setDate(index++, nyuushukkodate); //èoâ◊ó\íË
        ps.setDate(index++, shukkakigen); //è‹ñ°ä˙å¿
        //ps.setDate(index++, nyuushukkodate); //è‹ñ°ä˙å¿
        ps.setDate(index++, shukkakigen); //ìñì˙èoâ◊ó\íË
        ps.setDate(index++, nyuushukkodate); //óÇì˙èoâ◊ó\íË
        ps.setString(index++, souko_id);
        ResultSet rs = ps.executeQuery();
        if (rs == null) { Debug.println("rs null", this);return null; }

        //fetch
        HashMap item = null;
        while (rs.next()) {
            index = 1;
            String daibunrui = rs.getString(index++);
            String shouhin_id = rs.getString(index++);
            String shouhin = rs.getString(index++);
            String kikaku = rs.getString(index++);
            float zaikosuu = rs.getFloat(index++);
            float ny = rs.getFloat(index++);
            float sy = rs.getFloat(index++);
            float shukkakigenkire = rs.getFloat(index++);
            zaikosuu = zaikosuu + ny - sy - shukkakigenkire;
            float shukkayoteisuu0 = rs.getFloat(index++);
            float shukkayoteisuu1 = rs.getFloat(index++);
            int hacchuuten = rs.getInt(index++);
            int hacchuutani = rs.getInt(index++);
            int hacchuutanisuu = rs.getInt(index++);
            int saiteihacchuusuu = rs.getInt(index++);
            float irisuu = rs.getFloat(index++);
            int kobetsuhacchuu_flg = rs.getInt(index++);
            int youchuuihin_flg = rs.getInt(index++);
            int shukkakigennissuu = rs.getInt(index++);
            String  shiiresaki_id = rs.getString(index++);
            int shuubai_flg = rs.getInt(index++);
            float fusoku = zaikosuu - hacchuuten;
            //if (fusoku > 0) {
            if (shuubai_flg == 0) {
                if (fusoku >= 0) {
                    continue;
                }
            } else {
                if (zaikosuu >= 0) {
                    continue;
                }
            }
            int hacchuusuuryou =
              (int)Math.ceil((-fusoku) / irisuu / hacchuutanisuu)
              * hacchuutanisuu;
            if (hacchuusuuryou < saiteihacchuusuu) {
                hacchuusuuryou = saiteihacchuusuu;
            }
            item = new HashMap();
            item.put("daibunrui", daibunrui);
            item.put("shouhin_id", shouhin_id);
            item.put("shouhin", shouhin);
            item.put("kikaku", kikaku);
            //item.put("zaikosuu", new Float(zaikosuu));
            String zaikosuuhtml = df.format(zaikosuu);
            if (zaikosuu < 0) {
                zaikosuuhtml = "<span style=\"color:red\">" + zaikosuuhtml +
                  "</span>";
            }
            item.put("zaikosuu", zaikosuuhtml);
            //item.put("shukkayoteisuu0", new Float(shukkayoteisuu0));
            item.put("shukkayoteisuu0", df.format(shukkayoteisuu0));
            //item.put("shukkayoteisuu1", new Float(shukkayoteisuu1));
            item.put("shukkayoteisuu1", df.format(shukkayoteisuu1));
            item.put("hacchuuten", new Integer(hacchuuten));
            //item.put("fusoku", new Float(fusoku));
            item.put("fusoku", df.format(fusoku));
            item.put("hacchuusuuryou", new Integer(hacchuusuuryou));
            item.put("hacchuutani", TaniMap.getTani(hacchuutani).getTani());
            //item.put("irisuu", new Integer(irisuu));
            item.put("irisuu", new Float(irisuu));
            item.put("nyuukasuu", new Float(hacchuusuuryou * irisuu));
            item.put("kobetsuhacchuu_flg", new Integer(kobetsuhacchuu_flg));
            String youchuuikubun = null;
            if (youchuuihin_flg == 0) {
                youchuuikubun = "&nbsp;";
            } else {
                youchuuikubun = YouchuuikubunMap.get(youchuuihin_flg);
            }
            item.put("youchuuikubun", youchuuikubun);
            item.put("shukkakigennissuu", new Integer(shukkakigennissuu));
            // è⁄ç◊ÉäÉìÉNóp
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            String datestr = formatter.format(kensakudate);
            String lineparam = "id=" + shouhin_id + "&souko_id=" + souko_id +
              "&date=" + datestr;
            item.put("lineparam", lineparam);
            col.add(item);

            sql = "select name from zm_shiiresaki where shiiresaki_id=?";
            PreparedStatement ps2 = con.prepareStatement(sql);
            ps2.setString(1, shiiresaki_id);
            ResultSet rs2 = ps2.executeQuery();
            String shiiresaki = null;
            while (rs2.next()) {
                shiiresaki = rs2.getString(1);
            }
            rs2.close();
            ps2.close();
            item.put("shiiresaki", shiiresaki);
	}
        rs.close();
        if (ps!= null) { ps.close(); }
        return col;
    }

}
