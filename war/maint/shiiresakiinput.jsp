<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="com.oisix.oisystemfr.*,com.oisix.oisystemzs.ejb.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<%
TransactionEvent event = (TransactionEvent)
  request.getAttribute(TransactionServlet.RESULTKEY);
if (event != null) {
  Iterator iter = event.getErrorlist().iterator();
%>
エラーです。以下のエラーメッセージに従い入力内容を見直してください。
<p>
<%
  while (iter.hasNext()) {
%>
<%=iter.next() %><br>
<%
  }
}
// ここまでエラーだった場合
%>
<osx:form transaction="doshiiresakiinput.ev" result="maint/shiiresakiinputsuccess.ev" method="post" option="name='main'">
<table>
<tr>
<th>仕入先コード</th><td><osx:input type="text" name="#shiiresaki_id" size="6" value=""/></td>
</tr>
<tr>
<th>仕入先名</th><td><osx:input type="text" name="#name" size="80" value=""/></td>
</tr>
<tr>
<th>仕入先フリガナ</th><td><osx:input type="text" name="#furigana" size="80" value=""/></td>
</tr>
<tr>
<th>仕入先フリガナ1</th><td><osx:input type="text" name="#furigana1" size="80" value=""/></td>
</tr>
<tr>
<th>仕入先フリガナ2</th><td><osx:input type="text" name="#furigana2" size="80" value=""/></td>
</tr>
<tr>
<th>郵便番号</th><td><osx:input type="text" name="#yuubin" size="8" value=""/></td>
</tr>
<tr>
<th>住所</th><td><osx:input type="text" name="#addr" size="80" value=""/></td>
</tr>
<tr>
<th>電話番号</th><td><osx:input type="text" name="#tel" size="15" value=""/></td>
</tr>
<tr>
<th>FAX番号</th><td><osx:input type="text" name="#fax" size="15" value=""/></td>
</tr>
<tr>
<th>担当者氏名</th><td><osx:input type="text" name="#tantoushaname1" size="20" value=""/><osx:input type="text" name="#tantoushaname2" size="20" value=""/></td>
</tr>
</table>
<input type="submit" value="入力">
</osx:form>
