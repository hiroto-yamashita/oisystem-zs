<%@ page contentType="text/html; charset=SJIS" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<%@ page import="java.util.*,com.oisix.oisystemfr.TransactionServlet,com.oisix.oisystemfr.TransactionEvent,com.oisix.oisystemzs.ejb.*" %>
<osx:form action="kessan/tanaoroshi.ev" method="post" >
���x
&nbsp;&nbsp;&nbsp;
<%
java.util.Date makedate;
Calendar cal = Calendar.getInstance();
cal.add(cal.MONTH,-1);
makedate = cal.getTime();
request.setAttribute("makedate" , makedate);
%>
<osx:yearselect name="#year" from="2000" to="2020" datekey="makedate"/>�N
<osx:monthselect name="#month" datekey="makedate"/>���x
<br>
<input type="submit" value="�쐬">
</osx:form>