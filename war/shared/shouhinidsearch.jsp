<%@ page contentType="text/html; charset=SJIS" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<jsp:useBean id="event" scope="request" class="com.oisix.oisystemzs.eventhandler.ShouhinidsearchEvent" />
<%
response.setHeader("Expires", "Mon, 26 Jul 1997 05:00:00 GMT"); 
response.setHeader("Last-Modified", "Mon, 26 Jul 1997 05:00:00 GMT");
response.setHeader("Cache-Control","no-cache, must-revalidate");
response.setHeader("Pragma","no-cache");

String formname = request.getParameter("form");
String shouhin_idname = request.getParameter("shouhin_id");
String shouhinname = request.getParameter("shouhin");
String kikakuname = request.getParameter("kikaku");
String tankaname = request.getParameter("tanka");
String taniname = request.getParameter("tani");
String hacchuutaniname = request.getParameter("hacchuutani");
String irisuuname = request.getParameter("irisuu");
String hyoujuntankaname = request.getParameter("hyoujuntanka");
String shoumi_yname = request.getParameter("shoumi_y");
String shoumi_mname = request.getParameter("shoumi_m");
String shoumi_dname = request.getParameter("shoumi_d");
String shukka_yname = request.getParameter("shukka_y");
String shukka_mname = request.getParameter("shukka_m");
String shukka_dname = request.getParameter("shukka_d");
if (event.getResult() == 0) { %>
<script language="JavaScript1.2">
<% if (formname != null && shouhin_idname != null) { %>
if (window.opener.<%=formname%>.<%=shouhin_idname%>) {
window.opener.<%=formname%>.<%=shouhin_idname%>.value = "<%=event.getShouhin_id() %>";
}
if (window.opener.<%=formname%>.elements["#<%=shouhin_idname%>"]) {
window.opener.<%=formname%>.elements["#<%=shouhin_idname%>"].value = "<%=event.getShouhin_id() %>";
}
<% } %>
<% if (shouhinname != null) { %>
if (window.opener.<%=shouhinname%>) {
window.opener.<%=shouhinname%>.innerText = "<%=event.getShouhin() %>";
}
<% } %>
<% if (kikakuname != null) { %>
if (window.opener.<%=kikakuname%>) {
window.opener.<%=kikakuname%>.innerText = "<%=event.getKikaku() %>";
}
<% } %>
<% if (formname != null && tankaname != null) { %>
if (window.opener.<%=formname%>.<%=tankaname%>) {
window.opener.<%=formname%>.<%=tankaname%>.value = "<%=event.getTanka() %>";
}
if (window.opener.<%=formname%>.elements["#<%=tankaname%>"]) {
window.opener.<%=formname%>.elements["#<%=tankaname%>"].value = "<%=event.getTanka() %>";
}
<% } %>
<% if (formname != null && taniname != null) { %>
if (window.opener.<%=formname%>.<%=taniname%>) {
window.opener.<%=formname%>.<%=taniname%>.value = "<%=event.getTani() %>";
}
if (window.opener.<%=formname%>.elements["#<%=taniname%>"]) {
window.opener.<%=formname%>.elements["#<%=taniname%>"].value = "<%=event.getTani() %>";
}
<% } %>
<% if (formname != null && hacchuutaniname != null) { %>
if (window.opener.<%=formname%>.<%=hacchuutaniname%>) {
window.opener.<%=formname%>.<%=hacchuutaniname%>.value = "<%=event.getHacchuutani() %>";
}
if (window.opener.<%=formname%>.elements["#<%=hacchuutaniname%>"]) {
window.opener.<%=formname%>.elements["#<%=hacchuutaniname%>"].value = "<%=event.getHacchuutani() %>";
}
<% } %>
<% if (formname != null && irisuuname != null) { %>
if (window.opener.<%=formname%>.<%=irisuuname%>) {
window.opener.<%=formname%>.<%=irisuuname%>.value = "<%=event.getIrisuu() %>";
}
if (window.opener.<%=formname%>.elements["#<%=irisuuname%>"]) {
window.opener.<%=formname%>.elements["#<%=irisuuname%>"].value = "<%=event.getIrisuu() %>";
}
<% } %>
<% if (formname != null && hyoujuntankaname != null) { %>
if (window.opener.<%=formname%>.<%=hyoujuntankaname%>) {
window.opener.<%=formname%>.<%=hyoujuntankaname%>.value = "<%=event.getHyoujuntanka() %>";
}
if (window.opener.<%=formname%>.elements["#<%=hyoujuntankaname%>"]) {
window.opener.<%=formname%>.elements["#<%=hyoujuntankaname%>"].value = "<%=event.getHyoujuntanka() %>";
}
<% } %>
<% if (formname != null && shoumi_yname != null) { %>
if (window.opener.<%=formname%>.<%=shoumi_yname%>) {
window.opener.<%=formname%>.<%=shoumi_yname%>.value = "<%=event.getShoumi_y() %>";
}
if (window.opener.<%=formname%>.elements["#<%=shoumi_yname%>"]) {
window.opener.<%=formname%>.elements["#<%=shoumi_yname%>"].value = "<%=event.getShoumi_y() %>";
}
<% } %>
<% if (formname != null && shoumi_mname != null) { %>
if (window.opener.<%=formname%>.<%=shoumi_mname%>) {
window.opener.<%=formname%>.<%=shoumi_mname%>.value = "<%=event.getShoumi_m() %>";
}
if (window.opener.<%=formname%>.elements["#<%=shoumi_mname%>"]) {
window.opener.<%=formname%>.elements["#<%=shoumi_mname%>"].value = "<%=event.getShoumi_m() %>";
}
<% } %>
<% if (formname != null && shoumi_dname != null) { %>
if (window.opener.<%=formname%>.<%=shoumi_dname%>) {
window.opener.<%=formname%>.<%=shoumi_dname%>.value = "<%=event.getShoumi_d() %>";
}
if (window.opener.<%=formname%>.elements["#<%=shoumi_dname%>"]) {
window.opener.<%=formname%>.elements["#<%=shoumi_dname%>"].value = "<%=event.getShoumi_d() %>";
}
<% } %>
<% if (formname != null && shukka_yname != null) { %>
if (window.opener.<%=formname%>.<%=shukka_yname%>) {
window.opener.<%=formname%>.<%=shukka_yname%>.value = "<%=event.getShukka_y() %>";
}
if (window.opener.<%=formname%>.elements["#<%=shukka_yname%>"]) {
window.opener.<%=formname%>.elements["#<%=shukka_yname%>"].value = "<%=event.getShukka_y() %>";
}
<% } %>
<% if (formname != null && shukka_mname != null) { %>
if (window.opener.<%=formname%>.<%=shukka_mname%>) {
window.opener.<%=formname%>.<%=shukka_mname%>.value = "<%=event.getShukka_m() %>";
}
if (window.opener.<%=formname%>.elements["#<%=shukka_mname%>"]) {
window.opener.<%=formname%>.elements["#<%=shukka_mname%>"].value = "<%=event.getShukka_m() %>";
}
<% } %>
<% if (formname != null && shukka_dname != null) { %>
if (window.opener.<%=formname%>.<%=shukka_dname%>) {
window.opener.<%=formname%>.<%=shukka_dname%>.value = "<%=event.getShukka_d() %>";
}
if (window.opener.<%=formname%>.elements["#<%=shukka_dname%>"]) {
window.opener.<%=formname%>.elements["#<%=shukka_dname%>"].value = "<%=event.getShukka_d() %>";
}
<% } %>

window.close();
</script>
<% return; } %>

<html>
<head>
<title>è§ïiåüçı</title>
<body>
<%=event.getErrormsg() %><br>
<a href="javascript:history.back()" >ñﬂÇÈ</a>
</body>
</html>
