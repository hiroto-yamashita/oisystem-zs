<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="com.oisix.oisystemzs.ejb.*,java.util.*,java.text.SimpleDateFormat,com.oisix.oisystemzs.objectmap.*" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<jsp:useBean id="event" scope="request" class="com.oisix.oisystemzs.eventhandler.NyuukayoteishousaiEvent" />
<%
HacchuuLocal hacchuu = event.getHacchuu();
ShiiresakiData shiiresaki = event.getShiiresaki();
Collection nyuukayoteimeisai = event.getNyuukayoteimeisai();
Iterator iter = nyuukayoteimeisai.iterator();

HashMap meisailine = (HashMap)iter.next();
NyuukayoteimeisaiData nyd = (NyuukayoteimeisaiData)
meisailine.get("NYUUKAYOTEIMEISAI");
ShouhinLocal sh = (ShouhinLocal)meisailine.get("SHOUHIN");
OndotaiData od = (OndotaiData)meisailine.get("ONDOTAI");
SimpleDateFormat sd =new SimpleDateFormat("yyyy年MM月dd日");
String nyuukayotei_date = sd.format(nyd.getNyuukayotei_date());
String hacchuukubun = HacchuukubunMap.getHacchuukubun(nyd.getHacchuukubun()).getHacchuukubun();
%>
<table>
<tr>
<td class="layout">発注番号</td><td class="layout"><%=hacchuu.getHacchuu_bg() %></td>
<td class="layout">発注区分</td><td class="layout"><%=hacchuukubun%></td>
<td class="layout"></td><td class="layout"></td>
<td class="layout"></td><td class="layout"></td>
<td class="layout"></td><td class="layout"></td>
</tr>
<tr>
<td class="layout">仕入先コード</td><td class="layout"><%=shiiresaki.getShiiresaki_id() %></td>
<td class="layout">仕入先</td><td class="layout"><%=shiiresaki.getName() %></td>
<td class="layout"></td><td class="layout"></td>
<td class="layout"></td><td class="layout"></td>
<td class="layout"></td><td class="layout"></td>
</tr>
<tr>
<td class="layout">商品コード</td><td class="layout"><%=sh.getShouhin_id()%></td>
<td class="layout">商品名</td><td class="layout"><%=sh.getShouhin()%></td>
<td class="layout">規格</td><td class="layout"><%=sh.getKikaku()%></td>
<td class="layout">入り数</td><td class="layout"><%=sh.getIrisuu()%></td>
<td class="layout">入荷予定数</td><td class="layout"><%=nyd.getNyuukasuuryou()%></td>
</tr>
<tr>
<td class="layout">入荷予定日</td><td class="layout"><%=nyuukayotei_date%></td>
<td class="layout">時間帯</td><td class="layout"><%=nyd.getTouchakujikan()%></td>
<td class="layout">温度帯</td><td class="layout"><%=od.getOndotai()%></td>
<td class="layout"></td><td class="layout"></td>
<td class="layout"></td><td class="layout"></td>
</tr>
</table>
