<%@ page contentType="text/html; charset=SJIS" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<osx:form action="zaiko/tanaoroshilist.ev" method="post">
<osx:yearselect name="year" from="2000" to="2010"/>年
<osx:monthselect name="month"/>月
<osx:dateselect name="date"/>日<br>
<osxzs:shouhindaibunrui name="daibunrui" type="checkbox" /><br>
入出荷予定を<input type="radio" name="isyotei" value="true" checked>含める <input type="radio" name="isyotei" value="false">含めない<br>
<input type="submit" name="submit" value="棚卸リスト作成">
</osx:form>
