<%@ page contentType="text/html; charset=SJIS" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<osx:form action="zaiko/zaikosearch.ev" method="post" option='name="main"'>
�� �����Ō���<br>
<input type="hidden" name="searchoption" value="someconditions">
&nbsp;���i�R�[�h <osx:input type="text" name="#shouhin_id" size="8" value="" /><span style="cursor:hand" onClick="openSubSearch('../shared/shouhinsearch.ev?form=main&shouhin_id=shouhin_id')"><osx:img src="shared/image/icon_search01.gif" width="17" height="15" /></span><br>
&nbsp;���t
<osx:yearselect name="#toyear" from="2000" to="2020"/>�N
<osx:monthselect name="#tomonth"/>��
<osx:dateselect name="#todate"/>��<br>
<input type="submit" name="submit" value="����">
</osx:form>
<osx:form action="zaiko/zaikosearch.ev" method="post">
�� �o�׊����؂�(�����܂�)������<br>
<input type="hidden" name="searchoption" value="shukkakigengire">
&nbsp;���t
<osx:yearselect name="#zaikoyear" from="2000" to="2020"/>�N
<osx:monthselect name="#zaikomonth"/>��
<osx:dateselect name="#zaikodate"/>��
<input type="submit" name="submit" value="����">
</osx:form>
<p>

<osxzs:zaikosearch size="100">
<osx:prev action="zaiko/zaikosearch.ev">
�O��100��
</osx:prev>
<osx:next action="zaiko/zaikosearch.ev">
����100��
</osx:next>
<osx:total/>����<osx:startind />���ڂ���<osx:endind />���ڂ܂ł�\�����Ă��܂��B<br>
<table width="100%">
<tr>
<th>���i�R�[�h</th>
<th>���i��</th>
<th>�K�i</th>
<th>�ܖ�����</th>
<th>�o�׊���</th>
<th>���t</th>
<th>����</th>
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
<osx:a href="zaiko/shukkakigenhenkou.ev?" itemkey="lineparam">�o�׊����ύX</osx:a>
<osx:item field="kigeneditend" /></td>
</tr>
</osx:items>
</table>
</osxzs:zaikosearch>
