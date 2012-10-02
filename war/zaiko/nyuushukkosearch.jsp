<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="com.oisix.oisystemfr.UrlUtil,java.util.*" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%
Calendar cal = Calendar.getInstance();
cal.add(Calendar.DATE, -8);
java.util.Date nyuushukkodate = cal.getTime();
request.setAttribute("nyuushukkodate", nyuushukkodate);
%>
<osx:form action="zaiko/nyuushukkoshoukai.ev" method="post" option='name="main"'>
■日付と商品コードで検索
<osx:yearselect name="nyuushukkoyear" from="2000" to="2020" datekey="nyuushukkodate" />年
<osx:monthselect name="nyuushukkomonth" datekey="nyuushukkodate" />月
<osx:dateselect name="nyuushukkodate" datekey="nyuushukkodate" />日から
<select name="nyuushukkospan"><% for (int i = 1; i < 10; i++) { %><option value="<%=i%>"<% if (i == 9) { out.print(" selected"); } %>><%=i%><% } %></select>日間<br>
<table>
<tr><th>
商品コード
</th><th>
商品名
</th><th>
規格
</th></tr>
<% for (int i = 0; i < 10; i++) { %>
<tr><td>
<osx:input type="text" name='<%="shouhin_id"+i %>' size="6" value="" /><span style="cursor:hand" onClick="openSubSearch('../shared/shouhinsearch.ev?form=main&shouhin_id=shouhin_id<%=i%>&shouhin=shouhin<%=i%>&kikaku=kikaku<%=i%>')"><osx:img src="shared/image/icon_search01.gif" width="17" height="15" /></span>
</td><td>
<span id="shouhin<%=i%>"></span>
</td><td>
<span id="kikaku<%=i%>"></span>
</td></tr>
<% } %>
</table>
<input type="submit" value="入出庫照会">
</osx:form>
