<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="com.oisix.oisystemfr.*,com.oisix.oisystemzs.ejb.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<script language="JavaScript1.1">
function changeKingaku(ind) {
  //elementname = "document.main.#suuryou" + ind + ".value";
  //suuryoustring = eval(elementname);
  suuryoustring = document.main.elements["#suuryou" + ind].value;
  suuryou = parseFloat(suuryoustring);
  if (isNaN(suuryou)) { return; }
  tankastring = document.main.elements["#tanka" + ind].value;
  tanka = parseFloat(tankastring);
  if (isNaN(tanka)) { return; }
  elementname = "document.all.kingaku" + ind;
  kingaku = eval(elementname);
  kingaku.innerText = suuryou * tanka;

  irisuustring = document.main.elements["#irisuu" + ind].value;
  irisuu = parseFloat(irisuustring);
  if (isNaN(irisuu)) { return; }
  elementname = "document.main.elements['#nyuukasuuryou" + ind + "']";
  nyuukasuuryou = eval(elementname);
  nyuukasuuryou.value = suuryou * irisuu;
}
</script>
<%
TransactionEvent event = (TransactionEvent)
  request.getAttribute(TransactionServlet.RESULTKEY);
if (event != null) {
  Iterator iter = event.getErrorlist().iterator();
%>
�G���[�ł��B�ȉ��̃G���[���b�Z�[�W�ɏ]�����͓��e���������Ă��������B
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
<osx:form transaction="dohacchuuinput.ev" result="hacchuu/hacchuuinputsuccess.ev" method="post" option="name='main'">
<table>
<tr>
<td class="layout">�����ԍ�</td><td class="layout"></td>
<td class="layout">�d����R�[�h<span style="cursor:hand" onClick="openSubSearch('../shared/shiiresakisearch.ev?form=main&shiiresaki_id=shiiresaki_id')"><osx:img src="shared/image/icon_search01.gif" width="17" height="15" /></span></td><td class="layout"><osx:input type="text" name="#shiiresaki_id" size="6" value="" /></td>
</tr>
<tr>
<td class="layout">������</td><td class="layout"><osx:yearselect name="#year" from="2000" to="2020" />�N<osx:monthselect name="#month" />��<osx:dateselect name="#date" /></td>
<td class="layout">�[�i��R�[�h</td><td class="layout"><osx:input type="text" name="#nouhinsaki_id" size="6" value="<%=souko.getSouko_id()%>" /></td>
</tr>
<tr>
<td class="layout"></td><td class="layout"></td>
<td class="layout">�������t�H�[�}�b�g</td><td class="layout"><osxzs:hacchuushoselect name="#hacchuusho" /></td>
</tr></table>

<table width=100%>
<tr>
<th>���i<br>�R�[�h</th>
<th>���i��</th>
<th>�K�i</th>
<th>����</th>
<th>�P��</th>
<th>�P��</th>
<th>���z</th>
<th>���א���</th>
<th>�P��</th>
<th>�[�i��<br>��������</th>
<th>�����敪</th>
</tr>
<% for (int i=0; i<8; i++) {
String onchange = "onchange='changeKingaku(" + i + ")'"; %>
<tr>
<td><osx:input type="text" name='<%="#shouhin_id"+i %>' size="6" value="" /><span style="cursor:hand" onClick="openSubSearch('../shared/shouhinsearch.ev?form=main&shouhin_id=shouhin_id<%=i%>&shouhin=shouhin<%=i%>&kikaku=kikaku<%=i%>&tanka=tanka<%=i%>&hacchuutani=hacchuutani<%=i%>&tani=nyuukatani<%=i%>&irisuu=irisuu<%=i%>')"><osx:img src="shared/image/icon_search01.gif" width="17" height="15" /></span></td>
<td><span id="shouhin<%=i%>"></span></td>
<td><span id="kikaku<%=i%>"></span></td>
<td><osx:input type="text" name='<%="#suuryou"+i %>' size="3" value="" option="<%=onchange %>"/></td>
<td><osxzs:taniselect name='<%="#hacchuutani"+i %>' defaultval="�i�P�ʁj" /></td>
<td><osx:input type="text" name='<%="#tanka"+i %>' size="10" value="" option="<%=onchange %>" /></td>
<td><span id="kingaku<%=i%>"></span></td>
<td><osx:input type="text" name='<%="#nyuukasuuryou"+i %>' size="10" value="" /></td>
<td><osxzs:taniselect name='<%="#nyuukatani"+i %>' defaultval="�i�P�ʁj" /></td>
<td>
<osx:yearselect name='<%="#year"+i %>' from="2000" to="2020" />�N
<osx:monthselect name='<%="#month"+i %>' />��
<osx:dateselect name='<%="#date"+i %>' />��<br>
<osxzs:touchakuselect name='<%="#touchaku"+i %>' defaultval="20" />
<input type="hidden" name="#irisuu<%=i%>" value="">
</td>
<td><osxzs:hacchuukubunselect name='<%="#hacchuukubun"+i %>' /></td>
</tr>
<% } %>
</table>
<input type="submit" value="����">
</osx:form>