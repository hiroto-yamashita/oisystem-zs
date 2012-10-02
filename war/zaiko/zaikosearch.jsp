<%@ page contentType="text/html; charset=SJIS" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<osx:form action="zaiko/zaikosearch.ev" method="post" option='name="main"'>
■ 条件で検索<br>
<input type="hidden" name="searchoption" value="someconditions">
&nbsp;商品コード <osx:input type="text" name="#shouhin_id" size="8" value="" /><span style="cursor:hand" onClick="openSubSearch('../shared/shouhinsearch.ev?form=main&shouhin_id=shouhin_id')"><osx:img src="shared/image/icon_search01.gif" width="17" height="15" /></span><br>
&nbsp;日付
<osx:yearselect name="#toyear" from="2000" to="2020"/>年
<osx:monthselect name="#tomonth"/>月
<osx:dateselect name="#todate"/>日<br>
<input type="submit" name="submit" value="検索">
</osx:form>
<osx:form action="zaiko/zaikosearch.ev" method="post">
■ 出荷期限切れ(当日含む)を検索<br>
<input type="hidden" name="searchoption" value="shukkakigengire">
&nbsp;日付
<osx:yearselect name="#zaikoyear" from="2000" to="2020"/>年
<osx:monthselect name="#zaikomonth"/>月
<osx:dateselect name="#zaikodate"/>日
<input type="submit" name="submit" value="検索">
</osx:form>
<p>

<osxzs:zaikosearch size="100">
<osx:prev action="zaiko/zaikosearch.ev">
前の100件
</osx:prev>
<osx:next action="zaiko/zaikosearch.ev">
次の100件
</osx:next>
<osx:total/>件中<osx:startind />件目から<osx:endind />件目までを表示しています。<br>
<table width="100%">
<tr>
<th>商品コード</th>
<th>商品名</th>
<th>規格</th>
<th>賞味期限</th>
<th>出荷期限</th>
<th>日付</th>
<th>数量</th>
<th></th>
</tr>

<osx:items>
<tr>
<td><osx:item field="shouhin_id" /></td>
<td><osx:item field="shouhinmei" /></td>
<td><osx:item field="kikaku" /></td>
<td><osx:item field="shoumikigen" dateformat="yyyy/MM/dd" /></td>
<td><osx:item field="shukkakigen" dateformat="yyyy/MM/dd" /></td>
<td><osx:item field="zaikodate" dateformat="yyyy/MM/dd" /></td>
<td align="right"><osx:item field="suuryou" /></td>
<td><osx:item field="kigeneditstart" />
<osx:a href="zaiko/shukkakigenhenkou.ev?" itemkey="lineparam">出荷期限変更</osx:a>
<osx:item field="kigeneditend" /></td>
</tr>
</osx:items>
</table>
</osxzs:zaikosearch>
