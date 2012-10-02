<%@ page contentType="text/html; charset=SJIS" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
商品コードを入力してください
<osx:form action="zaiko/shoumihenkou.ev" method="post" option="name='main'" >
<% for (int i=0; i<10; i++) { %>
<osx:input type="text" name='<%="shouhin_id"+i %>' size="6" /><span style="cursor:hand" onClick="openSubSearch('../shared/shouhinsearch.ev?form=main&shouhin_id=shouhin_id<%=i%>&shouhin=shouhin<%=i%>&kikaku=kikaku<%=i%>')"><osx:img src="shared/image/icon_search01.gif" width="17" height="15" /></span>
<span id="shouhin<%=i%>"></span>
<span id="kikaku<%=i%>"></span>
<br>
<% } %>
<input type="submit" name="submit" value="賞味期限を検索">
</osx:form>
