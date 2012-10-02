<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="com.oisix.oisystemfr.*,com.oisix.oisystemzs.ejb.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<jsp:useBean id="event" scope="request" class="com.oisix.oisystemzs.eventhandler.ShiiresakieditEvent" />
<%
if (event.getResult() == event.RC_INPUTERROR) {
  Iterator iter = event.getErrorlist().iterator();
  while (iter.hasNext()) {
%>
<%=iter.next() %><br>
<%
  } return; }
// ここまでエラーだった場合
ShiiresakiData shiire = event.getShiiresaki();
%>
<osx:form action="maint/shiiresakieditfinish.ev" method="post" option="name='main'">
<table>
<tr>
<th>仕入先コード</th><td><%=shiire.getShiiresaki_id() %><input type="hidden" name="shiiresaki_id" value="<%=shiire.getShiiresaki_id() %>"></td>
</tr>
<tr>
<th>仕入先名</th><td><osx:input type="text" name="name" size="80" value="<%=shiire.getName() %>"/></td>
</tr>
<tr>
<th>仕入先フリガナ</th><td><osx:input type="text" name="furigana" size="80" value="<%=shiire.getFurigana() %>"/></td>
</tr>
<tr>
<th>仕入先フリガナ1(前株など)</th><td><osx:input type="text" name="furigana1" size="80" value="<%=shiire.getFurigana1() %>"/></td>
</tr>
<tr>
<th>仕入先フリガナ2(後株など)</th><td><osx:input type="text" name="furigana2" size="80" value="<%=shiire.getFurigana2() %>"/></td>
</tr>
<tr>
<th>郵便番号(-入り)</th><td><osx:input type="text" name="yuubin" size="8" value="<%=shiire.getYuubin() %>"/></td>
</tr>
<tr>
<th>住所</th><td><osx:input type="text" name="addr" size="80" value="<%=shiire.getAddr() %>"/></td>
</tr>
<tr>
<th>電話番号(-入り)</th><td><osx:input type="text" name="tel" size="15" value="<%=shiire.getTel() %>"/></td>
</tr>
<tr>
<th>FAX番号(-入り)</th><td><osx:input type="text" name="fax" size="15" value="<%=shiire.getFax() %>"/></td>
</tr>
<tr>
<th>担当者氏名</th><td><osx:input type="text" name="tantoushaname1" size="20" value="<%=shiire.getTantoushaname1() %>"/><osx:input type="text" name="tantoushaname2" size="20" value="<%=shiire.getTantoushaname2() %>"/></td>
</tr>
</table>
<input type="hidden" name="updated" value="<%=shiire.getUpdated().getTime()%>">
<input type="submit" value="変更">
</osx:form>
