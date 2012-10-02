<%@ page contentType="text/html; charset=SJIS" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
入庫入力完了しました<br>
<osxzs:nyuukoinput>
<table width="100%">
<tr>
<th>入庫番号</th>
<th>入庫日</th>
<th>商品コード</th>
<th>商品名</th>
<th>数量</th>
</tr>

<osx:items>
<tr>
<td><osx:item field="nyuuko_bg" /></td>
<td><osx:item field="nyuuko_date" dateformat="yyyy/MM/dd" /></td>
<td><osx:item field="shouhin_id" /></td>
<td><osx:item field="shouhin" /></td>
<td><osx:item field="suuryou" /></td>
</tr>
</osx:items>
</table>
</osxzs:nyuukoinput>
