<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="com.oisix.oisystemfr.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<jsp:useBean id="event" scope="request" class="com.oisix.oisystemzs.eventhandler.ShukkokubunkoushinEvent" />
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
�o�ɋ敪���X�V���܂����B<br>
<table>
<tr>
<th>�o�ɔԍ�</th>
<th>�ύX�O�o�ɋ敪</th>
<th>�ύX��o�ɋ敪</th>
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
