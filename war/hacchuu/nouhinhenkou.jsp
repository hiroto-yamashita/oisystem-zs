<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="com.oisix.oisystemzs.ejb.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
�[�i���ꊇ�ύX
<p>
<osxzs:nouhinhenkoulist>
<osx:form action="hacchuu/nouhinhenkoukanryou.ev" method="post">
<table width="100%">
<tr>
<th>�����ԍ�</th>
<th>������</th>
<th>�[�i��</th>
<th>��������</th>
<th>�d����R�[�h</th>
<th>�d���於</th>
<th>���i�R�[�h</th>
<th>���i��</th>
<th>�K�i</th>
<th>��������</th>
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
<osx:yearselect name='<%="year"+id %>' from="2000" to="2020" datekey="nouhin_date" />�N
<osx:monthselect name='<%="month"+id %>' datekey="nouhin_date" />��
<osx:dateselect name='<%="date"+id %>' datekey="nouhin_date" />��
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
<input type="submit" value="�ύX">
</osx:form>
</osxzs:nouhinhenkoulist>
