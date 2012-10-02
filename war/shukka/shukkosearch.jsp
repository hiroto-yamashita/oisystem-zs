<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<osx:form action="shukka/shukkosearch.ev" method="post" option="name='form1'">
<input type="hidden" name="searchoption" value="shukko_bg">
■ 出庫番号で検索<br>
&nbsp;出庫番号 <osx:input type="text" name="#shukko_bg" size="14" value="" />
<input type="submit" name="submit" value="検索">
</osx:form>
<p>
<osx:form action="shukka/shukkosearch.ev" method="post" option="name='form2'">
<input type="hidden" name="searchoption" value="any_conditions">
■ 条件で検索<br>
&nbsp;出庫日
<osx:yearselect name="#fromyear" from="2000" to="2020"/>年
<osx:monthselect name="#frommonth"/>月
<osx:dateselect name="#fromdate"/>日～
<osx:yearselect name="#toyear" from="2000" to="2020"/>年
<osx:monthselect name="#tomonth"/>月
<osx:dateselect name="#todate"/>日<br>
&nbsp;出庫区分 <osxzs:shukkokubunselect name="#shukkokubun" defaultval="（区分）" /><br>
&nbsp;商品コード <osx:input type="text" name="#shouhin_id" size="6" value="" /><span style="cursor:hand" onClick="openSubSearch('../shared/shouhinsearch.ev?form=form2&shouhin_id=shouhin_id&shouhin=shouhin&kikaku=kikaku')"><osx:img src="shared/image/icon_search01.gif" width="17" height="15" /></span>
<span id="shouhin"></span> <span id="kikaku"></span><br>
&nbsp;<osx:input type="checkbox" name="#daibunruicheck" value="on" />商品大分類<osxzs:shouhindaibunrui name="#daibunrui" type="select" defaultval="" /><br>
&nbsp;仕入先コード <osx:input type="text" name='<%="#shiiresaki_id" %>' size="6" value="" /><span style="cursor:hand" onClick="openSubSearch('../shared/shiiresakisearch.ev?form=form2&shiiresaki_id=shiiresaki_id&shiiresaki=shiiresaki')"><osx:img src="shared/image/icon_search01.gif" width="17" height="15" /></span><span id="shiiresaki"></span><br>



<input type="submit" name="submit" value="検索">
</osx:form>
<p>

<osxzs:shukkosearch size="100">
<osx:prev action="shukka/shukkosearch.ev">
前の100件
</osx:prev>
<osx:next action="shukka/shukkosearch.ev">
次の100件
</osx:next>
<osx:total/>件中<osx:startind />件目から<osx:endind />件目までを表示しています。<br>
<table width="100%">
<tr>
<th>
<th>出庫番号</th>
<th>出庫日</th>
<th>出庫区分</th>
<th>仕入先</th>
<th>商品名</th>
<th>数量</th>
<th>金額</th>
<th></th>
</tr>

<osx:form action="shukka/shukkokubunkoushin.ev" method="post" option="name='form3'">

<osx:items>
<tr>
<td><input type="checkbox" name="shukko_id" value="<osx:item field="shukko_id" />">
<td><osx:item field="shukko_bg" /></td>
<td><osx:item field="shukko_date" dateformat="yyyy/MM/dd" /></td>
<td><osx:item field="shukkokubun" /></td>
<td><osx:item field="shiiresaki" /></td>
<td><osx:item field="shouhinmei" /></td>
<td align="right"><osx:item field="suuryou" /></td>
<td align="right"><osx:item field="kingaku" /></td>
<td><osx:a href="shukka/shukkoshousai.ev?" itemkey="lineparam">詳細</osx:a></td>
</tr>
</osx:items>
</table>
<osxzs:shukkokubunselect name="shukkokubun" defaultval="（区分）" /><br>
<input type="submit" value="出庫区分を一括更新する">
</osx:form>
</osxzs:shukkosearch>
