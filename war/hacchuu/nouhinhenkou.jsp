<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="com.oisix.oisystemzs.ejb.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
納品日一括変更
<p>
<osxzs:nouhinhenkoulist>
<osx:form action="hacchuu/nouhinhenkoukanryou.ev" method="post">
<table width="100%">
<tr>
<th>発注番号</th>
<th>発注日</th>
<th>納品日</th>
<th>到着時間</th>
<th>仕入先コード</th>
<th>仕入先名</th>
<th>商品コード</th>
<th>商品名</th>
<th>規格</th>
<th>発注数量</th>
</tr>
<osx:items>
<%
HashMap item = (HashMap)request.getAttribute("ITEM");
java.util.Date date = (java.util.Date)item.get("nyuukayotei_date");
request.setAttribute("nouhin_date", date);
Integer id = (Integer)item.get("nyuukayotei_id");
String touchaku = (String)item.get("touchakujikan");
%>
<tr>
<td><osx:item field="hacchuu_bg" /></td>
<td><osx:item field="hacchuu_date" dateformat="yyyy/MM/dd" /></td>
<td>
<osx:yearselect name='<%="year"+id %>' from="2000" to="2020" datekey="nouhin_date" />年
<osx:monthselect name='<%="month"+id %>' datekey="nouhin_date" />月
<osx:dateselect name='<%="date"+id %>' datekey="nouhin_date" />日
</td>
<td>
<osxzs:touchakuselect name='<%="touchaku"+id %>' defaultval="<%=touchaku %>" />
</td>
<td><osx:item field="shiiresaki_id" /></td>
<td><osx:item field="shiiresaki_name" /></td>
<td><osx:item field="shouhin_id" /></td>
<td><osx:item field="shouhin" /></td>
<td><osx:item field="hacchuukikaku" /></td>
<td><osx:item field="hacchuusuuryou" /></td>
</tr>
</osx:items>
</table>
<input type="submit" value="変更">
</osx:form>
</osxzs:nouhinhenkoulist>
