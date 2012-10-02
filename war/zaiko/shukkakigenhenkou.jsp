<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="com.oisix.oisystemzs.ejb.*,java.util.*,java.text.*,com.oisix.oisystemzs.objectmap.*,com.oisix.oisystemfr.TransactionServlet,com.oisix.oisystemfr.TransactionEvent" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<jsp:useBean id="event" scope="request" class="com.oisix.oisystemzs.eventhandler.ShukkakigenhenkouEvent" />
<%
TransactionEvent eventerr = (TransactionEvent)
  request.getAttribute(TransactionServlet.RESULTKEY);
if (eventerr != null) {
  Iterator iter = eventerr.getErrorlist().iterator();
%>
エラーが発生しました。
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
<table width="100%">
<tr>
<th>商品コード</th>
<th>商品名</th>
<th>規格</th>
<th>賞味期限</th>
<th>日付</th>
<th>数量</th>
</tr>
<%
SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
%>
<tr>
<td><%=(String)event.getData().get("SHOUHIN_ID")%></td>
<td><%=(String)event.getData().get("SHOUHINMEI")%></td>
<td><%=(String)event.getData().get("KIKAKU")%></td>
<td><%=sdf.format((java.util.Date)event.getData().get("SHOUMIKIGEN"))%></td>
<td><%=sdf.format((java.util.Date)event.getData().get("ZAIKODATE"))%></td>
<td><%=String.valueOf((Float)event.getData().get("SUURYOU"))%></td>
</tr>
</table>
<osx:form transaction="doshukkakigenhenkou.ev" result="zaiko/shukkakigenhenkousuccess.ev" method="post">
出荷期限
<osx:yearselect name="#kigenyear" from="2000" to="2010" datekey="shukkakigen" />年
<osx:monthselect name="#kigenmonth" datekey="shukkakigen" />月
<osx:dateselect name="#kigendate" datekey="shukkakigen" />日<br>
<input type="hidden" name="#id" value="<%=event.getZaikomeisai_id()%>">
<input type="submit" name="submit" value="変更">
</osx:form>
