<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="java.util.*,com.oisix.oisystemfr.*,java.text.*" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<jsp:useBean id="event" scope="request" class="com.oisix.oisystemzs.eventhandler.IkkatsuhacchuukanryouEvent" />
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
発注データを作成しました。
<osx:form action="hacchuu/hacchuusho.ev" method="post">
<table>
<tr>
<th>発注番号</th>
<th>仕入先コード</th>
<th>商品コード</th>
<th>商品名</th>
<th>数量</th>
<th>単位</th>
</tr>

<%
LinkedList resultList = event.getResultList();
Iterator iter = resultList.iterator();
while (iter.hasNext()) {
  HashMap result = (HashMap)iter.next();
%>
<input type="hidden" name="id" value="<%=result.get("hacchuu_id") %>">
<tr>
<td><%=result.get("hacchuu_bg") %></td>
<td><%=result.get("shiiresaki_id") %></td>
<td><%=result.get("shouhin_id") %></td>
<td><%=result.get("shouhin") %></td>
<td><%=result.get("suuryou") %></td>
<td><%=result.get("hacchuutani") %></td>
</tr>
<% } %>
</table>
<input type="submit" value="発注書作成">
</osx:form>
