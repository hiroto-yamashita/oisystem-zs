<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
入庫確定入力完了しました<p>
<%
java.util.Date nyuuko_date = (java.util.Date)session.getAttribute("INPUTRESULTNYUUKODATE");
if (nyuuko_date != null) {
session.removeAttribute("INPUTRESULTNYUUKODATE");
Calendar cal = Calendar.getInstance();
cal.setTime(nyuuko_date);
%>
<osx:form action="nyuuka/nyuukayoteishoukai.ev" method="post" option="name='main'">
<input type="hidden" name="#nyuukayotei" value="two">
<input type="hidden" name="#beginyear" value="<%=cal.get(Calendar.YEAR) %>">
<input type="hidden" name="#beginmonth" value="<%=cal.get(Calendar.MONTH) + 1 %>">
<input type="hidden" name="#begindate" value="<%=cal.get(Calendar.DATE) %>">
<input type="hidden" name="#endyear" value="<%=cal.get(Calendar.YEAR) %>">
<input type="hidden" name="#endmonth" value="<%=cal.get(Calendar.MONTH) + 1 %>">
<input type="hidden" name="#enddate" value="<%=cal.get(Calendar.DATE) %>">
<input type="submit" value="入荷予定照会に戻る"><br>
(今入庫確定した日付の入庫日に戻ります)
</osx:form>
<% } %>
<osxzs:nyuukokakuteisuccess>
<table width="100%">
<tr>
<th>入庫番号</th>
<th>入庫日</th>
<th>商品コード</th>
<th>商品名</th>
<th>数量</th>
<th>賞味期限</th>
<th>出荷期限</th>
</tr>

<osx:items>
<tr>
<td><osx:item field="nyuuko_bg" /></td>
<td><osx:item field="nyuuko_date" dateformat="yyyy/MM/dd" /></td>
<td><osx:item field="shouhin_id" /></td>
<td><osx:item field="shouhinmei" /></td>
<td><osx:item field="nyuukosuuryou" /></td>
<td><osx:item field="shoumikigen" /></td>
<td><osx:item field="shukkakigen" /></td>
</tr>
</osx:items>
</table>
</osxzs:nyuukokakuteisuccess>
