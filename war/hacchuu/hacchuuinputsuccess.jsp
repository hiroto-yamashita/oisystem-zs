<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="com.oisix.oisystemfr.*,com.oisix.oisystemzs.ejb.*,com.oisix.oisystemzs.eventhandler.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<%
DohacchuuinputEvent event = (DohacchuuinputEvent)
  request.getAttribute(TransactionServlet.RESULTKEY);
%>
�����f�[�^���쐬���܂����B
<p>
�����ԍ� <%=event.getHacchuu_bg() %>
<p>
<osx:a href='<%="hacchuu/hacchuusho.ev?id=" + event.getHacchuu_id() %>'>�������쐬</osx:a>
