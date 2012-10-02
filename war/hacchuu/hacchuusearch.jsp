<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="com.oisix.oisystemfr.UrlUtil,com.oisix.oisystemzs.ejb.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<%
UserLocal user = (UserLocal)session.getAttribute("USER");
%>
<script language="JavaScript1.1">
function submitnouhin() {
  document.forms[1].action='<%=UrlUtil.encode(request, response, "hacchuu/nouhinhenkou.ev") %>';
  document.forms[1].submit();
}

function submitstatus() {
  document.forms[1].action='<%=UrlUtil.encode(request, response, "hacchuu/updatestatus.ev") %>';
  document.forms[1].submit();
}

function toggle() {
  for (i=0; i<document.forms[1].elements.length-3; i++) {
    if (document.forms[1].elements[i].checked) {
      document.forms[1].elements[i].checked = false;
    } else {
      document.forms[1].elements[i].checked = true;
    }
  }
}

</script>
<osx:form action="hacchuu/hacchuusearch.ev" method="post" option='name="ichiran"'>
<osx:input type="checkbox" name="#hacchuubi" value="on" />������
<osx:yearselect name="#year" from="2000" to="2020"/>�N
<osx:monthselect name="#month"/>��
<osx:dateselect name="#date"/>��&nbsp;
�d����R�[�h<span style="cursor:hand" onClick="openSubSearch('../shared/shiiresakisearch.ev?form=ichiran&shiiresaki_id=shiiresaki_id')"><osx:img src="shared/image/icon_search01.gif" width="17" height="15" /></span> <osx:input type="text" name="#shiiresaki_id" size="6" value="" />&nbsp;
�����ԍ� <osx:input type="text" name="#hacchuu_bg" size="14" value="" />
<%-- IE5.0�ł́Asubmit�{�^���܂���name="submit"��form�v�f�������form.submit()���G���[�ɂȂ邽�� --%>
<input type="button" value="�Ɖ�" onclick="javascript:document.forms[0].submit()">
</osx:form>
<p>

<osxzs:hacchuusearch size="50">
<osx:form action="hacchuu/hacchuusho.ev" method="post">
<osx:total/>����<osx:startind />���ڂ���<osx:endind />���ڂ܂ł�\�����Ă��܂��B<osx:prev action="hacchuu/hacchuusearch.ev">
�O��50��
</osx:prev>
<osx:next action="hacchuu/hacchuusearch.ev">
����50��
</osx:next>
<br>
<table width="100%">
<tr>
<th>������</th>
<th>�����ԍ�</th>
<th>������</th>
<th>�d����R�[�h</th>
<th>�d����</th>
<th>�����X�e�[�^�X</th>
<th></th><th></th>
</tr>

<osx:items>
<%
HashMap item = (HashMap)request.getAttribute("ITEM");
String status = (String)item.get("status");
String statusid = "status_" + (String)item.get("hacchuu_bg");
%>
<tr>
<td><input type="checkbox" name='id' value='<osx:item field="hacchuu_id"/>'></td>
<td><osx:item field="hacchuu_bg" /></td>
<td><osx:item field="hacchuu_date" dateformat="yyyy/MM/dd" /></td>
<td><osx:item field="shiiresaki_id" /></td>
<td><osx:item field="shiiresaki_name" /></td>
<td><osxzs:hacchuustatusselect name='<%=statusid %>' defaultval='<%=status %>' /></td>
<td><osx:a href="hacchuu/hacchuushousai.ev?" itemkey="lineparam">�ڍ�</osx:a></td>
<td>
<% if (user.getPriv() != 3) { %>
<osx:a href="hacchuu/hacchuusakujo.ev?" itemkey="lineparam">�폜</osx:a>
<% } %>
</td>
</tr>
</osx:items>
</table>
<osx:prev action="hacchuu/hacchuusearch.ev">
�O��50��
</osx:prev>
<osx:next action="hacchuu/hacchuusearch.ev">
����50��
</osx:next>
<br>
<% if (user.getPriv() != 3) { %>
<input type="button" value="�������쐬" onclick="javascript:document.forms[1].submit()">
<input type="button" value="�[�i���ꊇ�ύX" onclick="javascript:submitnouhin()">
<input type="button" value="�����X�e�[�^�X�X�V" onclick="javascript:submitstatus()">
<% } %>
<input type="button" value="�`�F�b�N�{�b�N�X���]" onclick="javascript:toggle()">
</osx:form>
</osxzs:hacchuusearch>
