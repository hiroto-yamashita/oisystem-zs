<%@ page contentType="text/html; charset=SJIS" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<jsp:useBean id="event" scope="request" class="com.oisix.oisystemzs.eventhandler.MaketanaoroshilistEvent" />

<p>
<%
String filename = event.getFileName();
if (filename == null) {
%>
�G���[���������܂����B<br>
<% } else { %>
�I�����X�g���쐬���܂����B<br>
<a href="../<%=event.getFileName() %>">�_�E�����[�h</a>
<% } %>
