<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="com.oisix.oisystemzs.ejb.UserLocal" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%
// �A�N�Z�X����
UserLocal user = (UserLocal)session.getAttribute("USER");
boolean isGuest = false;
if (user.getPriv() == 3) isGuest = true;
int tdwidth = 33;
%>
<br>
<table width="90%" align="center" class="layout"><tr>
<th nowrap width="<%=tdwidth%>%" class="layout">
������
<hr noshade>
</th>
<th nowrap width="<%=tdwidth%>%" class="layout">
�����ׁE����
<hr noshade>
</th>
<th nowrap width="<%=tdwidth%>%" class="layout">
���o�ׁE�o��
<hr noshade>
</th>
</tr><tr>
<td nowrap valign="top" class="layout" style="line-height:150%">
<% if (!isGuest) { %>
<osx:a href="hacchuu/zaikofusokusearch.ev">�\��݌ɕs���Ɖ�</osx:a><br>
<osx:a href="inputclear.ev?inputclearnext=hacchuu/hacchuuinput.ev">��������</osx:a><br>
<% } %>
<osx:a href="hacchuu/yoteizaikosearch.ev">�\��݌ɏƉ�</osx:a><br>
<osx:a href="inputclear.ev?inputclearnext=hacchuu/hacchuusearch.ev">�����Ɖ�</osx:a><br>
</td>
<td nowrap valign="top" class="layout" style="line-height:150%">
<% if (!isGuest) { %>
<osx:a href="nyuuka/yoteihyouselect.ev">���ח\��\</osx:a><br>
<osx:a href="nyuuka/yoteisearch.ev">���ח\��Ɖ�</osx:a><br>
<osx:a href="nyuuka/nyuukoinput.ev">���ɓ���</osx:a><br>
<% } %>
<osx:a href="nyuuka/nyuukosearch.ev">���ɏƉ�</osx:a><br>
</td>
<td nowrap valign="top" class="layout" style="line-height:150%">
<osx:a href="inputclear.ev?inputclearnext=shukka/shukkayoteiinput.ev">�o�ח\�����</osx:a><br>
<osx:a href="shukka/shukkayoteisearch.ev">�o�ח\��Ɖ�</osx:a><br>
<% if (!isGuest) { %>
<osx:a href="shukka/shukkoinput.ev">�o�ɓ���</osx:a><br>
<% } %>
<osx:a href="shukka/shukkosearch.ev">�o�ɏƉ�</osx:a><br>
</td>
</tr>
<tr><td class="layout"><br></td></tr>
<tr>
<th nowrap width="<%=tdwidth%>%" class="layout">
���݌�
<hr noshade>
</th>
<th nowrap width="<%=tdwidth%>%" class="layout">
�����Z
<hr noshade>
</th>
<th nowrap width="<%=tdwidth%>%" class="layout">
���}�X�^�[�����e
<hr noshade>
</th>
</tr><tr>
<td nowrap valign="top" class="layout" style="line-height:150%">
<% if (!isGuest) { %>
<osx:a href="zaiko/zaikosearch.ev">�݌ɏƉ�</osx:a><br>
<osx:a href="zaiko/tanaoroshilistselect.ev">�I�����X�g�쐬</osx:a><br>
<osx:a href="zaiko/shoumihenkousearch.ev">�ܖ������ύX</osx:a><br>
<% } %>
<osx:a href="zaiko/nyuushukkosearch.ev">���o�ɏƉ�</osx:a><br>
</td>
<td nowrap valign="top" class="layout" style="line-height:150%">
<osx:a href="kessan/tanaoroshiselect.ev">�I�����Y�񍐏��쐬</osx:a><br>
<osx:a href="kessan/kaikakekinsearch.ev">���|���Ɖ�</osx:a><br>
</td>
<td nowrap valign="top" class="layout" style="line-height:150%">
<osx:a href="maint/shouhin.ev">���i�}�X�^�[</osx:a><br>
<% if (!isGuest) { %>
<osx:a href="maint/shiiresaki.ev">�d����}�X�^�[</osx:a><br>
<% } %>
</td>
</tr></table>
<br>
