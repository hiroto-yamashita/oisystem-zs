<%@ page contentType="text/html; charset=SJIS" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<osx:form action="maint/shiiresakiinput.ev" method="get" >
���V�K����<br>
<input type="submit" name="submit" value="�V�K����">
</osx:form>
<p>
<osx:form action="maint/shiiresakiedit.ev" method="get" option="name='main'" >
���ύX<br>
�d����R�[�h <osx:input type="text" name='<%="#shiiresaki_id0" %>' size="6" /><span style="cursor:hand" onClick="openSubSearch('../shared/shiiresakisearch.ev?form=main&shiiresaki_id=shiiresaki_id0')"><osx:img src="shared/image/icon_search01.gif" width="17" height="15" /></span><br>
<input type="submit" name="submit" value="�ύX">
</osx:form>

