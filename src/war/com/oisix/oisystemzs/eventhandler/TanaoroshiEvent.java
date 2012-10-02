package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.ServiceLocator;
import com.oisix.oisystemfr.EventHandlerSupport;
import com.oisix.oisystemzs.ejb.SoukoData;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.naming.NamingException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TanaoroshiEvent extends EventHandlerSupport {

    private String filename;
    private int lastyear;
    private int thisyear;
    private int lastmonth;
    private int thismonth;
    private LinkedList tmtanaoroshilist;
    private LinkedList lmtanaoroshilist;
    private LinkedList nyuukolist;
    private LinkedList shukkolist;
    private HashMap date;
    private LinkedList daibunruilist = null;
    private LinkedList shukkokubunlist = null;
    private LinkedList nyuukokubunlist = null;
    private HashMap sizeMap;
    private float goukei = 0;
    private float zeikomigoukei = 0;
    private LinkedList tanaoroshilist;
    private int ind;
    private int firstflg = 0;

    private String souko_id;
    
    public void init(HttpServletRequest request) {
        String yearstr = request.getParameter("#year");
        String monthstr = request.getParameter("#month");
        thisyear = 2002;
        lastyear = 2002;
        thismonth = 1;
        lastmonth = 1;
        try {
            thisyear = Integer.parseInt(yearstr);
            thismonth = Integer.parseInt(monthstr);
            if(thismonth==1){
                lastmonth=12;
                lastyear = thisyear - 1;
            }else{
                lastmonth = thismonth - 1;
                lastyear = thisyear;
            }
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }

        SoukoData souko = (SoukoData)session.getAttribute("SOUKO");
        souko_id = souko.getSouko_id();
    }

    public void handleEvent(HashMap attr) {
        Connection con = null;
        PreparedStatement lmps = null;
        ResultSet lmrs = null;
        PreparedStatement tmps = null;
        ResultSet tmrs = null;
        PreparedStatement nps = null;
        ResultSet nrs = null;
        PreparedStatement sps = null;
        ResultSet srs = null;
        PreparedStatement skps = null;
        ResultSet skrs = null;
        PreparedStatement nkps = null;
        ResultSet nkrs = null;
        PreparedStatement dps = null;
        ResultSet drs = null;

        try {
            DataSource ds = ServiceLocator.getDataSource();
            con = ds.getConnection();

            String sksql = makeShukkokubunlistSql();
            skps = con.prepareStatement(sksql);
            skrs = skps.executeQuery();
            if (skrs == null) {
                Debug.println("skrs null", this);
                return;
            }
            //出庫区分リストの作成
            shukkokubunlist = new LinkedList();
            HashMap skitem = null;
            while (skrs.next()) {
                skitem = new HashMap();
                int index = 1;
                String shukkokubunstr = skrs.getString(index++);
                if(shukkokubunstr!=null&&!shukkokubunstr.equals("")){
                    skitem.put("shukkokubun",shukkokubunstr);
                    shukkokubunlist.add(skitem);
                }
            }
            skrs.close();
            skps.close();
            
            String nksql = makeNyuukokubunlistSql();
            nkps = con.prepareStatement(nksql);
            nkrs = nkps.executeQuery();
            if (nkrs == null) {
                Debug.println("nkrs null", this);
                return;
            }
            //入庫区分リストの作成
            nyuukokubunlist = new LinkedList();
            HashMap nkitem = null;
            while (nkrs.next()) {
                nkitem = new HashMap();
                int index = 1;
                String nyuukokubunstr = nkrs.getString(index++);
                if(nyuukokubunstr!=null&&!nyuukokubunstr.equals("")){
                    nkitem.put("nyuukokubun",nyuukokubunstr);
                    nyuukokubunlist.add(nkitem);
                }
            }
            nkrs.close();
            nkps.close();
            
            String dsql = makeDaibunruilistSql();
            dps = con.prepareStatement(dsql);
            drs = dps.executeQuery();
            if (drs == null) {
                Debug.println("drs null", this);
                return;
            }
            //大分類リストの作成
            daibunruilist = new LinkedList();
            HashMap ditem = null;
            while (drs.next()) {
                ditem = new HashMap();
                int index = 1;
                String daibunruistr = drs.getString(index++);
                if(daibunruistr!=null&&!daibunruistr.equals("")){
                    ditem.put("daibunrui",daibunruistr);
                    daibunruilist.add(ditem);
                }
            }
            String strgoukei = "合計";
            HashMap goukeiMp = null;
            goukeiMp = new HashMap();
            goukeiMp.put("daibunrui",strgoukei);
            daibunruilist.add(goukeiMp);
            drs.close();
            dps.close();

            String lmsql = makelmSql();
            lmps = con.prepareStatement(lmsql);
            lmrs = lmps.executeQuery();
            if (lmrs == null) {
                Debug.println("lmrs null", this);
                return;
            }
            lmtanaoroshilist = new LinkedList();
            HashMap lmitem = null;
            while (lmrs.next()) {
                //前月繰越の情報取得
                lmitem = new HashMap();
                int index = 1;
                Float kingaku = null;
                kingaku = new Float(lmrs.getFloat(index++));
                lmitem.put("kingaku",kingaku);
                lmitem.put("zeikomikingaku",new Float(lmrs.getFloat(index++)));
                String strdaibunrui = lmrs.getString(index++);
                lmitem.put("daibunrui",strdaibunrui);
                lmtanaoroshilist.add(lmitem);
            }
            lmrs.close();
            lmps.close();
            
            String tmsql = maketmSql();
            tmps = con.prepareStatement(tmsql);
            tmrs = tmps.executeQuery();
            if (tmrs == null) {
                Debug.println("tmrs null", this);
                return;
            }
            tmtanaoroshilist = new LinkedList();
            HashMap tmitem = null;
            while (tmrs.next()) {
                //今月繰越の情報取得
                tmitem = new HashMap();
                int index = 1;
                Float kingaku = null;
                kingaku = new Float(tmrs.getFloat(index++));
                tmitem.put("kingaku",kingaku);
                tmitem.put("zeikomikingaku",new Float(tmrs.getFloat(index++)));
                String strdaibunrui = tmrs.getString(index++);
                tmitem.put("daibunrui",strdaibunrui);
                tmtanaoroshilist.add(tmitem);
            }
            tmrs.close();
            tmrs.close();
            
            //増加項目
            nyuukolist = new LinkedList();
            String nsql = makeNyuukoSql();
            nps = con.prepareStatement(nsql);
            nrs = nps.executeQuery();
            if (nrs == null) {
                Debug.println("nrs null", this);
                return;
            }
            HashMap nitem = null;
            while (nrs.next()) {
                //入庫区分ごとの増加項目の情報取得
                nitem = new HashMap();
                int index = 1;
                Float kingaku = null;
                kingaku =new Float(nrs.getFloat(index++)) ;
                nitem.put("kingaku",kingaku);
                nitem.put("zeikomikingaku",new Float(nrs.getFloat(index++)));
                String strdaibunrui = nrs.getString(index++);
                nitem.put("daibunrui",strdaibunrui);
                nitem.put("kubun",nrs.getString(index++));
                nyuukolist.add(nitem);
            }
            nrs.close();
            nps.close();
            
            //減少項目
            shukkolist = new LinkedList();
            String ssql = makeShukkoSql();
            sps = con.prepareStatement(ssql);
            srs = sps.executeQuery();
            if (srs == null) {
                Debug.println("srs null", this);
                return;
            }
            HashMap sitem = null;
            while (srs.next()) {
                //出庫区分ごとの減少項目の情報取得
                sitem = new HashMap();
                int index = 1;
                Float kingaku = null;
                kingaku = new Float(srs.getFloat(index++));
                sitem.put("kingaku",kingaku);
                sitem.put("zeikomikingaku",new Float(srs.getFloat(index++)));
                String strdaibunrui = srs.getString(index++);
                sitem.put("daibunrui",strdaibunrui);
                sitem.put("kubun",srs.getString(index++));
                shukkolist.add(sitem);
            }
            srs.close();
            sps.close();
            
            //金額など０で全て初期化
            Float zero = new Float(0);
            tanaoroshilist = new LinkedList();
            for(int i=0;i<nyuukokubunlist.size()+shukkokubunlist.size()+2;i++){
                for(int j=0;j<daibunruilist.size();j++){
                    HashMap initMap = new HashMap();
                    HashMap daibunruiMap = new HashMap();
                    String goukeistr = "合計";
                    initMap.put("kingaku",zero);
                    initMap.put("zeikomikingaku",zero);
                    if(j==daibunruilist.size()-1){
                        initMap.put("daibunrui",goukeistr);
                    }else{
                        daibunruiMap = (HashMap)daibunruilist.get(j);
                        String daibunrui = (String)daibunruiMap.get("daibunrui");
                        initMap.put("daibunrui",daibunrui);
                    }
                    tanaoroshilist.add(initMap);
                }
            }
            //前月繰越データ作成
            ind = 0;
            for(int i=0;i<daibunruilist.size();i++){
            Iterator lmiter = lmtanaoroshilist.iterator();
                while (lmiter.hasNext()) {
                    HashMap lm = (HashMap)lmiter.next();
                    makeTanaoroshiList(lm,daibunruilist,i,ind);
                }
                if(i==daibunruilist.size()-1){
                    makeGoukeiList(i,daibunruilist,ind);
                }
            }
            ind++;
            //増加項目データ作成
            
            for(int j=0;j<nyuukokubunlist.size();j++){
                for(int i=0;i<daibunruilist.size();i++){
                    Iterator nyiter = nyuukolist.iterator();
                    while (nyiter.hasNext()) {
                        HashMap ny = (HashMap)nyiter.next();
                        makeNyuukoList(ny,nyuukokubunlist,daibunruilist,i,j,ind);
                    }
                    if(i==daibunruilist.size()-1){
                        makeGoukeiList(i,daibunruilist,ind);
                    }
                }
            ind++;
            }
            //減少項目データ作成
            
            for(int j=0;j<shukkokubunlist.size();j++){
                for(int i=0;i<daibunruilist.size();i++){
                    Iterator shiter = shukkolist.iterator();
                    while (shiter.hasNext()) {
                        HashMap sh = (HashMap)shiter.next();
                        makeShukkoList(sh,shukkokubunlist,daibunruilist,i,j,ind);
                    }
                    if(i==daibunruilist.size()-1){
                        makeGoukeiList(i,daibunruilist,ind);
                    }
                }
                ind++;
            }
            //今月繰越データ作成
            for(int i=0;i<daibunruilist.size();i++){
                Iterator tmiter = tmtanaoroshilist.iterator();
                while (tmiter.hasNext()) {
                    HashMap tm = (HashMap)tmiter.next();
                    makeTanaoroshiList(tm,daibunruilist,i,ind);
                }
                if(i==daibunruilist.size()-1){
                    makeGoukeiList(i,daibunruilist,ind);
                }
            }
            //在庫日付の取得
            date = new HashMap();
            int ind = 1;
            Calendar firstcal = Calendar.getInstance();
            Calendar lastcal = Calendar.getInstance();
            firstcal.set(thisyear,thismonth-1,1);
            int lastday = firstcal.getActualMaximum(firstcal.DAY_OF_MONTH);
            lastcal.set(thisyear,thismonth-1,lastday);
            java.util.Date firstdate = firstcal.getTime();
            java.util.Date lastdate = lastcal.getTime();
            date.put("lastdate",lastdate);
            date.put("firstdate",firstdate);
        } catch (NamingException nme) {
            Debug.println(nme);
        } catch (SQLException sqle) {
            Debug.println(sqle);
        } finally {
            try {
                if (con != null) { con.close(); }
                if (nkrs != null) { nkrs.close(); }
                if (nkps != null) { nkps.close(); }
                if (skrs != null) { skrs.close(); }
                if (skps != null) { skps.close(); }
                if (drs != null) { drs.close(); }
                if (dps != null) { dps.close(); }
                if (lmrs != null) { lmrs.close(); }
                if (lmps != null) { lmps.close(); }
                if (tmrs != null) { tmrs.close(); }
                if (tmps != null) { tmps.close(); }
                if (nrs != null) { nrs.close(); }
                if (nps != null) { nps.close(); }
                if (srs != null) { srs.close(); }
                if (sps != null) { sps.close(); }
            }catch (SQLException sqle) { Debug.println(sqle); }
        }
    }
    public String makelmSql() {
        String lmsql =
          "SELECT " + 
          "SUM(t1.KINGAKU), " + 
          "SUM(1.05*t1.KINGAKU), " + 
          "t2.DAIBUNRUI " + 
          "FROM ZT_ZAIKO t1, ZM_SHOUHIN t2, " +
          "(SELECT MAX(ZORDER) AS MZORDER,SHOUHIN_ID,SOUKO_ID " + 
          " FROM  ZT_ZAIKO " +
          " WHERE ZAIKODATE<'" + thisyear + "-" + thismonth + "-01' " +
          " AND SOUKO_ID='" + souko_id + "' " +
          " GROUP BY SOUKO_ID, SHOUHIN_ID) t3 " + 
          "WHERE t1.SHOUHIN_ID = t2.SHOUHIN_ID " +
          "AND t1.ZORDER=t3.MZORDER " +
          "AND t1.SHOUHIN_ID=t3.SHOUHIN_ID " +
          "AND t1.SOUKO_ID=t3.SOUKO_ID " +
          "GROUP BY t2.DAIBUNRUI";
        Debug.println(lmsql);
        return lmsql;
    }
    public String maketmSql() {
        String tmsql =
          "SELECT " + 
          "SUM(t1.KINGAKU), " + 
          "SUM(1.05*t1.KINGAKU), " + 
          "t2.DAIBUNRUI " + 
          "FROM ZT_ZAIKO t1, ZM_SHOUHIN t2, ";
        Iterator diter = daibunruilist.iterator();
        HashMap daibunruiMap = null;  
        int counter = 0;
        int nextyear = thisyear;
        int nextmonth = thismonth + 1;
        if (nextmonth == 13) {
            nextyear++;
            nextmonth = 1;
        }
        tmsql += "(SELECT MAX(ZORDER) AS MZORDER,SHOUHIN_ID,SOUKO_ID " +
          " FROM  ZT_ZAIKO " +
          " WHERE ZAIKODATE<'" + nextyear + "-" + nextmonth + "-01' " +
          " AND SOUKO_ID='" + souko_id + "'" +
          " GROUP BY SHOUHIN_ID,SOUKO_ID) t3 " +
          "WHERE t1.SHOUHIN_ID = t2.SHOUHIN_ID " +
          "AND t1.ZORDER=t3.MZORDER " +
          "AND t1.SHOUHIN_ID=t3.SHOUHIN_ID " +
          "AND t1.SOUKO_ID=t3.SOUKO_ID " +
          "GROUP BY t2.DAIBUNRUI ";
        Debug.println(tmsql);
        return tmsql;
    }
    public String makeNyuukoSql() {
        String nsql =
          "SELECT " + 
          "SUM(t1.NYUUKOSUURYOU*t1.NYUUKOTANKA), " + 
          "SUM(1.05*t1.NYUUKOSUURYOU*t1.NYUUKOTANKA), " + 
          "t2.DAIBUNRUI, t3.NYUUKOKUBUN  " + 
          "FROM ZT_NYUUKO t1, ZM_SHOUHIN t2 , ZM_NYUUKOKUBUN t3  " +
          "WHERE t1.SHOUHIN_ID = t2.SHOUHIN_ID " +
          "AND t1.SOUKO_ID ='" + souko_id + "' " +
          "AND t1.NYUUKOKUBUN = t3.NYUUKOKUBUN_ID ";
        nsql += " AND YEAR(t1.NYUUKO_DATE) = " + thisyear +
               " AND MONTH(t1.NYUUKO_DATE) = " + thismonth;
        Iterator diter = daibunruilist.iterator();
        HashMap daibunruiMap = null;  
        int counter = 0;
        StringBuffer sb = new StringBuffer("AND t2.DAIBUNRUI IN (");
        while (diter.hasNext()) {
            daibunruiMap = (HashMap)diter.next();
            String daibunruistr = (String)daibunruiMap.get("daibunrui");
            sb.append("'").append(daibunruistr).append("'");
            if (counter < (daibunruilist.size() - 1)) {
                sb.append(", ");
            }
            counter++;
        }
        sb.append(")");
        nsql += sb;
        nsql += " GROUP BY t3.NYUUKOKUBUN, t2.DAIBUNRUI " +
               " ORDER BY t3.NYUUKOKUBUN";
        Debug.println(nsql);
        return nsql;
    }
    public String makeShukkoSql() {
        String ssql =
          "SELECT " + 
          "SUM(t1.KINGAKU), " + 
          "SUM(1.05*KINGAKU), " + 
          "t2.DAIBUNRUI, t3.SHUKKOKUBUN  " + 
          "FROM ZT_SHUKKO t1, ZM_SHOUHIN t2 , ZM_SHUKKOKUBUN t3  " +
          "WHERE t1.SHOUHIN_ID = t2.SHOUHIN_ID " +
          "AND t1.SOUKO_ID = '" + souko_id + "' " +
          "AND t1.SHUKKOKUBUN = t3.SHUKKOKUBUN_ID ";
        ssql += " AND YEAR(t1.SHUKKO_DATE) = " + thisyear +
               " AND MONTH(t1.SHUKKO_DATE) = " + thismonth;
        Iterator diter = daibunruilist.iterator();
        HashMap daibunruiMap = null;  
        int counter = 0;
        StringBuffer sb = new StringBuffer("AND t2.DAIBUNRUI IN (");
        while (diter.hasNext()) {
            daibunruiMap = (HashMap)diter.next();
            String daibunruistr = (String)daibunruiMap.get("daibunrui");
            sb.append("'").append(daibunruistr).append("'");
            if (counter < (daibunruilist.size() - 1)) {
                sb.append(", ");
            }
            counter++;
        }
        sb.append(")");
        ssql += sb;
        ssql += " GROUP BY t3.SHUKKOKUBUN, t2.DAIBUNRUI " +
               " ORDER BY t3.SHUKKOKUBUN";
        Debug.println(ssql);
        return ssql;
    }
    public String makeShukkokubunlistSql() {
        String sksql =
          "SELECT " + 
          "SHUKKOKUBUN  " + 
          "FROM ZM_SHUKKOKUBUN " +
          "ORDER BY SHUKKOKUBUN_ID";
        Debug.println(sksql);
        return sksql;
    }
    public String makeNyuukokubunlistSql() {
        String nksql =
          "SELECT " + 
          "NYUUKOKUBUN  " + 
          "FROM ZM_NYUUKOKUBUN " +
          "ORDER BY NYUUKOKUBUN_ID";
        Debug.println(nksql);
        return nksql;
    }
    public String makeDaibunruilistSql() {
        String dsql =
          "SELECT " + 
          "DISTINCT DAIBUNRUI  " + 
          "FROM ZM_SHOUHIN " +
          "ORDER BY DAIBUNRUI";
        Debug.println(dsql);
        return dsql;
    }
    public void makeTanaoroshiList(HashMap item, LinkedList daibunruilist, int i, int index) {
        Iterator diter = daibunruilist.iterator();
        String bunrui = (String)item.get("daibunrui");
        HashMap daibunruiMap = null;
        if(diter.hasNext()) {
            daibunruiMap = (HashMap)daibunruilist.get(i);
            String daibunrui = (String)daibunruiMap.get("daibunrui");
            if(daibunrui.equals(bunrui)){
                Float kingaku = (Float)item.get("kingaku");
                Float zeikomikingaku = (Float)item.get("zeikomikingaku");
                float flkingaku = kingaku.floatValue();
                goukei = goukei+flkingaku;
                float flzeikomikingaku = zeikomikingaku.floatValue();
                zeikomigoukei = zeikomigoukei + flzeikomikingaku;
                HashMap tanaoroshiMap = new HashMap();
                if(i==daibunruilist.size()){
                    Float gk = new Float(goukei);
                    Float zgk = new Float(zeikomigoukei);
                    tanaoroshiMap.put("kingaku",gk);
                    tanaoroshiMap.put("zeikomikingaku",zgk);
                    goukei=0;
                    zeikomigoukei=0;
                }else{
                    tanaoroshiMap.put("kingaku",kingaku);
                    tanaoroshiMap.put("zeikomikingaku",zeikomikingaku);
                }
                tanaoroshiMap.put("daibunrui",bunrui);
                tanaoroshilist.set(i+daibunruilist.size()*index,tanaoroshiMap);
            }
        }
    }
    public void makeNyuukoList(HashMap item, LinkedList nyuukokubunlist, 
      LinkedList daibunruilist, int i, int j, int index) {
        String kubun = (String)item.get("kubun");
        String bunrui = (String)item.get("daibunrui");
        Iterator iter = nyuukokubunlist.iterator();
        HashMap nyuukokubunMap = null;
        if(iter.hasNext()) {
            nyuukokubunMap = (HashMap)nyuukokubunlist.get(j);
            String nyuukokubun = (String)nyuukokubunMap.get("nyuukokubun");
            HashMap daibunruiMap = null;
            if(kubun.equals(nyuukokubun)){
                daibunruiMap = (HashMap)daibunruilist.get(i);
                String daibunrui = (String)daibunruiMap.get("daibunrui");
                if(daibunrui.equals(bunrui)){
                    Float kingaku = (Float)item.get("kingaku");
                   Float zeikomikingaku = (Float)item.get("zeikomikingaku");
                    float flkingaku = kingaku.floatValue();
                    goukei = goukei+flkingaku;
                    float flzeikomikingaku = zeikomikingaku.floatValue();
                    zeikomigoukei = zeikomigoukei + flzeikomikingaku;
                    HashMap tanaoroshiMap = new HashMap();
                    if(i==daibunruilist.size()){
                        Float gk = new Float(goukei);
                        Float zgk = new Float(zeikomigoukei);
                        tanaoroshiMap.put("kingaku",gk);
                        tanaoroshiMap.put("zeikomikingaku",zgk);
                        goukei=0;
                        zeikomigoukei=0;
                    }else{
                        tanaoroshiMap.put("kingaku",kingaku);
                        tanaoroshiMap.put("zeikomikingaku",zeikomikingaku);
                    }
                    tanaoroshiMap.put("daibunrui",bunrui);
                    tanaoroshiMap.put("kubun",kubun);
                    tanaoroshilist.set(i+daibunruilist.size()*index,tanaoroshiMap);
                }
            }
        }
    }
    public void makeShukkoList(HashMap item, LinkedList shukkokubunlist, 
      LinkedList daibunruilist, int i, int j, int index) {
        String kubun = (String)item.get("kubun");
        String bunrui = (String)item.get("daibunrui");
        Iterator iter = shukkokubunlist.iterator();
        HashMap shukkokubunMap = null;
        if(iter.hasNext()) {
            shukkokubunMap = (HashMap)shukkokubunlist.get(j);
            String shukkokubun = (String)shukkokubunMap.get("shukkokubun");
            HashMap daibunruiMap = null;
            if(kubun.equals(shukkokubun)){
                daibunruiMap = (HashMap)daibunruilist.get(i);
                String daibunrui = (String)daibunruiMap.get("daibunrui");
                if(daibunrui.equals(bunrui)){
                    Float kingaku = (Float)item.get("kingaku");
                    Float zeikomikingaku = (Float)item.get("zeikomikingaku");
                    float flkingaku = kingaku.floatValue();
                    goukei = goukei+flkingaku;
                    float flzeikomikingaku = zeikomikingaku.floatValue();
                    zeikomigoukei = zeikomigoukei + flzeikomikingaku;
                    HashMap tanaoroshiMap = new HashMap();
                    if(i==daibunruilist.size()){
                        Float gk = new Float(goukei);
                        Float zgk = new Float(zeikomigoukei);
                        tanaoroshiMap.put("kingaku",gk);
                        tanaoroshiMap.put("zeikomikingaku",zgk);
                        goukei=0;
                        zeikomigoukei=0;
                    }else{
                        tanaoroshiMap.put("kingaku",kingaku);
                        tanaoroshiMap.put("zeikomikingaku",zeikomikingaku);
                    }
                    tanaoroshiMap.put("daibunrui",bunrui);
                    tanaoroshiMap.put("kubun",kubun);
                    tanaoroshilist.set(i+daibunruilist.size()*index,tanaoroshiMap);
                }
            }
        }
    }
    public void makeGoukeiList(int i, LinkedList daibunruilist, int index){
        if(i==daibunruilist.size()-1){
            HashMap goukeiMap = new HashMap();
            Float gk = new Float(goukei);
            Float zgk = new Float(zeikomigoukei);
            goukeiMap.put("kingaku",gk);
            goukeiMap.put("zeikomikingaku",zgk);
            String goukeistr = "合計";
            goukeiMap.put("daibunrui",goukeistr);
            tanaoroshilist.set(daibunruilist.size()-1+daibunruilist.size()*index,goukeiMap);
            goukei=0;
            zeikomigoukei=0;
        }
    }
    public void postHandle(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("TANAOROSHILIST",tanaoroshilist);
        session.setAttribute("NYUUKOKUBUNLIST",nyuukokubunlist);
        session.setAttribute("SHUKKOKUBUNLIST",shukkokubunlist);
        session.setAttribute("DAIBUNRUILIST",daibunruilist);
        session.setAttribute("TANAOROSHIDATE",date);
    }
}