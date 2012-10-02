<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%
// “ú•t•\Ž¦—p
SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
String strDate = sdf.format(new java.util.Date());
%>
<table width="100%"><tr><td align="left" valign="top" class="layout">
<osx:img src="shared/image/logo_zs.gif" width="150" height="26" />
</td><td align="right" valign="bottom" class="layout">
<%=strDate%>
</td></tr></table>
