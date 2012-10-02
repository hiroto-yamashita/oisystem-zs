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
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyNMMddú");
    java.util.Date lastdate = (java.util.Date)date.get("lastdate");
    java.util.Date firstdate = (java.util.Date)date.get("firstdate");
    String strlastdate = ymd.format(lastdate);
    String sublastdate = "";
    String strlast = sublastdate + strlastdate;
    String strfirstdate = ymd.format(firstdate);
    String subfirstdate = "©";
    String strfirst = subfirstdate + strfirstdate;
    String subdate = strlastdate.substring(0,8);
    //String kubun[] = {"dü^À","ÔiüÉ","UÖüÉ","Iµ²®",
    //                  "THã","THÄ","THdü^À","THÌ£",
    //                  "NSã","NSÄ","NSdü^À","NSÌ£",
    //                  "BtoBã","BtoBÄ","BtoBdü^À","BtoBã",
    //                  "pü","pü(düÔ`)","pü(TS)","ÔioÉ",
    //                  "UÖoÉ","Iµ¸Õ"};
    int index = 0;
    DecimalFormat df = new DecimalFormat("###,###,###,##0");
%>
<%=subdate%>x&nbsp;IµYñ<br>
<br>
<%=strfirst%><br>
<%=strlast%>&nbsp;&nbsp;&nbsp;(PÊF~)<br>
<br><br><br>
<table>
<tr>
<td class="layout"></td><td class="layout"></td><td class="layout"></td>
<td class="layout">(Å)</td>
</tr>
<%
    Iterator iter = tanaoroshilist.iterator();
    while (iter.hasNext()) {
        HashMap item = (HashMap)iter.next();
        index++; 
    if(index==1){
%>
<tr>
<td class="layout">OJz</td>
<%
    //}else if(index==116){
    }else if(index==(zoukakoumoku+genshoukoumoku+1)*daibunrui+1){
%>
<tr>
<td colspan="4" class="layout"><br></td>
</tr>
<tr>
<td class="layout">Jz</td>
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
