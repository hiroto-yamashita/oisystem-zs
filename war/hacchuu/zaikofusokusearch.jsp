<%@ page contentType="text/html; charset=SJIS" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<osx:form action="hacchuu/zaikofusoku.ev" method="post">
<osx:yearselect name="year" from="2000" to="2010"/>�N
<osx:monthselect name="month"/>��
<osx:dateselect name="date"/>���̗\��݌�<br>
<osxzs:shouhindaibunrui name="daibunrui" type="checkbox" /><br>
<input type="submit" name="submit" value="�Ɖ�">
</osx:form>
