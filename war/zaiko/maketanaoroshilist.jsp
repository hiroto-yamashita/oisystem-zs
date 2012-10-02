<%@ page contentType="text/html; charset=SJIS" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<jsp:useBean id="event" scope="request" class="com.oisix.oisystemzs.eventhandler.MaketanaoroshilistEvent" />

<p>
<%
String filename = event.getFileName();
if (filename == null) {
%>
エラーが発生しました。<br>
<% } else { %>
棚卸リストを作成しました。<br>
<a href="../<%=event.getFileName() %>">ダウンロード</a>
<% } %>
