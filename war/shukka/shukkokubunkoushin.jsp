<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="com.oisix.oisystemfr.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<jsp:useBean id="event" scope="request" class="com.oisix.oisystemzs.eventhandler.ShukkokubunkoushinEvent" />
<%
if (event.getResult() == TransactionEvent.RC_INPUTERROR) {
  Iterator iter = event.getErrorlist().iterator();
%>
エラーが発生しました。
<p>
<%
  while (iter.hasNext()) {
%>
<%=iter.next() %><br>
<%
  }
return;
}
// ここまでエラーだった場合
%>
出庫区分を更新しました。<br>
<table>
<tr>
<th>出庫番号</th>
<th>変更前出庫区分</th>
<th>変更後出庫区分</th>
</tr>
<%
Collection resultdetail = event.getResultdetail();
Iterator iter = resultdetail.iterator();
while (iter.hasNext()) {
  HashMap resultline = (HashMap)iter.next();
%>
<tr>
<td><%=resultline.get("SHUKKO_BG") %></td>
<td><%=resultline.get("BEFORE") %></td>
<td><%=resultline.get("AFTER") %></td>
</tr>
<% } %>
</table>
