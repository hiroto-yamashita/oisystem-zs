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
SimpleDateFormat sd =new SimpleDateFormat("yyyy�NMM��dd��");
String nyuukayotei_date = sd.format(nyd.getNyuukayotei_date());
String hacchuukubun = HacchuukubunMap.getHacchuukubun(nyd.getHacchuukubun()).getHacchuukubun();
%>
<table>
<tr>
<td class="layout">�����ԍ�</td><td class="layout"><%=hacchuu.getHacchuu_bg() %></td>
<td class="layout">�����敪</td><td class="layout"><%=hacchuukubun%></td>
<td class="layout"></td><td class="layout"></td>
<td class="layout"></td><td class="layout"></td>
<td class="layout"></td><td class="layout"></td>
</tr>
<tr>
<td class="layout">�d����R�[�h</td><td class="layout"><%=shiiresaki.getShiiresaki_id() %></td>
<td class="layout">�d����</td><td class="layout"><%=shiiresaki.getName() %></td>
<td class="layout"></td><td class="layout"></td>
<td class="layout"></td><td class="layout"></td>
<td class="layout"></td><td class="layout"></td>
</tr>
<tr>
<td class="layout">���i�R�[�h</td><td class="layout"><%=sh.getShouhin_id()%></td>
<td class="layout">���i��</td><td class="layout"><%=sh.getShouhin()%></td>
<td class="layout">�K�i</td><td class="layout"><%=sh.getKikaku()%></td>
<td class="layout">���萔</td><td class="layout"><%=sh.getIrisuu()%></td>
<td class="layout">���ח\�萔</td><td class="layout"><%=nyd.getNyuukasuuryou()%></td>
</tr>
<tr>
<td class="layout">���ח\���</td><td class="layout"><%=nyuukayotei_date%></td>
<td class="layout">���ԑ�</td><td class="layout"><%=nyd.getTouchakujikan()%></td>
<td class="layout">���x��</td><td class="layout"><%=od.getOndotai()%></td>
<td class="layout"></td><td class="layout"></td>
<td class="layout"></td><td class="layout"></td>
</tr>
</table>
