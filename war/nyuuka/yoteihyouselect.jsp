<%@ page contentType="text/html; charset=SJIS" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<osx:form action="nyuuka/yoteihyoupdf.ev" method="post">
日付を選択して下さい<br>
<osx:yearselect name="#year" from="2000" to="2020"/>年
<osx:monthselect name="#month"/>月
<osx:dateselect name="#date"/>日&nbsp;
<br><input type="submit" value="入荷予定表作成">
</osx:form>

