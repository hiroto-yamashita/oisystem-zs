<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="java.util.*,com.oisix.oisystemfr.*,java.text.*" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<jsp:useBean id="event" scope="request" class="com.oisix.oisystemzs.eventhandler.IkkatsuhacchuukanryouEvent" />
<%
if (event.getResult() == TransactionEvent.RC_INPUTERROR) {
  Iterator iter = event.getErrorlist().iterator();
%>
�G���[���������܂����B
<p>
<%
  while (iter.hasNext()) {
%>
<%=iter.next() %><br>
<%
  }
return;
}
// �����܂ŃG���[�������ꍇ
%>
�����f�[�^���쐬���܂����B
<osx:form action="hacchuu/hacchuusho.ev" method="post">
<table>
<tr>
<th>�����ԍ�</th>
<th>�d����R�[�h</th>
<th>���i�R�[�h</th>
<th>���i��</th>
<th>����</th>
<th>�P��</th>
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
<input type="submit" value="�������쐬">
</osx:form>
