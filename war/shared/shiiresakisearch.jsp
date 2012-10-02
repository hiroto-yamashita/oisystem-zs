<%@ page contentType="text/html; charset=SJIS" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<%
response.setHeader("Expires", "Mon, 26 Jul 1997 05:00:00 GMT"); 
response.setHeader("Last-Modified", "Mon, 26 Jul 1997 05:00:00 GMT");
response.setHeader("Cache-Control","no-cache, must-revalidate");
response.setHeader("Pragma","no-cache");

String formname = request.getParameter("form");
String shiiresaki_idname = request.getParameter("shiiresaki_id");
String shiiresakiname = request.getParameter("shiiresaki");
%>
<html>
<head>
<title>�d���挟��</title>
<script language="JavaScript1.2">
shiiresaki = new Array();
function selectShiiresaki(shiiresaki_id) {
<% if (formname != null && shiiresaki_idname != null) { %>
if (window.opener.<%=formname%>.<%=shiiresaki_idname%>) {
window.opener.<%=formname%>.<%=shiiresaki_idname%>.value = shiiresaki_id;
}
if (window.opener.<%=formname%>.elements["#<%=shiiresaki_idname%>"]) {
window.opener.<%=formname%>.elements["#<%=shiiresaki_idname%>"].value = shiiresaki_id;
}
<% } %>
<% if (formname != null && shiiresakiname != null) { %>
if (window.opener.<%=shiiresakiname%>) {
window.opener.<%=shiiresakiname%>.innerText = shiiresaki[shiiresaki_id];
}
<% } %>
<%
if (formname == null) {
  formname = "";
}
if (shiiresaki_idname == null) {
  shiiresaki_idname = "";
}
if (shiiresakiname == null) {
  shiiresakiname = "";
}
%>
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
<body>
<%
String query = "";
if (!formname.equals("")) {
  query += "&form=" + formname;
}
if (!shiiresaki_idname.equals("")) {
  query += "&shiiresaki_id=" + shiiresaki_idname;
}
if (!shiiresakiname.equals("")) {
  query += "&shiiresaki=" + shiiresakiname;
}
%>
<osx:a href='<%= "shared/shiiresakisearch.ev?head=a" + query %>'>�A</osx:a>
<osx:a href='<%= "shared/shiiresakisearch.ev?head=k" + query %>'>�J</osx:a>
<osx:a href='<%= "shared/shiiresakisearch.ev?head=s" + query %>'>�T</osx:a>
<osx:a href='<%= "shared/shiiresakisearch.ev?head=t" + query %>'>�^</osx:a>
<osx:a href='<%= "shared/shiiresakisearch.ev?head=n" + query %>'>�i</osx:a>
<osx:a href='<%= "shared/shiiresakisearch.ev?head=h" + query %>'>�n</osx:a>
<osx:a href='<%= "shared/shiiresakisearch.ev?head=m" + query %>'>�}</osx:a>
<osx:a href='<%= "shared/shiiresakisearch.ev?head=y" + query %>'>��</osx:a>
<osx:a href='<%= "shared/shiiresakisearch.ev?head=r" + query %>'>��</osx:a>
<osx:a href='<%= "shared/shiiresakisearch.ev?head=w" + query %>'>��</osx:a>
<p>
<%
String shiiresakihead = request.getParameter("head");
if (shiiresakihead == null) {}
else if (shiiresakihead.equals("a")) { out.print("�A�s"); }
else if (shiiresakihead.equals("k")) { out.print("�J�s"); }
else if (shiiresakihead.equals("s")) { out.print("�T�s"); }
else if (shiiresakihead.equals("t")) { out.print("�^�s"); }
else if (shiiresakihead.equals("n")) { out.print("�i�s"); }
else if (shiiresakihead.equals("h")) { out.print("�n�s"); }
else if (shiiresakihead.equals("m")) { out.print("�}�s"); }
else if (shiiresakihead.equals("y")) { out.print("���s"); }
else if (shiiresakihead.equals("r")) { out.print("���s"); }
else if (shiiresakihead.equals("w")) { out.print("���s"); }
%>
<osxzs:shiiresakisearch>
<table width="100%">
<tr>
<th>�d����R�[�h</th>
<th>�d���於</th>
<th>�ǂ݉���</th>
</tr>

<osx:items>
<tr>
<script language="JavaScript1.2">
shiiresaki["<osx:item field="shiiresaki_id" />"] = "<osx:item field="shiiresakimei" />";
</script>
<td><span style="color=blue;cursor:hand" onClick="selectShiiresaki('<osx:item field="shiiresaki_id" />')"><osx:item field="shiiresaki_id" /></span></td>
<td><osx:item field="shiiresakimei" /></td>
<td><osx:item field="furigana" /></td>
</tr>
</osx:items>
</table>
</osxzs:shiiresakisearch>
</body>
</html>
