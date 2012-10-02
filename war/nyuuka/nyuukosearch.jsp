<%@ page contentType="text/html; charset=SJIS" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
■入庫番号で検索
<osx:form action="nyuuka/nyuukosearch.ev" method="post">
入庫番号
<osx:input type="text" name="#nyuuko_bg" size="14" />
<input type="submit" value="検索">
</osx:form>
<p>

■条件で検索
<osx:form action="nyuuka/nyuukosearch.ev" method="post" option="name='main'">
入庫日
<osx:yearselect name="#beginyear" from="2000" to="2010"/>年
<osx:monthselect name="#beginmonth"/>月
<osx:dateselect name="#begindate"/>日&nbsp;～

<osx:yearselect name="#endyear" from="2000" to="2010"/>年
<osx:monthselect name="#endmonth"/>月
<osx:dateselect name="#enddate"/>日&nbsp;
<br>商品コード <osx:input type="text" name="#shouhin_id" size="6" value=""/>
<span style="cursor:hand" onClick="openSubSearch('../shared/shouhinsearch.ev?form=main&shouhin_id=shouhin_id&shouhin=shouhin&kikaku=kikaku')"><osx:img src="shared/image/icon_search01.gif" width="17" height="15" /></span>
<span id="shouhin"></span>&nbsp;
<span id="kikaku"></span><br>
<input type="submit" value="検索">
</osx:form>

<osxzs:nyuukosearch size="500">
<osx:prev action="nyuuka/nyuukoitiran.ev">
前の500件
</osx:prev>
<osx:next action="nyuuka/nyuukoitiran.ev">
次の500件
</osx:next>
<table width="100%">
<tr>
<th>入庫番号</th>
<th>入庫日</th>
<th>入庫区分</th>
<th>仕入先</th>
<th>商品コード</th>
<th>商品名</th>
<th>規格</th>
<th>入庫数量</th>
<th>入庫単位</th>
<th>仕入数量</th>
<th>仕入単位</th>
<th>単価</th>
<th>金額</th>
<th>賞味期限</th>
<th>出荷期限</th>
<th></th>
</tr>

<osx:items>
<tr>
<td><osx:item field="nyuuko_bg" /></td>
<td><osx:item field="nyuuko_date" dateformat="yyyy/MM/dd" /></td>
<td><osx:item field="nyuukokubun" /></td>
<td><osx:item field="shiiresaki" /></td>
<td><osx:item field="shouhin_id" /></td>
<td><osx:item field="shouhin" /></td>
<td><osx:item field="kikaku" /></td>
<td align="right"><osx:item field="nyuukosuuryou" /></td>
<td><osx:item field="nyuukotani" /></td>
<td align="right"><osx:item field="shiiresuuryou" /></td>
<td><osx:item field="shiiretani" /></td>
<td align="right"><osx:item field="nyuukotanka" /></td>
<td align="right"><osx:item field="kingaku" /></td>
<td><osx:item field="shoumikigen" dateformat="yyyy/MM/dd" /></td>
<td><osx:item field="shukkakigen" dateformat="yyyy/MM/dd" /></td>
<td><osx:a href="nyuuka/nyuukoshousai.ev?" itemkey="lineparam">詳細</osx:a></td>
</tr>
</osx:items>
</table>
</osxzs:nyuukosearch>
