<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="com.oisix.oisystemfr.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<jsp:useBean id="event" scope="request" class="com.oisix.oisystemzs.eventhandler.DonyuukoteiseisuccessEvent" />
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
<%
String motonyuuko_bg = (String)session.getAttribute("MOTONYUUKO_BG");
String teiseinyuuko_bg = (String)session.getAttribute("TEISEINYUUKO_BG");
%>
入庫番号<%=motonyuuko_bg%>を訂正しました。<br>
訂正入庫番号：<%=teiseinyuuko_bg%><br>
