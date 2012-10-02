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
エラーが発生しました。
<p>
<%
  while (iter.hasNext()) {
%>
<%=iter.next() %><br>
<%
  }
}
// ここまでエラーだった場合
%>
<osx:form transaction="donyuukokakuteiinput.ev" result="nyuuka/nyuukokakuteisuccess.ev" method="post" >
<%
int kakuteisuu = 0;
%>
入庫日
<osx:yearselect name="#nyuukoyear" from="2000" to="2010"/>年
<osx:monthselect name="#nyuukomonth"/>月
<osx:dateselect name="#nyuukodate"/>日<br>
<osxzs:nyuukokakuteiinput size="20">
<osx:prev action="nyuuka/nyuukokakuteiinput.ev">
前の20件
</osx:prev>
<osx:next action="nyuuka/nyuukokakuteiinput.ev">
次の20件
</osx:next>
<osx:total/>件中<osx:startind />件目から<osx:endind />件目までを表示しています。<br>
<table><tr>
<th>発注番号</th>
<th>仕入先コード</th>
<th>商品コード</th>
<th>商品名</th>
<th>規格</th>
<th>入庫数量</th>
<th>入庫単位</th>
<th>仕入数量</th>
<th>仕入単位</th>
<th>単価</th>
<th>金額</th>
<th>賞味期限</th>
<th>入庫区分</th>
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
<osx:yearselect name='<%="#shoumiyear"+kakuteisuu %>' from="2000" to="2010" datekey="shoumikigen" />年
<osx:monthselect name='<%="#shoumimonth"+kakuteisuu %>' datekey="shoumikigen" />月
<osx:dateselect name='<%="#shoumidate"+kakuteisuu %>' datekey="shoumikigen" />日
</td>
<%}else{%>
<td><input type="hidden" name='<%="#shoumiyear"+kakuteisuu %>' value="0" /></td>
<% } %>
<td><osxzs:nyuukokubunselect name='<%="#nyuukokubun"+kakuteisuu %>' defaultval=" (区分) " /></td>
</tr>
<% kakuteisuu++; %>
</osx:items>
</table>
<input type="hidden" name="#kakuteisuu" value="<%=kakuteisuu%>" >
<input type="submit" value="確定">

</osxzs:nyuukokakuteiinput>
</osx:form>