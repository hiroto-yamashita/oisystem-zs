<%@ page contentType="text/html; charset=SJIS" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<osx:form action="hacchuu/zaikofusoku.ev" method="post">
<osx:yearselect name="year" from="2000" to="2010"/>年
<osx:monthselect name="month"/>月
<osx:dateselect name="date"/>日の予定在庫<br>
<osxzs:shouhindaibunrui name="daibunrui" type="checkbox" /><br>
<input type="submit" name="submit" value="照会">
</osx:form>
