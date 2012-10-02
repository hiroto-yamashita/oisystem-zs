<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="com.oisix.oisystemfr.*,com.oisix.oisystemzs.ejb.*,java.util.*,com.oisix.oisystemzs.*" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<%
UserLocal user = (UserLocal)session.getAttribute("USER");
%>
<script language="JavaScript1.1">
function changeHacchuutanka() {
  irisuustr = document.main.elements["irisuu"].value;
  if (irisuustr == "") {
    return;
  }
  irisuu = parseFloat(irisuustr);
  hyoujuntankastr = document.main.elements["hyoujuntanka"].value;
  if (hyoujuntankastr == "") {
    return;
  }
  hacchuutanka = document.main.elements["tanka"].value;
  if (hacchuutanka != "") {
    if (!confirm("�����P����ύX���܂����H")) {
      return;
    }
  }
  hyoujuntanka = parseFloat(hyoujuntankastr);
  document.main.elements["tanka"].value = irisuu * hyoujuntanka;
}
function checktanka() {
  hacchuutankastr = document.main.elements["tanka"].value;
  hacchuutanka = parseFloat(hacchuutankastr);
  irisuustr = document.main.elements["irisuu"].value;
  irisuu = parseFloat(irisuustr);
  hyoujuntankastr = document.main.elements["hyoujuntanka"].value;
  hyoujuntanka = parseFloat(hyoujuntankastr);
  if (hacchuutanka != irisuu * hyoujuntanka) {
    return confirm("�����P�� " + hacchuutanka + " �́A���萔 " + irisuu + " �~ �W���P�� " + hyoujuntanka + " �ƈ�v���܂���B���i��o�^���Ė�肠��܂��񂩁H");
  }
  return true;
}
</script>
<jsp:useBean id="event" scope="request" class="com.oisix.oisystemzs.eventhandler.ShouhineditEvent" />
<%
if (event.getResult() == event.RC_INPUTERROR) {
  Iterator iter = event.getErrorlist().iterator();
  while (iter.hasNext()) {
%>
<%=iter.next() %><br>
<% } return; } //�����܂ŃG���[ %>
<%
ShouhinData shouhin = event.getShouhin();
ShiiresakiData shiiresaki = event.getShiiresaki();
%>
<osx:form action="maint/shouhineditfinish.ev" method="post" option="name='main' onsubmit='return checktanka()'">
<table>
<tr>
<th>���i�R�[�h</th><td><%=shouhin.getShouhin_id() %><input type="hidden" name="shouhin_id" value="<%=shouhin.getShouhin_id() %>"></td>
</tr>
<tr>
<th>���i��</th><td><osx:input type="text" name="shouhin" size="80" value="<%=shouhin.getShouhin() %>"/></td>
</tr>
<tr>
<th>���i���t���K�i</th><td><osx:input type="text" name="shouhinfurigana" size="80" value="<%=shouhin.getShouhinfurigana() %>"/></td>
</tr>
<tr>
<th>�d����R�[�h</th><td>
<osx:input type="text" name="#shiiresaki_id_se" size="6" value="<%=shouhin.getShiiresaki_id() %>"/><span style="cursor:hand" onClick="openSubSearch('../shared/shiiresakisearch.ev?form=main&shiiresaki_id=shiiresaki_id_se&shiiresaki=shiiresaki')"><osx:img src="shared/image/icon_search01.gif" width="17" height="15" /></span>
<span id="shiiresaki"><%=shiiresaki.getName() %></span>
</td>
</tr>
<tr>
<th>�������i��1</th><td><osx:input type="text" name="hacchuushouhin1" size="80" value="<%=shouhin.getHacchuushouhin1() %>"/></td>
</tr>
<tr>
<th>�������i��2</th><td><osx:input type="text" name="hacchuushouhin2" size="80" value="<%=shouhin.getHacchuushouhin2() %>"/></td>
</tr>
<tr>
<th>�������i��3</th><td><osx:input type="text" name="hacchuushouhin3" size="80" value="<%=shouhin.getHacchuushouhin3() %>"/></td>
</tr>
<tr>
<th>�K�i</th>
<td>
<osx:input type="text" name="kikaku" value="<%=shouhin.getKikaku() %>"/>
(��{�I�ɏo�ׂ̋K�i�ɂȂ�܂�)(��F500g)
</td>
</tr>
<tr>
<th>�P��</th>
<td>
<osxzs:taniselect name="tani" selected="<%=shouhin.getTani() %>" />
(��L�K�i�̒P�ʂł�)
</td>
</tr>
<tr>
<th>�����K�i</th>
<td>
<osx:input type="text" name="hacchuukikaku" value="<%=shouhin.getHacchuukikaku() %>"/>
(�������̋K�i�ɂȂ�܂�)(��F130g*6�~4)
</td>
</tr>
<tr>
<th>�����_</th><td><osx:input type="text" name="hacchuuten" size="5" value="<%=shouhin.getHacchuuten() %>"/></td>
</tr>
<tr>
<th>�����P��</th><td><osxzs:taniselect name="hacchuutani" selected="<%=shouhin.getHacchuutani() %>" /></td>
</tr>
<tr>
<th>���萔</th><td><osx:input type="text" name="irisuu" size="5" value="<%=shouhin.getIrisuu() %>" option="onchange='changeHacchuutanka()'"/></td>
</tr>
<tr>
<th>���萔����1</th><td><%=shouhin.getIrisuurireki1() %></td>
</tr>
<tr>
<th>�����P�ʐ�</th><td><osx:input type="text" name="hacchuutanisuu" size="5" value="<%=shouhin.getHacchuutanisuu() %>"/></td>
</tr>
<tr>
<th>�Œᔭ����</th><td><osx:input type="text" name="saiteihacchuusuu" size="5" value="<%=shouhin.getSaiteihacchuusuu() %>"/></td>
</tr>
<tr>
<th>�W���P��</th>
<td>
<osx:input type="text" name="hyoujuntanka" size="6" value="<%=shouhin.getHyoujuntanka() %>" option="onchange='changeHacchuutanka()'"/>
(�݌ɕ]����W�������@�Ōv�Z���鎞�̒P���B
�K�i��P�ʂ�����̌��������)
</td>
</tr>
<tr>
<th>�����P��</th>
<td>
<osx:input type="text" name="tanka" size="6" value="<%=shouhin.getTanka() %>"/>
(�����K�i��P�ʂ�����̉��i�����)
</td>
</tr>
<tr>
<th>
�W������
</th>
<td>
<osx:input type="text" name="hyoujunbaika" size="6" value="<%=shouhin.getHyoujunbaika() %>"/>
(�Q�l���ځB�K�i��P�ʂ�����̔��������)
</td>
</tr>
<tr>
<th>�d�����[�h�^�C��</th><td><osx:input type="text" name="shiireleadtime" size="5" value="<%=shouhin.getShiireleadtime() %>"/></td>
</tr>
<tr>
<th>���x��</th><td><osxzs:ondotaiselect name="ondotai" selected="<%=shouhin.getOndotai() %>" /></td>
</tr>
<tr>
<th>�ܖ������Ǘ�</th>
<td>
<% if (shouhin.getShoumikigen_flg() == Names.ON) { %>
����
<% } else { %>
���Ȃ�
<% } %>
<input type="hidden" name="shoumikigen_flg" value="<%=shouhin.getShoumikigen_flg() %>">
</td>
</tr>
<tr>
<th>�ܖ���������</th><td><osx:input type="text" name="shoumikigennissuu" size="5" value="<%=shouhin.getShoumikigennissuu() %>"/></td>
</tr>
<tr>
<th>�o�׊�������</th><td><osx:input type="text" name="shukkakigennissuu" size="5" value="<%=shouhin.getShukkakigennissuu() %>"/></td>
</tr>
<tr>
<th>�ʔ���</th><td><osx:input type="checkbox" name="kobetsuhacchuu_flg" value="1" defaultval="<%=shouhin.getKobetsuhacchuu_flg() %>"/>�ʔ�������</td>
</tr>
<tr>
<th>�v���Ӌ敪</th><td><osxzs:youchuuikubunselect name="youchuuihin_flg" defaultval="<%=String.valueOf(shouhin.getYouchuuihin_flg()) %>"/></td>
</tr>
<tr>
<th>�v���x��</th><td><osx:input type="checkbox" name="youraberu_flg" value="1" defaultval="<%=shouhin.getYouraberu_flg() %>"/>���׃��x���𔭍s����</td>
</tr>
<tr>
<th>�啪��</th><td>
<osxzs:shouhindaibunrui name="daibunrui" type="select" defaultval="<%=shouhin.getDaibunrui()%>" />
</td>
</tr>
<tr>
<th>P�R�[�h</th><td><osx:input type="text" name="pcode" size="6" value="<%=shouhin.getPcode() %>"/></td>
</tr>
<tr>
<!-- <th>�݌ɕ]�����@</th><td></td> �݌ɕ]�����@�͈ړ����� -->
<input type="hidden" name="zaikohyoukahouhou" value="1">
<th>JAN�R�[�h</th><td><osx:input type="text" name="jancode" size="80" value="<%=shouhin.getJancode() %>"/></td>
</tr>
<tr>
<th>�^��</th><td><osx:input type="text" name="kataban" size="80" value="<%=shouhin.getKataban() %>"/></td>
</tr>
<tr>
<th>���P�[�V�����R�[�h1</th><td><osx:input type="text" name="location_id1" size="6" value="<%=shouhin.getLocation_id1() %>"/></td>
</tr>
<tr>
<th>���P�[�V�����R�[�h2</th><td><osx:input type="text" name="location_id2" size="6" value="<%=shouhin.getLocation_id2() %>"/></td>
</tr>
<tr>
<th>���P�[�V�����R�[�h3</th><td><osx:input type="text" name="location_id3" size="6" value="<%=shouhin.getLocation_id3() %>"/></td>
</tr>
<tr>
<th>�׎p</th><td><osx:input type="text" name="nisugata" size="80" value="<%=shouhin.getNisugata() %>"/></td>
</tr>
<tr>
<th>�����R�����g</th><td><osx:input type="text" name="hacchuucomment" size="80" value="<%=shouhin.getHacchuucomment() %>"/></td>
</tr>
<tr>
<th>�̔��敪</th><td><osxzs:hanbaikubunselect name="hanbaikubun" defaultval="<%=shouhin.getHanbaikubun() %>"/></td>
</tr>
<tr>
<th>���</th>
<td>
<select name="shubetsu">
<osx:option name="shubetsu" value="��" defaultval="<%=shouhin.getShubetsu() %>"/>��
<osx:option name="shubetsu" value="���z�i" defaultval="<%=shouhin.getShubetsu() %>"/>���z�i
<osx:option name="shubetsu" value="���H�i" defaultval="<%=shouhin.getShubetsu() %>"/>���H�i
</select>
</td>
</tr>
<tr>
<th>���T�����z���t���O</th>
<td><osx:input type="checkbox" name="mochikoshi_flg" value="1" defaultval="<%=shouhin.getMochikoshi_flg() %>"/></td>
</tr>
<tr>
<th>�I���t���O</th>
<td><osx:input type="checkbox" name="shuubai_flg" value="1" defaultval="<%=shouhin.getShuubai_flg() %>"/></td>
</tr>
<tr>
<th>�[��[�i�t���O</th>
<td><osx:input type="checkbox" name="shinyanouhin_flg" value="1" defaultval="<%=shouhin.getShinyanouhin_flg() %>"/></td>
</tr>
</table>
<input type="hidden" name="updated" value="<%=shouhin.getUpdated().getTime()%>">
<% if (user.getPriv() != 3) { %>
<input type="submit" value="�ύX">
<% } %>
</osx:form>
