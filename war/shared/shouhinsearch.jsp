<%@ page contentType="text/html; charset=SJIS" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
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
%>
<html>
<head>
<title>商品検索</title>
<script language="JavaScript1.2">
shouhin = new Array();
kikaku = new Array();
tani = new Array();
tanka = new Array();
hacchuutani = new Array();
irisuu = new Array();
hyoujuntanka = new Array();
shoumi_y = new Array();
shoumi_m = new Array();
shoumi_d = new Array();
shukka_y = new Array();
shukka_m = new Array();
shukka_d = new Array();

function selectShouhin(shouhin_id) {
<% if (formname != null && shouhin_idname != null) { %>
if (window.opener.<%=formname%>.<%=shouhin_idname%>) {
window.opener.<%=formname%>.<%=shouhin_idname%>.value = shouhin_id;
}
if (window.opener.<%=formname%>.elements["#<%=shouhin_idname%>"]) {
window.opener.<%=formname%>.elements["#<%=shouhin_idname%>"].value = shouhin_id;
}
<% } %>
<% if (shouhinname != null) { %>
if (window.opener.<%=shouhinname%>) {
window.opener.<%=shouhinname%>.innerText = shouhin[shouhin_id];
}
<% } %>
<% if (kikakuname != null) { %>
if (window.opener.<%=kikakuname%>) {
window.opener.<%=kikakuname%>.innerText = kikaku[shouhin_id];
}
<% } %>
<% if (formname != null && tankaname != null) { %>
if (window.opener.<%=formname%>.<%=tankaname%>) {
window.opener.<%=formname%>.<%=tankaname%>.value = tanka[shouhin_id];
}
if (window.opener.<%=formname%>.elements["#<%=tankaname%>"]) {
window.opener.<%=formname%>.elements["#<%=tankaname%>"].value = tanka[shouhin_id];
}
<% } %>
<% if (formname != null && taniname != null) { %>
if (window.opener.<%=formname%>.<%=taniname%>) {
window.opener.<%=formname%>.<%=taniname%>.value = tani[shouhin_id];
}
if (window.opener.<%=formname%>.elements["#<%=taniname%>"]) {
window.opener.<%=formname%>.elements["#<%=taniname%>"].value = tani[shouhin_id];
}
<% } %>
<% if (formname != null && hacchuutaniname != null) { %>
if (window.opener.<%=formname%>.<%=hacchuutaniname%>) {
window.opener.<%=formname%>.<%=hacchuutaniname%>.value = hacchuutani[shouhin_id];
}
if (window.opener.<%=formname%>.elements["#<%=hacchuutaniname%>"]) {
window.opener.<%=formname%>.elements["#<%=hacchuutaniname%>"].value = hacchuutani[shouhin_id];
}
<% } %>
<% if (formname != null && irisuuname != null) { %>
if (window.opener.<%=formname%>.<%=irisuuname%>) {
window.opener.<%=formname%>.<%=irisuuname%>.value = irisuu[shouhin_id];
}
if (window.opener.<%=formname%>.elements["#<%=irisuuname%>"]) {
window.opener.<%=formname%>.elements["#<%=irisuuname%>"].value = irisuu[shouhin_id];
}
<% } %>
<% if (formname != null && hyoujuntankaname != null) { %>
if (window.opener.<%=formname%>.<%=hyoujuntankaname%>) {
window.opener.<%=formname%>.<%=hyoujuntankaname%>.value = hyoujuntanka[shouhin_id];
}
if (window.opener.<%=formname%>.elements["#<%=hyoujuntankaname%>"]) {
window.opener.<%=formname%>.elements["#<%=hyoujuntankaname%>"].value = hyoujuntanka[shouhin_id];
}
<% } %>
<% if (formname != null && shoumi_yname != null) { %>
if (window.opener.<%=formname%>.<%=shoumi_yname%>) {
window.opener.<%=formname%>.<%=shoumi_yname%>.value = shoumi_y[shouhin_id];
}
if (window.opener.<%=formname%>.elements["#<%=shoumi_yname%>"]) {
window.opener.<%=formname%>.elements["#<%=shoumi_yname%>"].value = shoumi_y[shouhin_id];
}
<% } %>
<% if (formname != null && shoumi_mname != null) { %>
if (window.opener.<%=formname%>.<%=shoumi_mname%>) {
window.opener.<%=formname%>.<%=shoumi_mname%>.value = shoumi_m[shouhin_id];
}
if (window.opener.<%=formname%>.elements["#<%=shoumi_mname%>"]) {
window.opener.<%=formname%>.elements["#<%=shoumi_mname%>"].value = shoumi_m[shouhin_id];
}
<% } %>
<% if (formname != null && shoumi_dname != null) { %>
if (window.opener.<%=formname%>.<%=shoumi_dname%>) {
window.opener.<%=formname%>.<%=shoumi_dname%>.value = shoumi_d[shouhin_id];
}
if (window.opener.<%=formname%>.elements["#<%=shoumi_dname%>"]) {
window.opener.<%=formname%>.elements["#<%=shoumi_dname%>"].value = shoumi_d[shouhin_id];
}
<% } %>
<% if (formname != null && shukka_yname != null) { %>
if (window.opener.<%=formname%>.<%=shukka_yname%>) {
window.opener.<%=formname%>.<%=shukka_yname%>.value = shukka_y[shouhin_id];
}
if (window.opener.<%=formname%>.elements["#<%=shukka_yname%>"]) {
window.opener.<%=formname%>.elements["#<%=shukka_yname%>"].value = shukka_y[shouhin_id];
}
<% } %>
<% if (formname != null && shukka_mname != null) { %>
if (window.opener.<%=formname%>.<%=shukka_mname%>) {
window.opener.<%=formname%>.<%=shukka_mname%>.value = shukka_m[shouhin_id];
}
if (window.opener.<%=formname%>.elements["#<%=shukka_mname%>"]) {
window.opener.<%=formname%>.elements["#<%=shukka_mname%>"].value = shukka_m[shouhin_id];
}
<% } %>
<% if (formname != null && shukka_dname != null) { %>
if (window.opener.<%=formname%>.<%=shukka_dname%>) {
window.opener.<%=formname%>.<%=shukka_dname%>.value = shukka_d[shouhin_id];
}
if (window.opener.<%=formname%>.elements["#<%=shukka_dname%>"]) {
window.opener.<%=formname%>.elements["#<%=shukka_dname%>"].value = shukka_d[shouhin_id];
}
<% } %>

window.close();
}
</script>
<style type="text/css">
<!--
  body { font-size:12px }
  table { font-size:12px; border-style:none }
  td { background-color:#EEEEE0 }
  th { text-align:left; background-color:#99CCFF }
  input { font-size:12px }
  .layout { border-style:none; background-color:white }
  a{ color:#3322FF }
  a:visited{ color:#3322FF }
  a:hover{ color:#3322FF }
  a:active{ color:#3322FF }
-->
</style>
</head>
<body onload="javascript:document.forms[1].input_id.focus()" >
<osx:form action="shared/shouhinsearch.ev" method="post">
<% if (formname != null) { %>
<input type="hidden" name="form" value="<%=formname%>">
<% } %>
<% if (shouhin_idname != null) { %>
<input type="hidden" name="shouhin_id" value="<%=shouhin_idname%>">
<% } %>
<% if (shouhinname != null) { %>
<input type="hidden" name="shouhin" value="<%=shouhinname%>">
<% } %>
<% if (kikakuname != null) { %>
<input type="hidden" name="kikaku" value="<%=kikakuname%>">
<% } %>
<% if (tankaname != null) { %>
<input type="hidden" name="tanka" value="<%=tankaname%>">
<% } %>
<% if (taniname != null) { %>
<input type="hidden" name="tani" value="<%=taniname%>">
<% } %>
<% if (hacchuutaniname != null) { %>
<input type="hidden" name="hacchuutani" value="<%=hacchuutaniname%>">
<% } %>
<% if (irisuuname != null) { %>
<input type="hidden" name="irisuu" value="<%=irisuuname%>">
<% } %>
<% if (hyoujuntankaname != null) { %>
<input type="hidden" name="hyoujuntanka" value="<%=hyoujuntankaname%>">
<% } %>
<% if (shoumi_yname != null) { %>
<input type="hidden" name="shoumi_y" value="<%=shoumi_yname%>">
<% } %>
<% if (shoumi_mname != null) { %>
<input type="hidden" name="shoumi_m" value="<%=shoumi_mname%>">
<% } %>
<% if (shoumi_dname != null) { %>
<input type="hidden" name="shoumi_d" value="<%=shoumi_dname%>">
<% } %>
<% if (shukka_yname != null) { %>
<input type="hidden" name="shukka_y" value="<%=shukka_yname%>">
<% } %>
<% if (shukka_mname != null) { %>
<input type="hidden" name="shukka_m" value="<%=shukka_mname%>">
<% } %>
<% if (shukka_dname != null) { %>
<input type="hidden" name="shukka_d" value="<%=shukka_dname%>">
<% } %>
商品名 <osx:input type="text" name="searchshouhinmei" size="14" value="" option="style='ime-mode:active'" />
<input type="submit" name="submit" value="検索">
</osx:form>
<osx:form action="shared/shouhinidsearch.ev" method="post">
<% if (formname != null) { %>
<input type="hidden" name="form" value="<%=formname%>">
<% } %>
<% if (shouhin_idname != null) { %>
<input type="hidden" name="shouhin_id" value="<%=shouhin_idname%>">
<% } %>
<% if (shouhinname != null) { %>
<input type="hidden" name="shouhin" value="<%=shouhinname%>">
<% } %>
<% if (kikakuname != null) { %>
<input type="hidden" name="kikaku" value="<%=kikakuname%>">
<% } %>
<% if (tankaname != null) { %>
<input type="hidden" name="tanka" value="<%=tankaname%>">
<% } %>
<% if (taniname != null) { %>
<input type="hidden" name="tani" value="<%=taniname%>">
<% } %>
<% if (hacchuutaniname != null) { %>
<input type="hidden" name="hacchuutani" value="<%=hacchuutaniname%>">
<% } %>
<% if (irisuuname != null) { %>
<input type="hidden" name="irisuu" value="<%=irisuuname%>">
<% } %>
<% if (hyoujuntankaname != null) { %>
<input type="hidden" name="hyoujuntanka" value="<%=hyoujuntankaname%>">
<% } %>
<% if (shoumi_yname != null) { %>
<input type="hidden" name="shoumi_y" value="<%=shoumi_yname%>">
<% } %>
<% if (shoumi_mname != null) { %>
<input type="hidden" name="shoumi_m" value="<%=shoumi_mname%>">
<% } %>
<% if (shoumi_dname != null) { %>
<input type="hidden" name="shoumi_d" value="<%=shoumi_dname%>">
<% } %>
<% if (shukka_yname != null) { %>
<input type="hidden" name="shukka_y" value="<%=shukka_yname%>">
<% } %>
<% if (shukka_mname != null) { %>
<input type="hidden" name="shukka_m" value="<%=shukka_mname%>">
<% } %>
<% if (shukka_dname != null) { %>
<input type="hidden" name="shukka_d" value="<%=shukka_dname%>">
<% } %>
商品コード <osx:input type="text" name="input_id" size="4" option="style='ime-mode:inactive'" />
<input type="submit" name="submit" value="入力">
</osx:form>

<osxzs:shouhinsearch>
<table width="100%">
<tr>
<th>商品コード</th>
<th>商品名</th>
<th>規格</th>
</tr>

<osx:items>
<tr>
<script language="JavaScript1.2">
shouhin["<osx:item field="shouhin_id" />"] = "<osx:item field="shouhinmei" />";
kikaku["<osx:item field="shouhin_id" />"] = "<osx:item field="kikaku" />";
tanka["<osx:item field="shouhin_id" />"] = "<osx:item field="tanka" />";
hacchuutani["<osx:item field="shouhin_id" />"] = "<osx:item field="hacchuutani" />";
tani["<osx:item field="shouhin_id" />"] = "<osx:item field="tani" />";
irisuu["<osx:item field="shouhin_id" />"] = "<osx:item field="irisuu" />";
hyoujuntanka["<osx:item field="shouhin_id" />"] = "<osx:item field="hyoujuntanka" />";
shoumi_y["<osx:item field="shouhin_id" />"] = "<osx:item field="shoumiyear" />";
shoumi_m["<osx:item field="shouhin_id" />"] = "<osx:item field="shoumimonth" />";
shoumi_d["<osx:item field="shouhin_id" />"] = "<osx:item field="shoumidate" />";
shukka_y["<osx:item field="shouhin_id" />"] = "<osx:item field="shukkayear" />";
shukka_m["<osx:item field="shouhin_id" />"] = "<osx:item field="shukkamonth" />";
shukka_d["<osx:item field="shouhin_id" />"] = "<osx:item field="shukkadate" />";
</script>
<td><span style="color=blue;cursor:hand" onClick="selectShouhin('<osx:item field="shouhin_id" />')"><osx:item field="shouhin_id" /></span></td>
<td><osx:item field="shouhinmei" /></td>
<td><osx:item field="kikaku" /></td>
</tr>
</osx:items>
</table>
</osxzs:shouhinsearch>
</body>
</html>
