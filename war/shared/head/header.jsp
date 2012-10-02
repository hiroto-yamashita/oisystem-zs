<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="java.text.SimpleDateFormat,com.oisix.oisystemzs.ejb.*" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<!--this is default header-->
<%
// ���t�\���p
SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
String strDate = sdf.format(new java.util.Date());
// �����\���p
UserLocal user = (UserLocal)session.getAttribute("USER");
SoukoData souko = (SoukoData)session.getAttribute("SOUKO");
OfficeData office = (OfficeData)session.getAttribute("OFFICE");
// �A�N�Z�X����
boolean isGuest = false;
if (user.getPriv() == 3) isGuest = true;
%>
<table width="100%">
<tr>
<td class="layout" align="left" valign="top" rowspan="3" width="70%">
<osx:img src="shared/image/logo_zs.gif" width="150" height="26" />
</td>
<td class="layout" nowrap>
�y<%=souko.getName()%>�z
</td>
<td class="layout" nowrap>
�y<%=office.getName()%>�z
</td>
<td class="layout" nowrap>
�y<%=user.getName1()%>�z
</td>
<td class="layout" align="right" nowrap>
<%=strDate%>
</td>
</tr></table>
<script language="JavaScript">
<!--
var menuObj;

menuObj = createMenu('<osx:a href="mainmenu.ev" option="class=link-navigation">���C�����j���[</osx:a>');
menuObj = createMenu("����");
<% if (!isGuest) { %>
  menuObj.addSubMenu('<osx:a href="hacchuu/zaikofusokusearch.ev" option="class=link-navigation">�\��݌ɕs���Ɖ�</osx:a>');
  menuObj.addSubMenu('<osx:a href="inputclear.ev?inputclearnext=hacchuu/hacchuuinput.ev" option="class=link-navigation">��������</osx:a>');
<% } %>
  menuObj.addSubMenu('<osx:a href="hacchuu/yoteizaikosearch.ev" option="class=link-navigation">�\��݌ɏƉ�</osx:a>');
  menuObj.addSubMenu('<osx:a href="hacchuu/hacchuusearch.ev" option="class=link-navigation">�����Ɖ�</osx:a>');
menuObj = createMenu("���ׁE����");
<% if (!isGuest) { %>
  menuObj.addSubMenu('<osx:a href="nyuuka/yoteihyouselect.ev" option="class=link-navigation">���ח\��\</osx:a>');
  menuObj.addSubMenu('<osx:a href="nyuuka/yoteisearch.ev" option="class=link-navigation">���ח\��Ɖ�</osx:a>');
  menuObj.addSubMenu('<osx:a href="nyuuka/nyuukoinput.ev" option="class=link-navigation">���ɓ���</osx:a>');
<% } %>
  menuObj.addSubMenu('<osx:a href="nyuuka/nyuukosearch.ev" option="class=link-navigation">���ɏƉ�</osx:a>');
menuObj = createMenu("�o�ׁE�o��");
  menuObj.addSubMenu('<osx:a href="inputclear.ev?inputclearnext=shukka/shukkayoteiinput.ev" option="class=link-navigation">�o�ח\�����</osx:a>');
  menuObj.addSubMenu('<osx:a href="shukka/shukkayoteisearch.ev" option="class=link-navigation">�o�ח\��Ɖ�</osx:a>');
<% if (!isGuest) { %>
  menuObj.addSubMenu('<osx:a href="shukka/shukkoinput.ev" option="class=link-navigation">�o�ɓ���</osx:a>');
<% } %>
  menuObj.addSubMenu('<osx:a href="shukka/shukkosearch.ev" option="class=link-navigation">�o�ɏƉ�</osx:a>');
menuObj = createMenu("�݌�");
<% if (!isGuest) { %>
  menuObj.addSubMenu('<osx:a href="zaiko/zaikosearch.ev" option="class=link-navigation">�݌ɏƉ�</osx:a>');
  menuObj.addSubMenu('<osx:a href="zaiko/tanaoroshilistselect.ev" option="class=link-navigation">�I�����X�g�쐬</osx:a>');
  menuObj.addSubMenu('<osx:a href="zaiko/shoumihenkousearch.ev" option="class=link-navigation">�ܖ������ύX</osx:a>');
<% } %>
  menuObj.addSubMenu('<osx:a href="zaiko/nyuushukkosearch.ev" option="class=link-navigation">���o�ɏƉ�</osx:a>');
menuObj = createMenu("���Z");
  menuObj.addSubMenu('<osx:a href="kessan/tanaoroshiselect.ev" option="class=link-navigation">�I�����Y�񍐏��쐬</osx:a>');
  menuObj.addSubMenu('<osx:a href="kessan/kaikakekinsearch.ev" option="class=link-navigation">���|���Ɖ�</osx:a>');
menuObj = createMenu("�}�X�^�[�����e");
  menuObj.addSubMenu('<osx:a href="maint/shouhin.ev" option="class=link-navigation">���i�}�X�^�[</osx:a>');
<% if (!isGuest) { %>
  menuObj.addSubMenu('<osx:a href="maint/shiiresaki.ev" option="class=link-navigation">�d����}�X�^�[</osx:a>');
<% } %>
//-->
</script>
<SCRIPT language="javascript">
drawMenu();
</SCRIPT>
