<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="java.util.*,com.oisix.oisystemfr.TransactionServlet,com.oisix.oisystemfr.TransactionEvent,com.oisix.oisystemzs.ejb.SoukoData" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<%
TransactionEvent errevent = (TransactionEvent)
  request.getAttribute(TransactionServlet.RESULTKEY);
if (errevent != null) {
  Iterator iter = errevent.getErrorlist().iterator();
%>
�G���[���������܂����B
<p>
<%
  while (iter.hasNext()) {
%>
<%=iter.next() %><br>
<%
  }
}
// �����܂ŃG���[�������ꍇ
SoukoData souko = (SoukoData)session.getAttribute("SOUKO");
%>
<osx:form transaction="doshukkayoteiinput.ev" result="shukka/shukkayoteiinputsuccess.ev" method="post" option="name='main'">
<table>
<tr><th class="layout">
�o�ɋ敪
</th><td class="layout">
<osxzs:shukkokubunselect name="#shukkokubun" defaultval="�i�敪�j" />
</td></tr>
<tr><th class="layout">
�o�ח\����
</th><td class="layout">
<!--<osxzs:shukkayoteishubetsuselect name="#shukkayoteishubetsu" defaultval="�i��ʁj" selected="3" />-->
<!--�o�ח\���ʂ͂��̑�(3)�ŌŒ�-->
<input type="hidden" name="#shukkayoteishubetsu" value="3">���̑�
</td></tr>
<tr><th class="layout">
�o�ח\���
</th><td class="layout">
<osx:yearselect name="#shukka_year" from="2000" to="2020"/>�N
<osx:monthselect name="#shukka_month"/>��
<osx:dateselect name="#shukka_date"/>��
</td></tr>
<tr><th class="layout">
�[�i�\���
</th><td class="layout">
<osx:yearselect name="#nouhin_year" from="2000" to="2020"/>�N
<osx:monthselect name="#nouhin_month"/>��
<osx:dateselect name="#nouhin_date"/>��
</td></tr>
<tr><th class="layout">
�o�׌��R�[�h
</th><td class="layout">
<osx:input type="text" name="#shukkamoto_id" size="6" value="<%=souko.getSouko_id()%>" />
</td></tr>
<tr><th class="layout">
�[�i��R�[�h
</th><td class="layout">
<osx:input type="text" name="#nouhinsaki_id" size="6" value="" />
</td></tr>
<tr><th class="layout">
�[�i�於
</th><td class="layout">
<osx:input type="text" name="#nouhinsakimei" size="30" value="" />
</td></tr>
<tr><th class="layout">
�[�i��X�֔ԍ�
</th><td class="layout">
<osx:input type="text" name="#nohinsakiyuubin" size="8" value="" />
</td></tr>
<tr><th class="layout">
�[�i��Z��
</th><td class="layout">
<osx:input type="text" name="#nouhinsakijuusho" size="80" value="" />
</td></tr>
<tr><th class="layout">
�[�i��d�b�ԍ�
</th><td class="layout">
<osx:input type="text" name="#nouhinsakitel" size="20" value="" />
</td></tr>
<tr><th class="layout">
�[�i��FAX�ԍ�
</th><td class="layout">
<osx:input type="text" name="#nouhinsakifax" size="20" value="" />
</td></tr>
<tr><td class="layout" colspan="2">

<table width="100%">
<tr>
<th>���i�R�[�h</th>
<th>���i��</th>
<th>�K�i</th>
<th>����</th>
<th>�P��</th>
</tr>
<% for (int i = 0; i < 30; i++) { %>
<tr>
<td><osx:input type="text" name='<%="#shouhin_id"+i %>' size="10" value="" /><span style="cursor:hand" onClick="openSubSearch('../shared/shouhinsearch.ev?form=main&shouhin_id=shouhin_id<%=i%>&shouhin=shouhin<%=i%>&kikaku=kikaku<%=i%>&tani=tani<%=i%>')"><osx:img src="shared/image/icon_search01.gif" width="17" height="15" /></span></td>
<td><span id="shouhin<%=i%>"></span></td>
<td><span id="kikaku<%=i%>"></span></td>
<td><osx:input type="text" name='<%="#suuryou"+i %>' size="6" value="" /></td>
<td><osxzs:taniselect name='<%="#tani"+i %>' defaultval="�i�P�ʁj" /></td>
</tr>
<% } %>
</table>

</td></tr>
<tr><th class="layout" colspan="2">
���l
</th></tr>
<tr><td class="layout" colspan="2">
<osx:input type="text" name="#bikou" size="80" value="" /><br>
</td></tr>
<tr><td class="layout" colspan="2">
<input type="submit" name="submit" value="�o�ח\�����"><br>
</td></tr>
</table>
</osx:form>
