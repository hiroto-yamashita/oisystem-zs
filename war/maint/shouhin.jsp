<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="com.oisix.oisystemzs.ejb.UserLocal" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<%
// アクセス権限
UserLocal user = (UserLocal)session.getAttribute("USER");
boolean isGuest = false;
if (user.getPriv() == 3) isGuest = true;
%>
<% if (!isGuest) { %>
<osx:form action="maint/shouhininput.ev" method="get" >
■新規入力<br>
<input type="submit" name="submit" value="新規入力">
</osx:form>
<p>
<% } %>
<osx:form action="maint/shouhinedit.ev" method="get" option="name='main'" >
■変更・照会<br>
<table>
<td class="layout">商品コード</td><td class="layout">商品名</td><td class="layout">規格</td>
</tr>
<tr>
<td class="layout">
<osx:input type="text" name='<%="#shouhin_id0" %>' size="6" /><span style="cursor:hand" onClick="openSubSearch('../shared/shouhinsearch.ev?form=main&shouhin_id=shouhin_id0&shouhin=shouhin0&kikaku=kikaku0')"><osx:img src="shared/image/icon_search01.gif" width="17" height="15" /></span>
</td>
<td class="layout"><span id="shouhin0"></span></td>
<td class="layout"><span id="kikaku0"></span></td>
</tr>
</table>
<input type="submit" name="submit" value="変更・照会">
</osx:form>

