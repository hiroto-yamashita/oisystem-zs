<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="java.util.*,com.oisix.oisystemfr.TransactionServlet,com.oisix.oisystemfr.TransactionEvent" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<%
TransactionEvent event = (TransactionEvent)
  request.getAttribute(TransactionServlet.RESULTKEY);
%>
<table width="100%"><tr><th style="font-size:14px">
���O�C��
</th></tr></table>
<%
if (event != null) {
  LinkedList errorlist = event.getErrorlist();
  Iterator erroriter = errorlist.iterator();
  while (erroriter.hasNext()) {
    String errormsg = (String)erroriter.next();
%>
<%= errormsg %><br>
<% } } %>
<p>
<%-- result�̃f�t�H���g��loginsuccess�Ƃ��� --%>
<osx:form transaction="dologin.ev" result="loginsuccess.ev" method="post">
<table>
<tr>
<td class="layout">�p�X���[�h</td>
<td class="layout"><osx:input type="password" name="#password" value="" /></td>
</tr>
<tr>
<td class="layout">�q��</td>
<td class="layout"><osxzs:soukoselect name="#souko_id" /></td>
</tr>
<td class="layout">������</td>
<td class="layout"><osxzs:officeselect name="#office_id" /></td>
</tr>
</table>
<input type="submit" value="���O�C��">
</osx:form>
