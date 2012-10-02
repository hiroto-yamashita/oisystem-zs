<%@ page contentType="text/html; charset=SJIS" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<jsp:useBean id="event" scope="request" class="com.oisix.oisystemzs.eventhandler.MakenouhinshoEvent" />

<p>
<% if (event.getIsError()) { %>
エラーが発生しました。
<% } else { %>
納品書を作成しました。<br>
<a href="../<%=event.getFileName() %>">ダウンロード</a>
<% } %>
