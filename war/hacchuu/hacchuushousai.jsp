<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="com.oisix.oisystemzs.ejb.*,java.util.*,java.text.*" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<jsp:useBean id="event" scope="request" class="com.oisix.oisystemzs.eventhandler.HacchuushousaiEvent" />
<script language="JavaScript1.1">
function changeKingaku(ind) {
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
UserLocal user = (UserLocal)session.getAttribute("USER");
DecimalFormat df = new DecimalFormat("##########.##");
HacchuuData hacchuu = event.getHacchuu();
Collection nyuukayoteimeisai = event.getNyuukayoteimeisai();
Iterator iter = nyuukayoteimeisai.iterator();
%>
<osx:form action="hacchuu/hacchuukoushin.ev" method="post" option="name='main'" >
<input type="hidden" name="hacchuu_id" value="<%=hacchuu.getHacchuu_id() %>" >
<table>
<tr>
<td class="layout">�����ԍ�</td><td class="layout"><%=hacchuu.getHacchuu_bg() %></td>
<td class="layout">�d����R�[�h<osx:img src="shared/image/icon_search01.gif" width="17" height="15" /></td><td class="layout"><osx:input type="text" name="shiiresaki_id" size="6" value="<%=hacchuu.getShiiresaki_id() %>" /></td>
</tr>
<tr>
<td class="layout">������</td><td class="layout"><osx:yearselect name="year" from="2000" to="2020" datekey="hacchuu" />�N<osx:monthselect name="month" datekey="hacchuu" />��<osx:dateselect name="date" datekey="hacchuu" /></td>
<td class="layout">�[�i��R�[�h</td><td class="layout"><osx:input type="text" name="nouhinsaki_id" size="6" value="<%=hacchuu.getNouhinsaki_id() %>" /></td>
</tr>
<tr>
<td class="layout"></td><td class="layout"></td>
<td class="layout">�������t�H�[�}�b�g</td><td class="layout"><osxzs:hacchuushoselect name="hacchuusho" defaultval="<%=String.valueOf(hacchuu.getFormat()) %>" /></td>
</tr></table>

<table width=100%>
<tr>
<th rowspan=2>���i<br>�R�[�h</th>
<th>���i��</th>
<th>�K�i</th>
<th>����</th>
<th>�P��</th>
<th>�P��</th>
<th>���z</th>
<th>���א���</th>
<th>�P��</th>
</tr>
<tr>
<th colspan=2>�[�i��</th>
<th>��������</th>
<th>�����敪</th>
<th>�����א�</th>
<th>���׏�</th>
<th>�����X�e�[�^�X</th>
<th></th>
</tr>
<%
while (iter.hasNext()) {
  HashMap meisailine = (HashMap)iter.next();
  NyuukayoteimeisaiData nyd = (NyuukayoteimeisaiData)
   meisailine.get("NYUUKAYOTEIMEISAI");
  ShouhinData sh = (ShouhinData)meisailine.get("SHOUHIN");
  request.setAttribute("nyuuka", nyd.getNyuukayotei_date());
  int id = nyd.getNyuukayotei_id();
%>
<tr>
<td rowspan=2><%=nyd.getShouhin_id() %></td>
<td><%=sh.getShouhin() %></td>
<td><%=sh.getHacchuukikaku() %></td>
<% float suuryou = nyd.getHacchuusuuryou(); %>
<td><osx:input type="text" name='<%="suuryou"+id %>' size="3" value="<%=df.format(suuryou) %>" /></td>
<td><osx:input type="text" name='<%="hacchuutani"+id %>' size="12" value="<%=nyd.getHacchuutani() %>" /></td>
<% float tanka = nyd.getHacchuutanka(); %>
<td><osx:input type="text" name='<%="tanka"+id %>' size="10" value="<%=df.format(tanka) %>" /></td>
<td><%=df.format(suuryou * tanka) %></td>
<td><osx:input type="text" name='<%="nyuukasuuryou"+id %>' size="10" value="<%=df.format(nyd.getNyuukasuuryou()) %>" /></td>
<td><osx:input type="text" name='<%="nyuukatani"+id %>' size="12" value="<%=nyd.getNyuukatani() %>" /></td>
</tr>
<tr>
<td colspan=2><osx:yearselect name='<%="year"+id %>' from="2000" to="2020" datekey="nyuuka" />�N<osx:monthselect name='<%="month"+id %>' datekey="nyuuka" />��<osx:dateselect name='<%="date"+id %>' datekey="nyuuka" />��</td>
<td><osxzs:touchakuselect name='<%="touchaku"+id %>' defaultval="<%=String.valueOf(nyd.getTouchakujikan()) %>" /></td>
<td><osxzs:hacchuukubunselect name='<%="hacchuukubun"+id %>' defaultval="<%=String.valueOf(nyd.getHacchuukubun()) %>" /></td>
<td><%=df.format(nyd.getJitsunyuukasuu()) %></td>
<td><osxzs:nyuukajoukyouselect name='<%="nyuukajoukyou"+id %>' defaultval="<%=String.valueOf(nyd.getNyuukajoukyou()) %>" /></td>
<td><osxzs:hacchuustatusselect name='<%="status"+id %>' defaultval="<%=String.valueOf(nyd.getStatus()) %>" /></td>
<td></td>
</tr>
<%
}
for (int i=0; i<3; i++) {
String onchange = "onchange='changeKingaku(" + i + ")'";
%>
<tr>
<td rowspan=2><osx:input type="text" name='<%="#shouhin_id"+i %>' size="6" value="" /><span style="cursor:hand" onClick="openSubSearch('../shared/shouhinsearch.ev?form=main&shouhin_id=shouhin_id<%=i%>&shouhin=shouhin<%=i%>&kikaku=kikaku<%=i%>&tanka=tanka<%=i%>&hacchuutani=hacchuutani<%=i%>&tani=nyuukatani<%=i%>&irisuu=irisuu<%=i%>')"><osx:img src="shared/image/icon_search01.gif" width="17" height="15" /></span></td>
<td><span id="shouhin<%=i%>"></span></td>
<td><span id="kikaku<%=i%>"></span></td>
<td><osx:input type="text" name='<%="#suuryou"+i %>' size="3" value="" option="<%=onchange %>"/></td>
<td><osxzs:taniselect name='<%="#hacchuutani"+i %>' defaultval="�i�P�ʁj" /></td>
<td><osx:input type="text" name='<%="#tanka"+i %>' size="10" value="" option="<%=onchange %>" /></td>
<td><span id="kingaku<%=i%>"></span></td>
<td><osx:input type="text" name='<%="#nyuukasuuryou"+i %>' size="10" value="" /></td>
<td><osxzs:taniselect name='<%="#nyuukatani"+i %>' defaultval="�i�P�ʁj" /></td>
</tr>
<tr>
<td colspan=2>
<osx:yearselect name='<%="#year"+i %>' from="2000" to="2020" />�N
<osx:monthselect name='<%="#month"+i %>' />��
<osx:dateselect name='<%="#date"+i %>' />��
</td>
<td>
<osxzs:touchakuselect name='<%="#touchaku"+i %>' defaultval="20" />
<input type="hidden" name="#irisuu<%=i%>" value="">
</td>
<td><osxzs:hacchuukubunselect name='<%="#hacchuukubun"+i %>' /></td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>
<% } %>

</table>
<% if (user.getPriv() != 3) { %>
<input type="submit" value="�X�V">
<% } %>
</osx:form>