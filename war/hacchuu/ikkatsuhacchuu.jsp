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
������ <%=sdf.format(today) %>
<!-- �[�i�� --><br>
<osxzs:hacchuukubunselect name="hacchuukubun" />
<osxzs:ikkatsuhacchuu>
<table width="100%">
<tr>
<th>���i�R�[�h</th>
<th>���i��</th>
<th>�K�i</th>
<th>����</th>
<th>�P��</th>
<th>�P��</th>
<th>���z</th>
<th>���א���</th>
<th>�P��</th>
<th>�d����R�[�h</th>
<th>�d����</th>
<th>�[�i��</th>
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
<input type="submit" value="����">
</osxzs:ikkatsuhacchuu>
</osx:form>
