<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="java.util.*,java.text.*,com.oisix.oisystemfr.TransactionServlet,com.oisix.oisystemfr.TransactionEvent,com.oisix.oisystemzs.ejb.*,com.oisix.oisystemzs.objectmap.*" %>
<jsp:useBean id="event" scope="request" class="com.oisix.oisystemzs.eventhandler.TanaoroshiinputEvent" />
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<script language="JavaScript1.1">
function onSendButton(form, shori_id) {
if (shori_id != "") {
form.elements["#shorikubun"].value = shori_id;
}
form.submit();
}
</script>
<%
TransactionEvent eventerr = (TransactionEvent)
  request.getAttribute(TransactionServlet.RESULTKEY);
if (eventerr != null && eventerr.getErrorlist().size() > 0) {
  Iterator iter = eventerr.getErrorlist().iterator();
%>
�G���[���������܂����B<br>
<%
  while (iter.hasNext()) {
%>
�E<%=iter.next() %><br>
<%
  }
%>
<p>
<%
}
// �����܂ŃG���[�������ꍇ
SimpleDateFormat ymdj = new SimpleDateFormat("yyyy�NM��d��");
SimpleDateFormat ymd = new SimpleDateFormat("yyyy/MM/dd");
%>
�I����&nbsp;
<%=ymdj.format(event.getTanaoroshidate())%>
<%
LinkedList list = event.getTanaoroshilist();
%>
<osx:form transaction="dotanaoroshiinput.ev" result="zaiko/tanaoroshiinputsuccess.ev" method="post">
<input type="hidden" name="#shorikubun" value="INPUT">
<%=event.countTanaoroshi()%>����<%=event.firstIndex()%>���ڂ���<%=event.lastIndex()%>���ڂ�\��<br>
<% if (event.hasPrevpage()) { %>
<input type="button" onClick="onSendButton(this.form, 'PREV')" value="�O��10��">&nbsp;
<% } %>
<% if (event.hasNextpage()) { %>
<input type="button" onClick="onSendButton(this.form, 'NEXT')" value="����10��">&nbsp;
<% } %>
<table>
<tr><th>
���i�R�[�h
</th><th>
���i��
</th><th>
�K�i
</th><th>
�ܖ�����
</th><th>
����
</th></tr>
<%
Iterator iter = list.iterator();
while (iter.hasNext()) {
HashMap tanaoroshi = (HashMap)iter.next();
String shouhin_id = (String)tanaoroshi.get("SHOUHIN_ID");
String shouhinmei = (String)tanaoroshi.get("SHOUHINMEI");
String kikaku = (String)tanaoroshi.get("KIKAKU");
java.util.Date shoumikigen_date = null;
shoumikigen_date = (java.util.Date)tanaoroshi.get("SHOUMIKIGEN");
String shoumikigen = "";
if (shoumikigen_date != null) {
  shoumikigen = ymd.format(shoumikigen_date);
}
java.util.Date shukkakigen_date = null;
shukkakigen_date = (java.util.Date)tanaoroshi.get("SHUKKAKIGEN");
String shukkakigen = "";
if (shukkakigen_date != null) {
  shukkakigen = ymd.format(shukkakigen_date);
}
String suuryou = (tanaoroshi.get("SUURYOU")).toString();
int meisai_ind = ((Integer)tanaoroshi.get("TANAOROSHIINDEX")).intValue();
%>
<tr><td>
<%=shouhin_id%>
</td><td>
<%=shouhinmei%>
</td><td>
<%=kikaku%>
</td><td>
<%=shoumikigen%>
<input type="hidden" name='<%="#shoumikigen"+meisai_ind %>' value="<%=shoumikigen%>">
<input type="hidden" name='<%="#shukkakigen"+meisai_ind %>' value="<%=shukkakigen%>">
</td><td>
<osx:input type="text" name='<%="#suuryou"+meisai_ind %>' size="6" value="<%=suuryou%>" />
</td></tr>
<% } %>
</table>
<input type="button" onClick="onSendButton(this.form, 'INPUT')" value="�I������"><br>
</osx:form>
