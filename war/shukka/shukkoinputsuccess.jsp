<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
出庫確定データを作成しました。
<%
java.util.Date shukko_date = (java.util.Date)session.getAttribute("SHUKKO_DATE");
if (shukko_date != null) {
session.removeAttribute("SHUKKO_DATE");
Calendar cal = Calendar.getInstance();
cal.setTime(shukko_date);
%>
<osx:form action="shukka/shukkayoteisearch.ev" method="post" option="name='main'">
<input type="hidden" name="searchoption" value="any_conditions">
<input type="hidden" name="#fromyear" value="<%=cal.get(Calendar.YEAR) %>">
<input type="hidden" name="#frommonth" value="<%=cal.get(Calendar.MONTH) + 1 %>">
<input type="hidden" name="#fromdate" value="<%=cal.get(Calendar.DATE) %>">
<input type="hidden" name="#toyear" value="<%=cal.get(Calendar.YEAR) %>">
<input type="hidden" name="#tomonth" value="<%=cal.get(Calendar.MONTH) + 1 %>">
<input type="hidden" name="#todate" value="<%=cal.get(Calendar.DATE) %>">
<input type="hidden" name="#mishukko_flg" value="on">
<input type="submit" value="出荷予定照会に戻る"><br>
(今出庫確定した日付の出庫日に戻ります)
</osx:form>
<% } %>
<osxzs:shukkoinputsuccess>
<table>
<tr>
<th>出庫番号</th>
<th>商品コード</th>
<th>賞味期限</th>
<th>商品名</th>
<th>数量</th>
</tr>

<osx:items>
<tr>
<td><osx:item field="shukko_bg" /></td>
<td><osx:item field="shouhin_id" /></td>
<td><osx:item field="shoumikigen" dateformat="yyyy/MM/dd" /></td>
<td><osx:item field="shouhinmei" /></td>
<td><osx:item field="suuryou" /></td>
</tr>
</osx:items>
</table>
</osxzs:shukkoinputsuccess>
