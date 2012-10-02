<%@ page contentType="text/html; charset=SJIS" %><%@ page import="com.oisix.oisystemfr.*,com.oisix.oisystemzs.eventhandler.*,com.oisix.oisystemzs.ejb.UserLocal" %><%
String loginRedirectURI = (String)session.getAttribute("LOGINREDIRECTURI");
if (loginRedirectURI == null) {
  //あまり汎用的でないが。。
  loginRedirectURI = "mainmenu";
}
response.sendRedirect(UrlUtil.encode(request, response, loginRedirectURI + UrlUtil.getSuffix()));
%>