<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="com.oisix.oisystemzs.ejb.*,java.util.*,java.text.*,com.oisix.oisystemzs.objectmap.*,com.oisix.oisystemfr.TransactionServlet,com.oisix.oisystemfr.TransactionEvent" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<jsp:useBean id="event" scope="request" class="com.oisix.oisystemzs.eventhandler.ShukkoteiseiEvent" />
<%
TransactionEvent eventerr = (TransactionEvent)
  request.getAttribute(TransactionServlet.RESULTKEY);
if (eventerr != null) {
  Iterator iter = eventerr.getErrorlist().iterator();
%>
�G���[���������܂����B
<p>
<%
  while (iter.hasNext()) {
%>
<%=iter.next() %><br>
<%
  }
}
// �����܂ŃG���[�������ꍇ
%>
<%
ShukkoData shukko = event.getShukko();
ShouhinLocal shouhin = event.getShouhin();
UserLocal user = (UserLocal)session.getAttribute("USER");

SimpleDateFormat sdf = new SimpleDateFormat("yyyy�NM��d��");
String strShukko_date = sdf.format(shukko.getShukko_date());
String strShoumikigen = "�Ȃ�";
if (shukko.getShoumikigen() != null) {
  strShoumikigen = sdf.format(shukko.getShoumikigen());
}

String shukkokubun = ShukkokubunMap.getShukkokubun(shukko.getShukkokubun()).getShukkokubun();

String suuryoustr = String.valueOf(-shukko.getSuuryou());
String tankastr = String.valueOf(shukko.getTanka());
%>
<osx:form transaction="doshukkoteisei.ev" result="shukka/shukkoteiseisuccess.ev" method="post">
<input type="hidden" name="#shukko_id" value="<%=shukko.getShukko_id()%>">
<table width="60%"><tr>
<th class="layout">�o�ɔԍ�</th>
<td class="layout" colspan="5"><%=shukko.getShukko_bg()%></td>
</tr><tr>
<th class="layout">�o�ɓ�</th>
<td class="layout"><%=strShukko_date%></td>
<th class="layout">�o�ɋ敪</th>
<td class="layout" colspan="3"><%=shukkokubun%></td>
</tr><tr>
<th class="layout">���i�R�[�h</th>
<th class="layout">���i��</th>
<th class="layout">����</th>
<th class="layout">�P��</th>
<th class="layout">�P��</th>
<th class="layout">���z</th>
</tr><tr>
<td class="layout"><%=shukko.getShouhin_id()%></td>
<td class="layout"><%=shouhin.getShouhin()%></td>
<td class="layout"><osx:input type="text" name="#suuryou" value="<%=suuryoustr%>" size="10" /></td>
<td class="layout"><%=shukko.getTani()%></td>
<td class="layout"><%=tankastr%></td>
<td class="layout"><span id="kingaku"><%=-shukko.getKingaku()%></span></td>
</tr><tr>
<th class="layout">�ܖ�����</th>
<td class="layout" colspan="5"><%=strShoumikigen%></td>
</tr><tr>
<td class="layout" colspan="6" align="center">
<% if (user.getPriv() != 3) { %>
<input type="submit" value="��������">
<% } %></td>
</tr></table>
</osx:form>