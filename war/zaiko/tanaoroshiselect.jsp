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
在庫日付
<osx:yearselect name="zaikoyear" from="2000" to="2020"/>年
<osx:monthselect name="zaikomonth"/>月
<osx:dateselect name="zaikodate"/>日<br>
<input type="button" value="棚卸リスト作成" onClick="nextpage('<%=UrlUtil.encode(request, response, "zaiko/maketanaoroshilist.ev") %>')">
<input type="button" value="棚卸入力" onClick="nextpage('<%=UrlUtil.encode(request, response, "zaiko/tanaoroshiinput.ev") %>')">
</osx:form>
