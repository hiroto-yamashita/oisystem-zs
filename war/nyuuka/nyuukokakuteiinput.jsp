<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="java.util.*,com.oisix.oisystemfr.TransactionServlet,com.oisix.oisystemfr.TransactionEvent,com.oisix.oisystemzs.ejb.*" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
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
<osx:form transaction="donyuukokakuteiinput.ev" result="nyuuka/nyuukokakuteisuccess.ev" method="post" >
<%
int kakuteisuu = 0;
%>
���ɓ�
<osx:yearselect name="#nyuukoyear" from="2000" to="2010"/>�N
<osx:monthselect name="#nyuukomonth"/>��
<osx:dateselect name="#nyuukodate"/>��<br>
<osxzs:nyuukokakuteiinput size="20">
<osx:prev action="nyuuka/nyuukokakuteiinput.ev">
�O��20��
</osx:prev>
<osx:next action="nyuuka/nyuukokakuteiinput.ev">
����20��
</osx:next>
<osx:total/>����<osx:startind />���ڂ���<osx:endind />���ڂ܂ł�\�����Ă��܂��B<br>
<table><tr>
<th>�����ԍ�</th>
<th>�d����R�[�h</th>
<th>���i�R�[�h</th>
<th>���i��</th>
<th>�K�i</th>
<th>���ɐ���</th>
<th>���ɒP��</th>
<th>�d������</th>
<th>�d���P��</th>
<th>�P��</th>
<th>���z</th>
<th>�ܖ�����</th>
<th>���ɋ敪</th>
</tr>
<osx:items>
<%
HashMap item = (HashMap)request.getAttribute("ITEM");
String nyuukosuuryou = (String)item.get("nyuukosuuryou");
String nyuukotani = (String)item.get("nyuukotani");
String shouhin = (String)item.get("shouhin");
String shiiresuuryou = (String)item.get("shiiresuuryou");
String shiiretani = (String)item.get("shiiretani");
String shiiretanka = (String)item.get("shiiretanka");
String kingaku = (String)item.get("kingaku");
Date shoumikigen = (Date)item.get("shoumikigen");
Date shukkakigen = (Date)item.get("shukkakigen");
Integer id = (Integer)item.get("nyuukayotei_id");
%>
<input type="hidden" name="#id<%=kakuteisuu%>" value="<%=id%>" >
<tr>
<td><osx:item field="hacchuu_bg" /></td>
<td><osx:item field="shiiremei" /></td>
<td><osx:item field="shouhin_id" /></td>
<td><osx:item field="shouhin" /></td>
<input type="hidden" name='<%="#shouhin"+kakuteisuu %>' value="<%=shouhin%>" />
<td><osx:item field="kikaku" /></td>
<td><osx:input type="text" name='<%="#nyuukosuuryou"+kakuteisuu %>' size="6" value="<%=String.valueOf(nyuukosuuryou)%>" /></td>
<td>
<osxzs:tanimeiselect name='<%="#nyuukotani"+kakuteisuu %>' defaultval="<%=nyuukotani%>" selected="<%=nyuukotani%>"  />
</td><td>
<osx:input type="text" name='<%="#shiiresuuryou"+kakuteisuu %>' size="6" value="<%=String.valueOf(shiiresuuryou)%>" />
</td><td>
<osxzs:tanimeiselect name='<%="#shiiretani"+kakuteisuu %>' defaultval="<%=shiiretani%>" selected="<%=shiiretani%>" />
</td><td>
<osx:input type="text" name='<%="#shiiretanka"+kakuteisuu %>' size="6" value="<%=String.valueOf(shiiretanka)%>" /></td>
<td><osx:item field="kingaku" /></td>
<%
if(shoumikigen != null && shukkakigen != null){
request.setAttribute("shoumikigen" , shoumikigen);
request.setAttribute("shukkakigen" , shukkakigen);
%>
<td>
<osx:yearselect name='<%="#shoumiyear"+kakuteisuu %>' from="2000" to="2010" datekey="shoumikigen" />�N
<osx:monthselect name='<%="#shoumimonth"+kakuteisuu %>' datekey="shoumikigen" />��
<osx:dateselect name='<%="#shoumidate"+kakuteisuu %>' datekey="shoumikigen" />��
</td>
<%}else{%>
<td><input type="hidden" name='<%="#shoumiyear"+kakuteisuu %>' value="0" /></td>
<% } %>
<td><osxzs:nyuukokubunselect name='<%="#nyuukokubun"+kakuteisuu %>' defaultval=" (�敪) " /></td>
</tr>
<% kakuteisuu++; %>
</osx:items>
</table>
<input type="hidden" name="#kakuteisuu" value="<%=kakuteisuu%>" >
<input type="submit" value="�m��">

</osxzs:nyuukokakuteiinput>
</osx:form>