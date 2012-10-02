<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="java.util.HashMap,com.oisix.oisystemfr.UrlUtil,com.oisix.oisystemzs.ejb.*,java.text.*" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<jsp:useBean id="event" scope="request" class="com.oisix.oisystemzs.taglib.KaikakekinsearchTag" />
<osxzs:kaikakekinsearch size="300">
<%
String year = request.getParameter("year1");
String month = request.getParameter("month1");
HashMap goukei = (HashMap)request.getAttribute("GOUKEI");
%>
<%=year%>年<%=month%>月度&nbsp;<%=goukei.get("shiiresaki")%><br>
合計<%=goukei.get("goukei")%>円<br>

<osx:total/>件中<osx:startind />件目から<osx:endind />件目までを表示しています。<br>
<osx:prev action="kessan/kaikakekinshoukai.ev">
前の300件
</osx:prev>
<osx:next action="kessan/kaikakekinshoukai.ev">
次の300件
</osx:next>
<table width="100%">
<tr>
<th>日付</th>
<th>納品先</th>
<th>商品コード</th>
<th>商品名</th>
<th>規格</th>
<th>入庫数量</th>
<th>入庫単価</th>
<th>仕入数量</th>
<th>仕入単位</th>
<th>仕入単価</th>
<th>金額</th>
<th></th>
</tr>
<osx:items>
<tr>
<td><osx:item field="nyuuko_date" dateformat="yyyy/MM/dd" /></td>
<td><osx:item field="nouhinsaki" /></td>
<td><osx:item field="shouhin_id" /></td>
<td><osx:item field="shouhin" /></td>
<td><osx:item field="kikaku" /></td>
<td align="right"><osx:item field="nyuukasuuryou" /></td>
<td align="right"><osx:item field="nyuukotanka" /></td>
<td align="right"><osx:item field="hacchuusuuryou" /></td>
<td><osx:item field="hacchuutani" /></td>
<td align="right"><osx:item field="hacchuutanka" /></td>
<td align="right"><osx:item field="hacchuukingaku" /></td>
<td>
<%
HashMap item = (HashMap)request.getAttribute("ITEM");
String nyuuko_id = (String)item.get("nyuuko_id");
if ((nyuuko_id != null) && (!nyuuko_id.equals("0"))) {
%>
<osx:a href="nyuuka/nyuukotankateisei.ev?" itemkey="param">
単価訂正
</osx:a>
<% } %>
</td>
</tr>
</osx:items>
</table>
</osxzs:kaikakekinsearch>
