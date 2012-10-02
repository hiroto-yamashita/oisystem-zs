<%@ page contentType="text/html; charset=SJIS" %>
<%
String motoshukko_bg = (String)session.getAttribute("MOTOSHUKKO_BG");
String teiseishukko_bg = (String)session.getAttribute("TEISEIHUKKO_BG");
%>
出庫番号<%=motoshukko_bg%>を訂正しました。<br>
訂正出庫番号：<%=teiseishukko_bg%><br>
