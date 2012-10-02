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
<title>仕入先検索</title>
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
<osx:a href='<%= "shared/shiiresakisearch.ev?head=a" + query %>'>ア</osx:a>
<osx:a href='<%= "shared/shiiresakisearch.ev?head=k" + query %>'>カ</osx:a>
<osx:a href='<%= "shared/shiiresakisearch.ev?head=s" + query %>'>サ</osx:a>
<osx:a href='<%= "shared/shiiresakisearch.ev?head=t" + query %>'>タ</osx:a>
<osx:a href='<%= "shared/shiiresakisearch.ev?head=n" + query %>'>ナ</osx:a>
<osx:a href='<%= "shared/shiiresakisearch.ev?head=h" + query %>'>ハ</osx:a>
<osx:a href='<%= "shared/shiiresakisearch.ev?head=m" + query %>'>マ</osx:a>
<osx:a href='<%= "shared/shiiresakisearch.ev?head=y" + query %>'>ヤ</osx:a>
<osx:a href='<%= "shared/shiiresakisearch.ev?head=r" + query %>'>ラ</osx:a>
<osx:a href='<%= "shared/shiiresakisearch.ev?head=w" + query %>'>ワ</osx:a>
<p>
<%
String shiiresakihead = request.getParameter("head");
if (shiiresakihead == null) {}
else if (shiiresakihead.equals("a")) { out.print("ア行"); }
else if (shiiresakihead.equals("k")) { out.print("カ行"); }
else if (shiiresakihead.equals("s")) { out.print("サ行"); }
else if (shiiresakihead.equals("t")) { out.print("タ行"); }
else if (shiiresakihead.equals("n")) { out.print("ナ行"); }
else if (shiiresakihead.equals("h")) { out.print("ハ行"); }
else if (shiiresakihead.equals("m")) { out.print("マ行"); }
else if (shiiresakihead.equals("y")) { out.print("ヤ行"); }
else if (shiiresakihead.equals("r")) { out.print("ラ行"); }
else if (shiiresakihead.equals("w")) { out.print("ワ行"); }
%>
<osxzs:shiiresakisearch>
<table width="100%">
<tr>
<th>仕入先コード</th>
<th>仕入先名</th>
<th>読み仮名</th>
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
