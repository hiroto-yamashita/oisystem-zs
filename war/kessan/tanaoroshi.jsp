<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="java.util.*,java.text.*" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%
    LinkedList tanaoroshilist = (LinkedList)session.getAttribute("TANAOROSHILIST");
    LinkedList nyuukokubunlist = (LinkedList)session.getAttribute("NYUUKOKUBUNLIST");
    LinkedList shukkokubunlist = (LinkedList)session.getAttribute("SHUKKOKUBUNLIST");
    LinkedList daibunruilist = (LinkedList)session.getAttribute("DAIBUNRUILIST");
    int zoukakoumoku = nyuukokubunlist.size();
    int genshoukoumoku = shukkokubunlist.size();
    int daibunrui = daibunruilist.size();
    String kubun;
    HashMap date = (HashMap)session.getAttribute("TANAOROSHIDATE");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyy年MM月dd日");
    java.util.Date lastdate = (java.util.Date)date.get("lastdate");
    java.util.Date firstdate = (java.util.Date)date.get("firstdate");
    String strlastdate = ymd.format(lastdate);
    String sublastdate = "至";
    String strlast = sublastdate + strlastdate;
    String strfirstdate = ymd.format(firstdate);
    String subfirstdate = "自";
    String strfirst = subfirstdate + strfirstdate;
    String subdate = strlastdate.substring(0,8);
    //String kubun[] = {"仕入運賃","返品入庫","振替入庫","棚卸調整",
    //                  "TH売上","TH再送","TH仕入運賃","TH販促",
    //                  "NS売上","NS再送","NS仕入運賃","NS販促",
    //                  "BtoB売上","BtoB再送","BtoB仕入運賃","BtoB売上",
    //                  "廃棄","廃棄(仕入赤伝)","廃棄(サン負担)","返品出庫",
    //                  "振替出庫","棚卸減耗"};
    int index = 0;
    DecimalFormat df = new DecimalFormat("###,###,###,##0");
%>
<%=subdate%>度&nbsp;棚卸資産報告書<br>
<br>
<%=strfirst%><br>
<%=strlast%>&nbsp;&nbsp;&nbsp;(単位：円)<br>
<br><br><br>
<table>
<tr>
<td class="layout"></td><td class="layout"></td><td class="layout"></td>
<td class="layout">(税込)</td>
</tr>
<%
    Iterator iter = tanaoroshilist.iterator();
    while (iter.hasNext()) {
        HashMap item = (HashMap)iter.next();
        index++; 
    if(index==1){
%>
<tr>
<td class="layout">前月繰越</td>
<%
    //}else if(index==116){
    }else if(index==(zoukakoumoku+genshoukoumoku+1)*daibunrui+1){
%>
<tr>
<td colspan="4" class="layout"><br></td>
</tr>
<tr>
<td class="layout">次月繰越</td>
<%
    //}else if(index%5==1&&index!=116){
    }else if(index%daibunrui==1&&index!=(zoukakoumoku+genshoukoumoku+1)*daibunrui+1){
%>
<tr>
<td colspan="4" class="layout"><br></td>
</tr>
<tr>
<td class="layout"><%
    if(index/daibunrui<1+zoukakoumoku){
        HashMap nyuukoMap = (HashMap)nyuukokubunlist.get(index/daibunrui-1);
        kubun = (String)nyuukoMap.get("nyuukokubun");
    }else{
        HashMap shukkoMap = (HashMap)shukkokubunlist.get(index/daibunrui-1-zoukakoumoku);
        kubun = (String)shukkoMap.get("shukkokubun");
    }
%><%=kubun%></td>
<%
    //}else if(index%5==0){
    }else if(index%daibunrui==0){
%>
<tr>
<td colspan="4" style="background-color:#000000"></td>
</tr>
<tr>
<td class="layout"></td>
<%
    }else{
%>
<tr>
<td class="layout"></td>
<%
    }
%>
<td class="layout"><%=item.get("daibunrui")%></td>
<%
int kingaku = ((Float)item.get("kingaku")).intValue();
int zeikomikingaku = ((Float)item.get("zeikomikingaku")).intValue();
String strkingaku = df.format(kingaku);
String strzeikomi = df.format(zeikomikingaku);
%>
<td align="right" class="layout"><%=strkingaku%></td>
<td align="right" class="layout"><%=strzeikomi%></td>
</tr>
<%
    }
%>
</table>
