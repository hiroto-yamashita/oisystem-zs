<%@ page contentType="text/html; charset=SJIS" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
�����ɔԍ��Ō���
<osx:form action="nyuuka/nyuukosearch.ev" method="post">
���ɔԍ�
<osx:input type="text" name="#nyuuko_bg" size="14" />
<input type="submit" value="����">
</osx:form>
<p>

�������Ō���
<osx:form action="nyuuka/nyuukosearch.ev" method="post" option="name='main'">
���ɓ�
<osx:yearselect name="#beginyear" from="2000" to="2010"/>�N
<osx:monthselect name="#beginmonth"/>��
<osx:dateselect name="#begindate"/>��&nbsp;�`

<osx:yearselect name="#endyear" from="2000" to="2010"/>�N
<osx:monthselect name="#endmonth"/>��
<osx:dateselect name="#enddate"/>��&nbsp;
<br>���i�R�[�h <osx:input type="text" name="#shouhin_id" size="6" value=""/>
<span style="cursor:hand" onClick="openSubSearch('../shared/shouhinsearch.ev?form=main&shouhin_id=shouhin_id&shouhin=shouhin&kikaku=kikaku')"><osx:img src="shared/image/icon_search01.gif" width="17" height="15" /></span>
<span id="shouhin"></span>&nbsp;
<span id="kikaku"></span><br>
<input type="submit" value="����">
</osx:form>

<osxzs:nyuukosearch size="500">
<osx:prev action="nyuuka/nyuukoitiran.ev">
�O��500��
</osx:prev>
<osx:next action="nyuuka/nyuukoitiran.ev">
����500��
</osx:next>
<table width="100%">
<tr>
<th>���ɔԍ�</th>
<th>���ɓ�</th>
<th>���ɋ敪</th>
<th>�d����</th>
<th>���i�R�[�h</th>
<th>���i��</th>
<th>�K�i</th>
<th>���ɐ���</th>
<th>���ɒP��</th>
<th>�d������</th>
<th>�d���P��</th>
<th>�P��</th>
<th>���z</th>
<th>�ܖ�����</th>
<th>�o�׊���</th>
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
<td><osx:a href="nyuuka/nyuukoshousai.ev?" itemkey="lineparam">�ڍ�</osx:a></td>
</tr>
</osx:items>
</table>
</osxzs:nyuukosearch>
