<%@ page contentType="text/html; charset=SJIS" %><%@ page import="com.oisix.oisystemfr.*,java.util.HashMap" %><%
HashMap inputval = (HashMap)session.getAttribute("INPUTVALUE");
inputval.clear();
String next = request.getParameter("inputclearnext");
if (next == null) {
  //���܂�ėp�I�łȂ����B�B
  next = "mainmenu";
}
String qs = request.getQueryString();
if (qs != null) {
  next += "?" + qs;
}
response.sendRedirect(UrlUtil.encode(request, response, next));
%>