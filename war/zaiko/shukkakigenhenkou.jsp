<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="com.oisix.oisystemzs.ejb.*,java.util.*,java.text.*,com.oisix.oisystemzs.objectmap.*,com.oisix.oisystemfr.TransactionServlet,com.oisix.oisystemfr.TransactionEvent" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<jsp:useBean id="event" scope="request" class="com.oisix.oisystemzs.eventhandler.ShukkakigenhenkouEvent" />
<%
TransactionEvent eventerr = (TransactionEvent)
  request.getAttribute(TransactionServlet.RESULTKEY);
if (eventerr != null) {
  Iterator iter = eventerr.getErrorlist().iterator();
%>
�G���[���������܂����B
<p>
<%
  while (iter.hasNext()) {
%>
<%=iter.next() %><br>
<%
  }
}
// �����܂ŃG���[�������ꍇ
%>
<table width="100%">
<tr>
<th>���i�R�[�h</th>
<th>���i��</th>
<th>�K�i</th>
<th>�ܖ�����</th>
<th>���t</th>
<th>����</th>
</tr>
<%
SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
%>
<tr>
<td><%=(String)event.getData().get("SHOUHIN_ID")%></td>
<td><%=(String)event.getData().get("SHOUHINMEI")%></td>
<td><%=(String)event.getData().get("KIKAKU")%></td>
<td><%=sdf.format((java.util.Date)event.getData().get("SHOUMIKIGEN"))%></td>
<td><%=sdf.format((java.util.Date)event.getData().get("ZAIKODATE"))%></td>
<td><%=String.valueOf((Float)event.getData().get("SUURYOU"))%></td>
</tr>
</table>
<osx:form transaction="doshukkakigenhenkou.ev" result="zaiko/shukkakigenhenkousuccess.ev" method="post">
�o�׊���
<osx:yearselect name="#kigenyear" from="2000" to="2010" datekey="shukkakigen" />�N
<osx:monthselect name="#kigenmonth" datekey="shukkakigen" />��
<osx:dateselect name="#kigendate" datekey="shukkakigen" />��<br>
<input type="hidden" name="#id" value="<%=event.getZaikomeisai_id()%>">
<input type="submit" name="submit" value="�ύX">
</osx:form>
