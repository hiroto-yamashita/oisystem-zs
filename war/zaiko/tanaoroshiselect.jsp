<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="com.oisix.oisystemfr.UrlUtil" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<script language="JavaScript1.1">
function nextpage(page) {
  document.main.action=page;
  document.main.submit();
}
</script>
<osx:form action="" method="post" option='name="main"'>
�݌ɓ��t
<osx:yearselect name="zaikoyear" from="2000" to="2020"/>�N
<osx:monthselect name="zaikomonth"/>��
<osx:dateselect name="zaikodate"/>��<br>
<input type="button" value="�I�����X�g�쐬" onClick="nextpage('<%=UrlUtil.encode(request, response, "zaiko/maketanaoroshilist.ev") %>')">
<input type="button" value="�I������" onClick="nextpage('<%=UrlUtil.encode(request, response, "zaiko/tanaoroshiinput.ev") %>')">
</osx:form>
