<%@ page contentType="text/html; charset=SJIS" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<osx:form action="nyuuka/yoteihyoupdf.ev" method="post">
���t��I�����ĉ�����<br>
<osx:yearselect name="#year" from="2000" to="2020"/>�N
<osx:monthselect name="#month"/>��
<osx:dateselect name="#date"/>��&nbsp;
<br><input type="submit" value="���ח\��\�쐬">
</osx:form>

