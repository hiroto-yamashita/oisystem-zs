<%@ page contentType="text/html; charset=SJIS" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
���ɓ��͊������܂���<br>
<osxzs:nyuukoinput>
<table width="100%">
<tr>
<th>���ɔԍ�</th>
<th>���ɓ�</th>
<th>���i�R�[�h</th>
<th>���i��</th>
<th>����</th>
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
