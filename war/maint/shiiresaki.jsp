<%@ page contentType="text/html; charset=SJIS" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<osx:form action="maint/shiiresakiinput.ev" method="get" >
■新規入力<br>
<input type="submit" name="submit" value="新規入力">
</osx:form>
<p>
<osx:form action="maint/shiiresakiedit.ev" method="get" option="name='main'" >
■変更<br>
仕入先コード <osx:input type="text" name='<%="#shiiresaki_id0" %>' size="6" /><span style="cursor:hand" onClick="openSubSearch('../shared/shiiresakisearch.ev?form=main&shiiresaki_id=shiiresaki_id0')"><osx:img src="shared/image/icon_search01.gif" width="17" height="15" /></span><br>
<input type="submit" name="submit" value="変更">
</osx:form>

