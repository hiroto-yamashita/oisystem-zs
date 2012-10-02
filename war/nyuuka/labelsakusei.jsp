<%@ page contentType="text/html; charset=SJIS" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<%@ page import="java.util.*,com.oisix.oisystemfr.TransactionServlet,com.oisix.oisystemfr.TransactionEvent,com.oisix.oisystemzs.ejb.*" %>
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
<osx:form transaction="Makelabelpdf.ev" result="nyuuka/labelpdf.ev" method="post">
<%
int labelsuu = 0;
%>
<osxzs:labelsakusei size="20">
<osx:prev action="nyuuka/labelsakusei.ev">
前の20件
</osx:prev>
<osx:next action="nyuuka/labelsakusei.ev">
次の20件
</osx:next>
<osx:total/>件中<osx:startind />件目から<osx:endind />件目までを表示しています。<br>
<table><tr>
<th>商品コード</th>
<th>商品名</th>
<th>枚数</th>
<th>賞味期限</th>
<th>出荷期限</th>
</tr>
<osx:items>
<%
HashMap item = (HashMap)request.getAttribute("ITEM");
Date shoumikigen = (Date)item.get("shoumikigen");
Date shukkakigen = (Date)item.get("shukkakigen");
Integer nyuukayotei_id = (Integer)item.get("nyuukayotei_id");
String strhacchuusuuryou = ((Float)item.get("hacchuusuuryou")).toString();
if(shoumikigen != null && shukkakigen != null){
request.setAttribute("shoumikigen" , shoumikigen);
request.setAttribute("shukkakigen" , shukkakigen);

%>
<tr>
<td><osx:item field="shouhin_id" />
<input type="hidden" name='<%="#shouhin_id"+labelsuu %>' value="<%=item.get("shouhin_id")%>" /></td>
<td><osx:item field="shouhin" />
<input type="hidden" name='<%="#shouhin"+labelsuu %>' value="<%=item.get("shouhin")%>" /></td>
<td><osx:input type="text" name='<%="#maisuu"+labelsuu %>' value="<%=strhacchuusuuryou%>" size="6" /></td>
<td>
<osx:yearselect name='<%="#shoumiyear"+labelsuu %>' from="2000" to="2020" datekey="shoumikigen" />年
<osx:monthselect name='<%="#shoumimonth"+labelsuu %>' datekey="shoumikigen" />月
<osx:dateselect name='<%="#shoumidate"+labelsuu %>' datekey="shoumikigen" />日
</td>
<td>
<osx:yearselect name='<%="#shukkayear"+labelsuu %>' from="2000" to="2020" datekey="shukkakigen" />年
<osx:monthselect name='<%="#shukkamonth"+labelsuu %>' datekey="shukkakigen" />月
<osx:dateselect name='<%="#shukkadate"+labelsuu %>' datekey="shukkakigen" />日
</td>
</tr>
<%}else{%>
<tr>
<td><osx:item field="shouhin_id" />
<input type="hidden" name='<%="#shouhin_id"+labelsuu %>' value="<%=item.get("shouhin_id")%>" /></td>
<td><osx:item field="shouhin" />
<input type="hidden" name='<%="#shouhin"+labelsuu %>' value="<%=item.get("shouhin")%>" /></td>
<td><osx:input type="text" name='<%="#maisuu"+labelsuu %>' value="<%=strhacchuusuuryou%>" size="6" /></td>
<td><input type="hidden" name='<%="#shoumiyear"+labelsuu %>' value="0" /></td>
<td><input type="hidden" name='<%="#shukkayear"+labelsuu %>' value="0" /></td>
</tr>
<%}%>
<input type="hidden" name="#nyuukayotei_id<%=labelsuu%>" value="<%=nyuukayotei_id%>" />
<%
labelsuu++;
%>
</osx:items>
</table>
ブランク枚数&nbsp;<input type="text" name="#burankumaisuu" value="" size="6" >
<%
String strlabelsuu = Integer.toString(labelsuu);
%>
<input type="hidden" name="#labelsuu" value="<%=labelsuu%>">
<input type="submit" value="ラベル作成">
</osxzs:labelsakusei>
</osx:form>
