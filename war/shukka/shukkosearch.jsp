<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<osx:form action="shukka/shukkosearch.ev" method="post" option="name='form1'">
<input type="hidden" name="searchoption" value="shukko_bg">
�� �o�ɔԍ��Ō���<br>
&nbsp;�o�ɔԍ� <osx:input type="text" name="#shukko_bg" size="14" value="" />
<input type="submit" name="submit" value="����">
</osx:form>
<p>
<osx:form action="shukka/shukkosearch.ev" method="post" option="name='form2'">
<input type="hidden" name="searchoption" value="any_conditions">
�� �����Ō���<br>
&nbsp;�o�ɓ�
<osx:yearselect name="#fromyear" from="2000" to="2020"/>�N
<osx:monthselect name="#frommonth"/>��
<osx:dateselect name="#fromdate"/>���`
<osx:yearselect name="#toyear" from="2000" to="2020"/>�N
<osx:monthselect name="#tomonth"/>��
<osx:dateselect name="#todate"/>��<br>
&nbsp;�o�ɋ敪 <osxzs:shukkokubunselect name="#shukkokubun" defaultval="�i�敪�j" /><br>
&nbsp;���i�R�[�h <osx:input type="text" name="#shouhin_id" size="6" value="" /><span style="cursor:hand" onClick="openSubSearch('../shared/shouhinsearch.ev?form=form2&shouhin_id=shouhin_id&shouhin=shouhin&kikaku=kikaku')"><osx:img src="shared/image/icon_search01.gif" width="17" height="15" /></span>
<span id="shouhin"></span> <span id="kikaku"></span><br>
&nbsp;<osx:input type="checkbox" name="#daibunruicheck" value="on" />���i�啪��<osxzs:shouhindaibunrui name="#daibunrui" type="select" defaultval="" /><br>
&nbsp;�d����R�[�h <osx:input type="text" name='<%="#shiiresaki_id" %>' size="6" value="" /><span style="cursor:hand" onClick="openSubSearch('../shared/shiiresakisearch.ev?form=form2&shiiresaki_id=shiiresaki_id&shiiresaki=shiiresaki')"><osx:img src="shared/image/icon_search01.gif" width="17" height="15" /></span><span id="shiiresaki"></span><br>



<input type="submit" name="submit" value="����">
</osx:form>
<p>

<osxzs:shukkosearch size="100">
<osx:prev action="shukka/shukkosearch.ev">
�O��100��
</osx:prev>
<osx:next action="shukka/shukkosearch.ev">
����100��
</osx:next>
<osx:total/>����<osx:startind />���ڂ���<osx:endind />���ڂ܂ł�\�����Ă��܂��B<br>
<table width="100%">
<tr>
<th>
<th>�o�ɔԍ�</th>
<th>�o�ɓ�</th>
<th>�o�ɋ敪</th>
<th>�d����</th>
<th>���i��</th>
<th>����</th>
<th>���z</th>
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
<td><osx:a href="shukka/shukkoshousai.ev?" itemkey="lineparam">�ڍ�</osx:a></td>
</tr>
</osx:items>
</table>
<osxzs:shukkokubunselect name="shukkokubun" defaultval="�i�敪�j" /><br>
<input type="submit" value="�o�ɋ敪���ꊇ�X�V����">
</osx:form>
</osxzs:shukkosearch>
