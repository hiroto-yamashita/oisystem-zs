<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="com.oisix.oisystemzs.ejb.*,java.util.*,java.text.*,java.text.*,com.oisix.oisystemzs.objectmap.*,com.oisix.oisystemzs.Names" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<jsp:useBean id="event" scope="request" class="com.oisix.oisystemzs.eventhandler.NyuukoshousaiEvent" />
<%
NyuukoLocal nyuuko = event.getNyuuko();
ShouhinData shouhin = event.getShouhin();
ShiiresakiData shiiresaki = null;
SimpleDateFormat sdf = new SimpleDateFormat("yyyy�NM��d��");
java.util.Date nyuukodate = (java.util.Date)nyuuko.getNyuuko_date();
String nyuuko_date = sdf.format(nyuukodate);
java.util.Date shoumikigendate = (java.util.Date)nyuuko.getShoumikigen();
String shoumikigen;
if(shoumikigendate != null){
    shoumikigen = sdf.format(shoumikigendate);
}else{
    shoumikigen = "";
}
String shiiresakimei;
String shiiresaki_id = nyuuko.getShiiresaki_id();
if((shiiresaki_id != null) && (!shiiresaki_id.equals(""))){
    shiiresaki = event.getShiiresaki();
    shiiresakimei = shiiresaki.getName();
}else{
    shiiresaki_id = "nll";
    shiiresakimei = "";
}
java.util.Date shukkakigendate = (java.util.Date)nyuuko.getShukkakigen();
String shukkakigen;
if(shukkakigendate != null){
    shukkakigen = sdf.format(shukkakigendate);
}else{
    shukkakigen = "";
}
String nyuukokubun = NyuukokubunMap.getNyuukokubun(nyuuko.getNyuukokubun()).getNyuukokubun();
%>
<osx:form action="nyuuka/nyuukoteisei.ev" method="post">

<table>
<tr>
<th class="layout">���ɔԍ�</th><td class="layout"><%=nyuuko.getNyuuko_bg() %></td>
<th class="layout">���ɓ�</th><td class="layout"><%=nyuuko_date%></td>
<th class="layout">�d����</th>
<%
if(!shiiresaki_id.equals("nll")){
%>
<td class="layout"><%=shiiresaki_id %></td>
<td class="layout"><%=shiiresakimei%></td>
<%}else{%>
<td class="layout"></td>
<td class="layout"></td>
<%}%>
</tr>
<tr>
<th class="layout">���ɋ敪</th><td class="layout"><%=nyuukokubun%></td>
<td class="layout"></td><td class="layout"></td><td class="layout"></td>
<td class="layout"></td><td class="layout"></td>
</tr></table>

<table>
<tr>
<th class="layout">���i�R�[�h</th>
<th class="layout"></th>
<th class="layout">���i��</th>
<th class="layout"></th>
<th class="layout">�K�i</th>
<th class="layout"></th>
</tr>
<tr>
<td class="layout"><%=shouhin.getShouhin_id() %></td>
<td class="layout"></td>
<td class="layout"><%=shouhin.getShouhin() %></td>
<td class="layout"></td>
<td class="layout"><%=shouhin.getKikaku() %></td>
<td class="layout"></td>
</tr>
<tr>
<th class="layout">���ɐ���</th>
<th class="layout">���ɒP��</th>
<th class="layout">�d������</th>
<th class="layout">�d���P��</th>
<th class="layout">�P��</th>
<th class="layout">���z</th>
</tr>
<tr>
<td class="layout"><%=nyuuko.getNyuukosuuryou() %></td>
<td class="layout"><%=nyuuko.getNyuukotani() %></td>
<td class="layout"><%=nyuuko.getShiiresuuryou() %></td>
<td class="layout"><%=nyuuko.getShiiretani() %></td>
<td class="layout"><%=nyuuko.getNyuukotanka() %></td>
<td class="layout"><%=nyuuko.getNyuukotanka()*nyuuko.getNyuukosuuryou() %></td>
</tr>
</table>
<br>

�ܖ�����&nbsp;<%=shoumikigen%><br>
�o�׊���&nbsp;<%=shukkakigen%><br>
<input type="hidden" name="#nyuuko_id" value="<%=nyuuko.getNyuuko_id()%>" >
<input type="hidden" name="#shiiresaki_id" value="<%=shiiresaki_id%>" >
<% 
UserLocal user = (UserLocal)session.getAttribute("USER");
if (user.getPriv() != 3) { 
    if (nyuuko.getTeisei_flg() == Names.OFF){
        String teiseiurl = "nyuuka/nyuukotankateisei.ev?nyuuko_id=";
        teiseiurl += nyuuko.getNyuuko_id();
        teiseiurl += "&tanka=" + nyuuko.getShiiretanka();
%>
<input type="submit" value="����">
<br>
<osx:a href="<%=teiseiurl %>">
�P������
</osx:a>
<%
    }
} %>
</osx:form>