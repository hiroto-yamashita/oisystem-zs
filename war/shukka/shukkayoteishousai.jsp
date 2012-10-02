<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="java.util.*,com.oisix.oisystemfr.TransactionServlet,com.oisix.oisystemfr.TransactionEvent,com.oisix.oisystemzs.ejb.*" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<jsp:useBean id="event" scope="request" class="com.oisix.oisystemzs.eventhandler.ShukkayoteishousaiEvent" />
<%
TransactionEvent eventerr = (TransactionEvent)
  request.getAttribute(TransactionServlet.RESULTKEY);
if (eventerr != null) {
  Iterator iter = eventerr.getErrorlist().iterator();
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
ShukkayoteiData shukkayotei = event.getShukkayotei();
Collection shukkayoteimeisai = event.getShukkayoteimeisai();
%>
<osx:form transaction="doshukkayoteikoushin.ev" result="shukka/shukkayoteikoushinsuccess.ev" method="post" option="name='main'">
<input type="hidden" name="#shukkayotei_id" value="<%=shukkayotei.getShukkayotei_id()%>">
<table>
<tr><th class="layout">
�o�ɋ敪
</th><td class="layout">
<osxzs:shukkokubunselect name="#shukkokubun" defaultval="�i�敪�j" selected="<%=shukkayotei.getShukkokubun()%>" />
</td></tr>
<tr><th class="layout">
�o�ח\����
</th><td class="layout">
<osxzs:shukkayoteishubetsuselect name="#shukkayoteishubetsu" defaultval="�i��ʁj" selected="<%=shukkayotei.getShukkayoteishubetsu()%>" />
</td></tr>
<tr><th class="layout">
�o�ח\���
</th><td class="layout">
<osx:yearselect name="#shukka_year" from="2000" to="2020" datekey="shukkayotei" />�N
<osx:monthselect name="#shukka_month" datekey="shukkayotei" />��
<osx:dateselect name="#shukka_date" datekey="shukkayotei" />��
</td></tr>
<tr><th class="layout">
�[�i�\���
</th><td class="layout">
<osx:yearselect name="#nouhin_year" from="2000" to="2020" datekey="nouhinyotei" />�N
<osx:monthselect name="#nouhin_month" datekey="nouhinyotei" />��
<osx:dateselect name="#nouhin_date" datekey="nouhinyotei" />��
</td></tr>
<tr><th class="layout">
�o�׌��R�[�h
</th><td class="layout">
<osx:input type="text" name="#shukkamoto_id" size="6" value="<%=shukkayotei.getShukkamoto_id()%>" />
</td></tr>
<tr><th class="layout">
�[�i��R�[�h
</th><td class="layout">
<osx:input type="text" name="#nouhinsaki_id" size="6" value="<%=shukkayotei.getNouhinsaki_id()%>" />
</td></tr>
<tr><th class="layout">
�[�i�於
</th><td class="layout">
<osx:input type="text" name="#nouhinsakimei" size="30" value="<%=shukkayotei.getNouhinsakiname()%>" />
</td></tr>
<tr><th class="layout">
�[�i��X�֔ԍ�
</th><td class="layout">
<osx:input type="text" name="#nohinsakiyuubin" size="8" value="<%=shukkayotei.getNouhinsakizip()%>" />
</td></tr>
<tr><th class="layout">
�[�i��Z��
</th><td class="layout">
<osx:input type="text" name="#nouhinsakijuusho" size="80" value="<%=shukkayotei.getNouhinsakiaddr()%>" />
</td></tr>
<tr><th class="layout">
�[�i��d�b�ԍ�
</th><td class="layout">
<osx:input type="text" name="#nouhinsakitel" size="20" value="<%=shukkayotei.getNouhinsakitel()%>" />
</td></tr>
<tr><th class="layout">
�[�i��FAX�ԍ�
</th><td class="layout">
<osx:input type="text" name="#nouhinsakifax" size="20" value="<%=shukkayotei.getNouhinsakifax()%>" />
</td></tr>
<tr><td class="layout" colspan="2">

<table width="100%">
<tr>
<th>���i<br>�R�[�h<br>(�����p)</th>
<th>���i<br>�R�[�h<br>(�X�V�p)</th>
<th>���i��</th>
<th>�K�i</th>
<th>����</th>
<th>�P��</th>
<th>�o�׏�</th>
</tr>
<%
Iterator meisaiIter = shukkayoteimeisai.iterator();
int meisaisuu = 0;
while (meisaiIter.hasNext()) {
HashMap meisaiMap = (HashMap)meisaiIter.next();
ShukkayoteimeisaiData meisai = (ShukkayoteimeisaiData)meisaiMap.get("SHUKKAYOTEIMEISAI");
ShouhinLocal shouhin = (ShouhinLocal)meisaiMap.get("SHOUHIN");
String strSuuryou = String.valueOf(meisai.getShukkayoteisuuryou());
int id = meisai.getShukkayoteimeisai_id();
%>
<tr>
<input type="hidden" name="#shukkayoteimeisai_id<%=meisaisuu%>" value="<%=id%>">
<td><%=meisai.getShouhin_id()%></td>
<td><osx:input type="text" name='<%="#shouhin_id"+meisaisuu %>' size="6" value="<%=meisai.getShouhin_id()%>" /></td>
<td><%=shouhin.getShouhin()%></td>
<td><%=shouhin.getKikaku()%></td>
<td><osx:input type="text" name='<%="#suuryou"+meisaisuu %>' size="6" value="<%=strSuuryou%>" /></td>
<td><osxzs:tanimeiselect name='<%="#tani"+meisaisuu %>' defaultval="�i�P�ʁj" selected="<%=meisai.getTani()%>" /></td>
<td><osxzs:shukkajoukyouselect name='<%="#shukkajoukyou"+meisaisuu %>' defaultval="�i�󋵁j" selected="<%=meisai.getShukkajoukyou()%>" /></td>
</tr>
<%
meisaisuu++;
}
int newmeisaisuu = 0;
if (meisaisuu >= 30) {
%>
<tr>
<td></td>
<td><osx:input type="text" name='#shouhin_idnew0' size="6" value="" /><span style="cursor:hand" onClick="openSubSearch('../shared/shouhinsearch.ev?form=main&shouhin_id=shouhin_idnew0&shouhin=shouhinnew0&kikaku=kikakunew0&tanimei=taninew0')"><osx:img src="shared/image/icon_search01.gif" width="17" height="15" /></span></td>
<td><span id="shouhinnew0"></span></td>
<td><span id="kikakunew0"></span></td>
<td><osx:input type="text" name='#suuryounew0' size="6" value="" /></td>
<td><osxzs:tanimeiselect name='#taninew0' defaultval="�i�P�ʁj" /></td>
<td><osxzs:shukkajoukyouselect name='#shukkajoukyounew0' defaultval="�i�󋵁j" /></td>
</tr>
<%
newmeisaisuu++;
} else {
for (int i = meisaisuu; i < 30; i++) {
%>
<tr>
<td></td>
<td><osx:input type="text" name='<%="#shouhin_idnew"+newmeisaisuu %>' size="6" value="" /><span style="cursor:hand" onClick="openSubSearch('../shared/shouhinsearch.ev?form=main&shouhin_id=shouhin_idnew<%=newmeisaisuu%>&shouhin=shouhinnew<%=newmeisaisuu%>&kikaku=kikakunew<%=newmeisaisuu%>&tanimei=taninew<%=newmeisaisuu%>')"><osx:img src="shared/image/icon_search01.gif" width="17" height="15" /></span></td>
<td><span id="shouhinnew<%=newmeisaisuu%>"></span></td>
<td><span id="kikakunew<%=newmeisaisuu%>"></span></td>
<td><osx:input type="text" name='<%="#suuryounew"+newmeisaisuu %>' size="6" value="" /></td>
<td><osxzs:tanimeiselect name='<%="#taninew"+newmeisaisuu %>' defaultval="�i�P�ʁj" /></td>
<td><osxzs:shukkajoukyouselect name='<%="#shukkajoukyounew"+newmeisaisuu %>' defaultval="�i�󋵁j" /></td>
</tr>
<%
newmeisaisuu++;
}
}
%>
</table>
<input type="hidden" name="#meisaisuu" value="<%=meisaisuu%>">
<input type="hidden" name="#newmeisaisuu" value="<%=newmeisaisuu%>">
</td></tr>
<tr><th class="layout" colspan="2">
���l
</th></tr>
<tr><td class="layout" colspan="2">
<osx:input type="text" name="#bikou" size="80" value="<%=shukkayotei.getBikou()%>" /><br>
</td></tr>
<tr><td class="layout" colspan="2">
<input type="submit" name="submit" value="�o�ח\�����"><br>
</td></tr>
</table>
</osx:form>
