<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="com.oisix.oisystemzs.ejb.*,java.util.*,java.text.*,com.oisix.oisystemzs.objectmap.*,com.oisix.oisystemzs.Names" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<%
UserLocal user = (UserLocal)session.getAttribute("USER");
%>
<osx:form action="shukka/shukkayoteisearch.ev" method="post">
<input type="hidden" name="searchoption" value="shukkayotei_bg">
�� �o�ח\��ԍ��Ō���<br>
&nbsp;�o�ח\��ԍ� <osx:input type="text" name="#shukkayotei_bg" size="14" value="" />
<input type="submit" name="submit" value="����">
</osx:form>
<p>
<osx:form action="shukka/shukkayoteisearch.ev" method="post">
<input type="hidden" name="searchoption" value="any_conditions">
�� �����Ō���<br>
&nbsp;<osx:input type="checkbox" name="#mishukko_flg" value="on" defaultval="on" />���o�ׂ̂��̂̂ݕ\��<br>
&nbsp;�o�ח\���
<osx:yearselect name="#fromyear" from="2000" to="2020"/>�N
<osx:monthselect name="#frommonth"/>��
<osx:dateselect name="#fromdate"/>���`
<osx:yearselect name="#toyear" from="2000" to="2020"/>�N
<osx:monthselect name="#tomonth"/>��
<osx:dateselect name="#todate"/>��<br>
�o�ח\���� <osxzs:shukkayoteishubetsuselect name="#shukkayoteishubetsu" defaultval="�i��ʁj" />&nbsp;
<input type="submit" name="submit" value="����">
</osx:form>
<p>

<osxzs:shukkayoteisearch size="20">
<osx:prev action="shukka/shukkayoteisearch.ev">
�O��20��
</osx:prev>
<osx:next action="shukka/shukkayoteisearch.ev">
����20��
</osx:next>
<osx:total/>����<osx:startind />���ڂ���<osx:endind />���ڂ܂ł�\�����Ă��܂��B<br>
<table width="100%">
<tr>
<th>�o�ח\��ԍ�</th>
<th>�o�ח\���</th>
<th>�o�׌�</th>
<th>�[�i��</th>
<th>�o�ɋ敪</th>
<th>�o�׏�</th>
<th></th>
<th></th>
<th></th>
<th></th>
</tr>

<osx:items>
<tr>
<td><osx:item field="shukkayotei_bg" /></td>
<td><osx:item field="shukkayotei_date" dateformat="yyyy/MM/dd" /></td>
<td><osx:item field="shukkamoto" /></td>
<td><osx:item field="nouhinsaki" /></td>
<td><osx:item field="shukkokubun" /></td>
<td><osx:item field="shukkajoukyou" /></td>
<td><osx:a href="inputclear.ev?inputclearnext=shukka/shukkayoteishousai.ev&" itemkey="lineparam">�ڍ�</osx:a></td>
<td>
<% if (user.getPriv() != 3) { %>
<osx:a href="shukka/shukkokakuteiinput.ev?" itemkey="lineparam">�o�Ɋm��</osx:a>
<% } %>
</td>
<td><osx:a href="shukka/makepickinglist.ev?" itemkey="lineparam">P���X�g</osx:a></td>
<td><osx:a href="shukka/makenouhinsho.ev?" itemkey="lineparam">�[�i��</osx:a></td>
</tr>
</osx:items>
</table>
</osxzs:shukkayoteisearch>
