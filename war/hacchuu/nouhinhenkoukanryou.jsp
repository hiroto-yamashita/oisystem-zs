<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="com.oisix.oisystemfr.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<jsp:useBean id="event" scope="request" class="com.oisix.oisystemzs.eventhandler.NouhinhenkoukanryouEvent" />
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
�[�i����ύX���܂����B