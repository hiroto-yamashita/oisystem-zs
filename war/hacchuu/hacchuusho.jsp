<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="com.oisix.oisystemfr.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<jsp:useBean id="event" scope="request" class="com.oisix.oisystemzs.eventhandler.HacchuushoEvent" />
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
<p>
���������쐬���܂����B
<p>
<osx:a href="<%=event.getFileName() %>">�_�E�����[�h</osx:a>
