<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="java.util.*,com.oisix.oisystemfr.*,java.text.*" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<%
java.util.Date today = DateUtil.getDate();
SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
%>
<osx:form action="hacchuu/ikkatsuhacchuukanryou.ev" method="post">
<% if (request.getParameter("year") != null) { %>
<input type="hidden" name="year" value="<%=request.getParameter("year") %>">
<input type="hidden" name="month" value="<%=request.getParameter("month") %>">
<input type="hidden" name="date" value="<%=request.getParameter("date") %>">
<% } %>
発注日 <%=sdf.format(today) %>
<!-- 納品先 --><br>
<osxzs:hacchuukubunselect name="hacchuukubun" />
<osxzs:ikkatsuhacchuu>
<table width="100%">
<tr>
<th>商品コード</th>
<th>商品名</th>
<th>規格</th>
<th>数量</th>
<th>単位</th>
<th>単価</th>
<th>金額</th>
<th>入荷数量</th>
<th>単位</th>
<th>仕入先コード</th>
<th>仕入先</th>
<th>納品日</th>
</tr>

<osx:items>
<tr>
<td><osx:item field="shouhin_id" /></td>
<td><osx:item field="shouhin" /></td>
<td><osx:item field="kikaku" /></td>
<td><osx:item field="suuryou" /></td>
<td><osx:item field="hacchuutani" /></td>
<td><osx:item field="tanka" /></td>
<td><osx:item field="kingaku" /></td>
<td><osx:item field="nyuukasuuryou" /></td>
<td><osx:item field="nyuukatani" /></td>
<td><osx:item field="shiiresaki_id" /></td>
<td><osx:item field="shiiresaki" /></td>
<td><osx:item field="nouhindate" /></td>
<input type="hidden" name='id<osx:item field="shouhin_id" />' value='<osx:item field="suuryou" />' >
<input type="hidden" name='shiiresaki<osx:item field="shouhin_id" />' value='<osx:item field="shiiresaki_id" />' >
</tr>
</osx:items>
</table>
<input type="submit" value="発注">
</osxzs:ikkatsuhacchuu>
</osx:form>
