<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="com.oisix.oisystemfr.*,com.oisix.oisystemzs.ejb.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<script language="JavaScript1.1">
function changeHacchuutanka() {
  irisuustr = document.main.elements["#irisuu"].value;
  if (irisuustr == "") {
    return;
  }
  irisuu = parseFloat(irisuustr);
  hyoujuntankastr = document.main.elements["#hyoujuntanka"].value;
  if (hyoujuntankastr == "") {
    return;
  }
  hacchuutanka = document.main.elements["#tanka"].value;
  if (hacchuutanka != "") {
    if (!confirm("�����P����ύX���܂����H")) {
      return;
    }
  }
  hyoujuntanka = parseFloat(hyoujuntankastr);
  document.main.elements["#tanka"].value = irisuu * hyoujuntanka;
}
function checktanka() {
  hacchuutankastr = document.main.elements["#tanka"].value;
  hacchuutanka = parseFloat(hacchuutankastr);
  irisuustr = document.main.elements["#irisuu"].value;
  irisuu = parseFloat(irisuustr);
  hyoujuntankastr = document.main.elements["#hyoujuntanka"].value;
  hyoujuntanka = parseFloat(hyoujuntankastr);
  if (hacchuutanka != irisuu * hyoujuntanka) {
    return confirm("�����P�� " + hacchuutanka + " �́A���萔 " + irisuu + " �~ �W���P�� " + hyoujuntanka + " �ƈ�v���܂���B���i��o�^���Ė�肠��܂��񂩁H");
  }
  return true;
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
%>
<osx:form transaction="doshouhininput.ev" result="maint/shouhininputsuccess.ev" method="post" option="name='main' onsubmit='return checktanka()'">
<table>
<tr>
<th>���i�R�[�h</th><td><osx:input type="text" name="#shouhin_id" size="6" value=""/></td>
</tr>
<tr>
<th>���i��</th><td><osx:input type="text" name="#shouhin" size="80" value=""/></td>
</tr>
<tr>
<th>���i���t���K�i</th><td><osx:input type="text" name="#shouhinfurigana" size="80" value=""/></td>
</tr>
<tr>
<th>�d����R�[�h</th><td>
<osx:input type="text" name="#shiiresaki_id" size="6" value=""/><span style="cursor:hand" onClick="openSubSearch('../shared/shiiresakisearch.ev?form=main&shiiresaki_id=shiiresaki_id&shiiresaki=shiiresaki')"><osx:img src="shared/image/icon_search01.gif" width="17" height="15" /></span>
<span id="shiiresaki"></span>
</td>
</tr>
<tr>
<th>�������i��1</th><td><osx:input type="text" name="#hacchuushouhin1" size="80" value=""/></td>
</tr>
<tr>
<th>�������i��2</th><td><osx:input type="text" name="#hacchuushouhin2" size="80" value=""/></td>
</tr>
<tr>
<th>�������i��3</th><td><osx:input type="text" name="#hacchuushouhin3" size="80" value=""/></td>
</tr>
<tr>
<th>
�K�i
<br>
</th>
<td>
<osx:input type="text" name="#kikaku" value=""/>
(��{�I�ɏo�ׂ̋K�i�ɂȂ�܂�)(��F500g)
</td>
</tr>
<tr>
<th>
�P��
</th>
<td>
<osxzs:taniselect name="#tani" defaultval="�i�P�ʁj" />(��L�K�i�̒P�ʂł�)
</td>
</tr>
<tr>
<th>
�����K�i
</th>
<td>
<osx:input type="text" name="#hacchuukikaku" value=""/>
(�������̋K�i�ɂȂ�܂�)(��F130g*6�~4)
</td>
</tr>
<tr>
<th>�����_</th><td><osx:input type="text" name="#hacchuuten" size="5" value=""/></td>
</tr>
<tr>
<th>�����P��</th><td><osxzs:taniselect name="#hacchuutani" defaultval="�i�P�ʁj" /></td>
</tr>
<tr>
<th>���萔</th><td><osx:input type="text" name="#irisuu" size="5" value="" option="onchange='changeHacchuutanka()'"/></td>
</tr>
<tr>
<th>�����P�ʐ�</th><td><osx:input type="text" name="#hacchuutanisuu" size="5" value=""/></td>
</tr>
<tr>
<th>�Œᔭ����</th><td><osx:input type="text" name="#saiteihacchuusuu" size="5" value=""/></td>
</tr>
<tr>
<th>
�W���P��
</th>
<td>
<osx:input type="text" name="#hyoujuntanka" size="6" value="" option="onchange='changeHacchuutanka()'"/>
(�݌ɕ]����W�������@�Ōv�Z���鎞�̒P���B
�K�i��P�ʂ�����̌��������)
</td>
</tr>
<tr>
<th>
�����P��
</th>
<td>
<osx:input type="text" name="#tanka" size="6" value=""/>
(�����K�i��P�ʂ�����̉��i�����)
</td>
</tr>
<tr>
<th>
�W������
</th>
<td>
<osx:input type="text" name="#hyoujunbaika" size="6" value=""/>
(�Q�l���ځB�K�i��P�ʂ�����̔��������)
</td>
</tr>
<tr>
<th>�d�����[�h�^�C��</th><td><osx:input type="text" name="#shiireleadtime" size="5" value=""/></td>
</tr>
<tr>
<th>���x��</th><td><osxzs:ondotaiselect name="#ondotai" defaultval="�i���x�сj" /></td>
</tr>
<tr>
<th>�ܖ������Ǘ�</th>
<td>
<select name="#shoumikigen_flg">
<osx:option name="#shoumikigen_flg" value="1" />����
<osx:option name="#shoumikigen_flg" value="0" />���Ȃ�
</select>
</td>
</tr>
<tr>
<th>�ܖ���������</th><td><osx:input type="text" name="#shoumikigennissuu" size="5" value=""/></td>
</tr>
<tr>
<th>�o�׊�������</th><td><osx:input type="text" name="#shukkakigennissuu" size="5" value=""/></td>
</tr>
<tr>
<th>�ʔ���</th><td><osx:input type="checkbox" name="#kobetsuhacchuu_flg" value="1"/>�ʔ�������</td>
</tr>
<tr>
<th>�v���Ӌ敪</th><td><osxzs:youchuuikubunselect name="#youchuuihin_flg" defaultval="0"/></td>
</tr>
<tr>
<th>�v���x��</th><td><osx:input type="checkbox" name="#youraberu_flg" value="1"/>���׃��x���𔭍s����</td>
</tr>
<tr>
<th>�啪��</th><td>
<osxzs:shouhindaibunrui name="#daibunrui" type="select" defaultval="" />
</td>
</tr>
<tr>
<th>P�R�[�h</th><td><osx:input type="text" name="#pcode" size="6" value=""/></td>
</tr>
<tr>
<!-- <th>�݌ɕ]�����@</th><td></td> �݌ɕ]�����@�͈ړ����� -->
<input type="hidden" name="#zaikohyoukahouhou" value="1">
<th>JAN�R�[�h</th><td><osx:input type="text" name="#jancode" size="80" value=""/></td>
</tr>
<tr>
<th>�^��</th><td><osx:input type="text" name="#kataban" size="80" value=""/></td>
</tr>
<tr>
<th>���P�[�V�����R�[�h1</th><td><osx:input type="text" name="#location_id1" size="6" value=""/></td>
</tr>
<tr>
<th>���P�[�V�����R�[�h2</th><td><osx:input type="text" name="#location_id2" size="6" value=""/></td>
</tr>
<tr>
<th>���P�[�V�����R�[�h3</th><td><osx:input type="text" name="#location_id3" size="6" value=""/></td>
</tr>
<tr>
<th>�׎p</th><td><osx:input type="text" name="#nisugata" size="80" value=""/></td>
</tr>
<tr>
<th>�����R�����g</th><td><osx:input type="text" name="#hacchuucomment" size="80" value=""/></td>
</tr>
<tr>
<th>�̔��敪</th><td><osxzs:hanbaikubunselect name="#hanbaikubun" defaultval="0"/></td>
</tr>
<tr>
<th>���</th>
<td>
<select name="#shubetsu">
<osx:option name="#shubetsu" value="��"/>��
<osx:option name="#shubetsu" value="���z�i"/>���z�i
<osx:option name="#shubetsu" value="���H�i"/>���H�i
</select>
</td>
</tr>
<tr>
<th>���T�����z���t���O</th>
<td><osx:input type="checkbox" name="#mochikoshi_flg" value="1"/></td>
</tr>
<tr>
<th>�I���t���O</th>
<td><osx:input type="checkbox" name="#shuubai_flg" value="1"/></td>
</tr>
<tr>
<th>�[��[�i�t���O</th>
<td><osx:input type="checkbox" name="#shinyanouhin_flg" value="1"/></td>
</tr>
</table>
<input type="submit" value="����">
</osx:form>
