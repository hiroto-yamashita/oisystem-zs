<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="com.oisix.oisystemfr.UrlUtil,com.oisix.oisystemzs.ejb.*" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<osxzs:nyuukayoteisearch size="20">
<osx:form action="nyuuka/labelsakusei.ev" method="post">
<osx:total/>����<osx:startind />���ڂ���<osx:endind />���ڂ܂ł�\�����Ă��܂��B<br>
<osx:prev action="nyuuka/nyuukayoteishoukai.ev">�O��20��</osx:prev>
<osx:next action="nyuuka/nyuukayoteishoukai.ev">����20��</osx:next>
<table width="100%">
<tr>
<th></th>
<th>�����ԍ�</th>
<th>���ח\���</th>
<th>���׋敪</th>
<th>�d����</th>
<th>���i�R�[�h</th>
<th>���i��</th>
<th>����</th>
<th>�P��</th>
<th></th>
</tr>
<script language="JavaScript1.2">
function submitnyuukokakutei() {
  document.forms[0].action='<%=UrlUtil.encode(request, response, "nyuuka/nyuukokakuteiinput.ev") %>';
  document.forms[0].submit();
}
function toggle() {
  for (i=0; i<document.forms[0].elements.length-3; i++) {
    if (document.forms[0].elements[i].checked) {
      document.forms[0].elements[i].checked = false;
    } else {
      document.forms[0].elements[i].checked = true;
    }
  }
}
</script>
<osx:items>
<tr>
<td><input type="checkbox" name='nyuukayotei_id' value='<osx:item field="nyuukayotei_id"/>'></td>
<td><osx:item field="hacchuu_bg" /></td>
<td><osx:item field="nyuukayotei_date" dateformat="yyyy/MM/dd" /></td>
<td><osx:item field="nyuukokubun" /></td>
<td><osx:item field="name" /></td>
<td><osx:item field="shouhin_id" /></td>
<td><osx:item field="shouhin" /></td>
<td align="right"><osx:item field="nyuukasuuryou" /></td>
<td><osx:item field="nyuukatani" /></td>
<td><osx:a href="nyuuka/nyuukayoteishousai.ev?" itemkey="lineparam">�ڍ�</osx:a></td>
</tr>
</osx:items>
</table>
<%
UserLocal user = (UserLocal)session.getAttribute("USER");
if (user.getPriv() != 3) { %>
<input type="button" value="���x���쐬" onclick="javascript:document.forms[0].submit()">
<input type="button" value="���Ɋm��" onclick="javascript:submitnyuukokakutei()">
<input type="button" value="�`�F�b�N�{�b�N�X���]" onclick="javascript:toggle()">
<% } %>
</osx:form>
</osxzs:nyuukayoteisearch>