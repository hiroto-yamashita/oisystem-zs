<%@ page contentType="text/html; charset=SJIS" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<osx:form action="nyuuka/nyuukayoteishoukai.ev" method="post" option="name='main'">
<osx:input type="checkbox" name="#hacchuubi" value="one" />������
<osx:yearselect name="#hacchuuyear" from="2000" to="2020"/>�N
<osx:monthselect name="#hacchuumonth"/>��
<osx:dateselect name="#hacchuudate"/>��&nbsp;
<br>
<osx:input type="checkbox" name="#nyuukayotei" value="two" />���ח\���
<osx:yearselect name="#beginyear" from="2000" to="2020"/>�N
<osx:monthselect name="#beginmonth"/>��
<osx:dateselect name="#begindate"/>��&nbsp;�`
<osx:yearselect name="#endyear" from="2000" to="2020"/>�N
<osx:monthselect name="#endmonth"/>��
<osx:dateselect name="#enddate"/>��&nbsp;
<br>���i�R�[�h <osx:input type="text" name="#shouhin_id" size="6" />
<span style="cursor:hand" onClick="openSubSearch('../shared/shouhinsearch.ev?form=main&shouhin_id=shouhin_id&shouhin=shouhin&kikaku=kikaku')"><osx:img src="shared/image/icon_search01.gif" width="17" height="15" /></span
<span id="shouhin_id"></span>
<br>�d����R�[�h <osx:input type="text" name="#shiiresaki_id" size="6" />
<span style="cursor:hand" onClick="openSubSearch('../shared/shiiresakisearch.ev?form=main&shiiresaki_id=shiiresaki_id&name=name')"><osx:img src="shared/image/icon_search01.gif" width="17" height="15" /></span><span id="shiiresaki_id"></span><span id="name"></span>
<br>�����ԍ� <osx:input type="text" name="#hacchuu_bg" size="14" />
<br>�[�i��R�[�h <osx:input type="text" name="#nouhinsaki_id" size="6" />
<br><input type="submit" value="�Ɖ�">
</osx:form>