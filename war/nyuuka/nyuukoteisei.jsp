<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="com.oisix.oisystemzs.ejb.*,java.util.*,java.text.*,java.text.*,com.oisix.oisystemzs.objectmap.*,com.oisix.oisystemfr.TransactionServlet,com.oisix.oisystemfr.TransactionEvent" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<jsp:useBean id="event" scope="request" class="com.oisix.oisystemzs.eventhandler.NyuukoteiseiEvent" />
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
NyuukoData nyuuko = event.getNyuuko();
ShouhinLocal shouhin = event.getShouhin();
ShiiresakiData shiiresaki = null;
String shiiresakimei;
String shiiresaki_id = nyuuko.getShiiresaki_id();
if((shiiresaki_id != null) && (!shiiresaki_id.equals(""))){
    shiiresaki = event.getShiiresaki();
    shiiresakimei = shiiresaki.getName();
}else{
    shiiresaki_id = "";
    shiiresakimei = "";
}

SimpleDateFormat sdf = new SimpleDateFormat("yyyy�NM��d��");
String nyuuko_date = sdf.format(nyuuko.getNyuuko_date());
java.util.Date shoumikigendate = (java.util.Date)nyuuko.getShoumikigen();
String shoumikigen;
if(shoumikigendate != null){
    shoumikigen = sdf.format(shoumikigendate);
}else{
    shoumikigen = "";
}
java.util.Date shukkakigendate = (java.util.Date)nyuuko.getShukkakigen();
String shukkakigen;
if(shukkakigendate != null){
    shukkakigen = sdf.format(shukkakigendate);
}else{
    shukkakigen = "";
}
UserLocal user = (UserLocal)session.getAttribute("USER");
if (user == null) System.out.println("user is null");
String nyuukokubun = NyuukokubunMap.getNyuukokubun(nyuuko.getNyuukokubun()).getNyuukokubun();
float nyuukosuuryou = nyuuko.getNyuukosuuryou();
float nyuukotanka = nyuuko.getNyuukotanka();
float kingaku = nyuukosuuryou*nyuukotanka;
%>
<osx:form transaction="donyuukoteiseisuccess.ev" result="nyuuka/nyuukoteiseisuccess.ev" method="post">
<input type="hidden" name="#nyuuko_id" value="<%=nyuuko.getNyuuko_id()%>">
<table width="60%"><tr>
<th class="layout">���ɔԍ�</th>
<td class="layout"><%=nyuuko.getNyuuko_bg()%></td>
<th class="layout">���ɓ�</th>
<td class="layout"><%=nyuuko_date%></td>
<th class="layout">�d����</th><td class="layout"><%=shiiresaki_id%></td>
<td class="layout"><%=shiiresakimei%></td>
</tr><tr>
<th class="layout">���ɋ敪</th>
<td class="layout" colspan="6"><%=nyuukokubun%></td>
</tr><tr>
<th class="layout">���i�R�[�h</th>
<th class="layout">���i��</th>
<th class="layout">�K�i</th>
<th class="layout" colspan="4"></th>
</tr><tr>
<td class="layout"><%=shouhin.getShouhin_id()%></td>
<td class="layout"><%=shouhin.getShouhin()%></td>
<td class="layout"><%=shouhin.getKikaku()%></td>
<td class="layout" colspan="4"></td>
</tr><tr>
<th class="layout">���ɐ���</th>
<th class="layout">�d������</th>
<th class="layout">�P��</th>
<th class="layout">���z</th>
<th class="layout" colspan="3"></th>
</tr><tr>
<td class="layout">
<input type="text" name="#nyuukosuuryou" value="<%=-nyuuko.getNyuukosuuryou()%>" size="10">
</td>
<td class="layout">
<input type="text" name="#shiiresuuryou" value="<%=-nyuuko.getShiiresuuryou()%>" size="10">
</td>
<td class="layout">
<%=nyuuko.getNyuukotanka()%>
<input type="hidden" name="#nyuukotanka" value="<%=nyuuko.getNyuukotanka()%>">
</td>
<td class="layout">
<%=kingaku%>
</td>
<td class="layout" colspan="3"></td>
</tr><tr>
<th class="layout">�ܖ�����</th>
<td class="layout" colspan="6"><%=shoumikigen%></td>
</tr><tr>
<th class="layout">�o�׊���</th>
<td class="layout" colspan="6"><%=shukkakigen%></td>
</tr><tr>
<td class="layout" colspan="7" align="center">
<% if (user.getPriv() != 3) { %>
<input type="submit" value="����">
<% } %></td>
</tr></table>
</osx:form>