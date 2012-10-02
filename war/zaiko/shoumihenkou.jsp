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
<osxzs:shoumisearch>
<osx:form transaction="doshoumihenkou.ev" result="zaiko/shoumihenkousuccess.ev" method="post" option="name='main'" >
<table>
<tr>
<th>商品コード</th>
<th>商品名</th>
<th>規格</th>
<th>賞味期限</th>
<th>出荷期限</th>
<th>数量</th>
<th>賞味期限<br>日数</th>
<th>出荷期限<br>日数</th>
</tr>
<%
String preshouhin = null;
boolean newShouhin = true;
int index = 0;
%>
<osx:items>
<%
index++;
HashMap item = (HashMap)request.getAttribute("ITEM");
String shouhin_id = (String)item.get("shouhin_id");
if (shouhin_id.equals(preshouhin)) {
    newShouhin = false;
} else {
    newShouhin = true;
    preshouhin = shouhin_id;
}
java.sql.Date shoumi = (java.sql.Date)item.get("shoumi");
request.setAttribute("shoumi", shoumi);
%>
<input type="hidden" name="#shouhin_id_<%=index %>" value="<%=shouhin_id %>">
<tr>
<td>
<% if (newShouhin) { %>
<osx:item field="shouhin_id" />
<% } else { %>
&nbsp;
<% } %>
</td>
<td>
<% if (newShouhin) { %>
<osx:item field="shouhin" />
<% } else { %>
&nbsp;
<% } %>
</td>
<td><osx:item field="kikaku" /></td>
<td>
<osx:yearselect name='<%="#year_"+shouhin_id+"_"+index %>' from="2000" to="2010" datekey="shoumi" />年
<osx:monthselect name='<%="#month_"+shouhin_id+"_"+index %>' datekey="shoumi" />月
<osx:dateselect name='<%="#date_"+shouhin_id+"_"+index %>' datekey="shoumi" />日
<input type="hidden" name="#orgyear_<%=shouhin_id %>_<%=index %>" value="<osx:item field="shoumiyear" />">
<input type="hidden" name="#orgmonth_<%=shouhin_id %>_<%=index %>" value="<osx:item field="shoumimonth" />">
<input type="hidden" name="#orgdate_<%=shouhin_id %>_<%=index %>" value="<osx:item field="shoumidate" />">
</td>
<td><osx:item field="shukkakigen" /></td>
<td align=right>
<osx:item field="suuryou" />
<input type="hidden" name="#suuryou_<%=shouhin_id %>_<%=index %>" value="<osx:item field="suuryou" />">
</td>
<td align=right>
<osx:item field="shoumikigennissuu" />
<input type="hidden" name="#shoumi_<%=shouhin_id %>_<%=index %>" value="<osx:item field="shoumikigennissuu" />">
</td>
<td align=right>
<osx:item field="shukkakigennissuu" />
<input type="hidden" name="#shukka_<%=shouhin_id %>_<%=index %>" value="<osx:item field="shukkakigennissuu" />">
</td>
</tr>
</osx:items>
</table>
<input type="submit" value="変更">
</osx:form>
</osxzs:shoumisearch>
