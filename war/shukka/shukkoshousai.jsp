<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="com.oisix.oisystemzs.ejb.*,java.util.*,java.text.*,com.oisix.oisystemzs.objectmap.*,com.oisix.oisystemzs.Names" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<jsp:useBean id="event" scope="request" class="com.oisix.oisystemzs.eventhandler.ShukkoshousaiEvent" />
<%
ShukkoData shukko = event.getShukko();
ShouhinLocal shouhin = event.getShouhin();
UserLocal user = (UserLocal)session.getAttribute("USER");

SimpleDateFormat sdf = new SimpleDateFormat("yyyyNMdú");
String strShukko_date = sdf.format(shukko.getShukko_date());
String strShoumikigen = "Èµ";
if (shukko.getShoumikigen() != null) {
  strShoumikigen = sdf.format(shukko.getShoumikigen());
}

String shukkokubun = ShukkokubunMap.getShukkokubun(shukko.getShukkokubun()).getShukkokubun();
%>
<osx:form action="shukka/shukkoteisei.ev" method="post">
<input type="hidden" name="shukko_id" value="<%=shukko.getShukko_id()%>">
<table width="60%"><tr>
<th class="layout">oÉÔ</th>
<td class="layout" colspan="5"><%=shukko.getShukko_bg()%></td>
</tr><tr>
<th class="layout">oÉú</th>
<td class="layout"><%=strShukko_date%></td>
<th class="layout">oÉæª</th>
<td class="layout" colspan="3"><%=shukkokubun%></td>
</tr><tr>
<th class="layout">¤iR[h</th>
<th class="layout">¤i¼</th>
<th class="layout">Ê</th>
<th class="layout">PÊ</th>
<th class="layout">P¿</th>
<th class="layout">àz</th>
</tr><tr>
<td class="layout"><%=shukko.getShouhin_id()%></td>
<td class="layout"><%=shouhin.getShouhin()%></td>
<td class="layout"><%=shukko.getSuuryou()%></td>
<td class="layout"><%=shukko.getTani()%></td>
<td class="layout"><%=shukko.getTanka()%></td>
<td class="layout"><%=shukko.getKingaku()%></td>
</tr><tr>
<th class="layout">Ü¡úÀ</th>
<td class="layout" colspan="5"><%=strShoumikigen%></td>
</tr><tr>
<td class="layout" colspan="6" align="center">
<% if (user.getPriv() != 3 && shukko.getTeisei_flg() == Names.OFF) { %>
<input type="submit" value="ù³">
<% } %></td>
</tr></table>
</osx:form>