<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="com.oisix.oisystemzs.ejb.UserLocal" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<%
// �A�N�Z�X����
UserLocal user = (UserLocal)session.getAttribute("USER");
boolean isGuest = false;
if (user.getPriv() == 3) isGuest = true;
%>
<% if (!isGuest) { %>
<osx:form action="maint/shouhininput.ev" method="get" >
���V�K����<br>
<input type="submit" name="submit" value="�V�K����">
</osx:form>
<p>
<% } %>
<osx:form action="maint/shouhinedit.ev" method="get" option="name='main'" >
���ύX�E�Ɖ�<br>
<table>
<td class="layout">���i�R�[�h</td><td class="layout">���i��</td><td class="layout">�K�i</td>
</tr>
<tr>
<td class="layout">
<osx:input type="text" name='<%="#shouhin_id0" %>' size="6" /><span style="cursor:hand" onClick="openSubSearch('../shared/shouhinsearch.ev?form=main&shouhin_id=shouhin_id0&shouhin=shouhin0&kikaku=kikaku0')"><osx:img src="shared/image/icon_search01.gif" width="17" height="15" /></span>
</td>
<td class="layout"><span id="shouhin0"></span></td>
<td class="layout"><span id="kikaku0"></span></td>
</tr>
</table>
<input type="submit" name="submit" value="�ύX�E�Ɖ�">
</osx:form>

