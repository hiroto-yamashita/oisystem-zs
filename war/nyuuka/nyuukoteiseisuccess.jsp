<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="com.oisix.oisystemfr.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<jsp:useBean id="event" scope="request" class="com.oisix.oisystemzs.eventhandler.DonyuukoteiseisuccessEvent" />
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
<%
String motonyuuko_bg = (String)session.getAttribute("MOTONYUUKO_BG");
String teiseinyuuko_bg = (String)session.getAttribute("TEISEINYUUKO_BG");
%>
���ɔԍ�<%=motonyuuko_bg%>��������܂����B<br>
�������ɔԍ��F<%=teiseinyuuko_bg%><br>
