<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="java.util.*,com.oisix.oisystemfr.TransactionServlet,com.oisix.oisystemfr.TransactionEvent,com.oisix.oisystemzs.ejb.SoukoData" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<%
TransactionEvent errevent = (TransactionEvent)
  request.getAttribute(TransactionServlet.RESULTKEY);
if (errevent != null) {
  Iterator iter = errevent.getErrorlist().iterator();
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
SoukoData souko = (SoukoData)session.getAttribute("SOUKO");
%>
<osx:form transaction="doshukkayoteiinput.ev" result="shukka/shukkayoteiinputsuccess.ev" method="post" option="name='main'">
<table>
<tr><th class="layout">
出庫区分
</th><td class="layout">
<osxzs:shukkokubunselect name="#shukkokubun" defaultval="（区分）" />
</td></tr>
<tr><th class="layout">
出荷予定種別
</th><td class="layout">
<!--<osxzs:shukkayoteishubetsuselect name="#shukkayoteishubetsu" defaultval="（種別）" selected="3" />-->
<!--出荷予定種別はその他(3)で固定-->
<input type="hidden" name="#shukkayoteishubetsu" value="3">その他
</td></tr>
<tr><th class="layout">
出荷予定日
</th><td class="layout">
<osx:yearselect name="#shukka_year" from="2000" to="2020"/>年
<osx:monthselect name="#shukka_month"/>月
<osx:dateselect name="#shukka_date"/>日
</td></tr>
<tr><th class="layout">
納品予定日
</th><td class="layout">
<osx:yearselect name="#nouhin_year" from="2000" to="2020"/>年
<osx:monthselect name="#nouhin_month"/>月
<osx:dateselect name="#nouhin_date"/>日
</td></tr>
<tr><th class="layout">
出荷元コード
</th><td class="layout">
<osx:input type="text" name="#shukkamoto_id" size="6" value="<%=souko.getSouko_id()%>" />
</td></tr>
<tr><th class="layout">
納品先コード
</th><td class="layout">
<osx:input type="text" name="#nouhinsaki_id" size="6" value="" />
</td></tr>
<tr><th class="layout">
納品先名
</th><td class="layout">
<osx:input type="text" name="#nouhinsakimei" size="30" value="" />
</td></tr>
<tr><th class="layout">
納品先郵便番号
</th><td class="layout">
<osx:input type="text" name="#nohinsakiyuubin" size="8" value="" />
</td></tr>
<tr><th class="layout">
納品先住所
</th><td class="layout">
<osx:input type="text" name="#nouhinsakijuusho" size="80" value="" />
</td></tr>
<tr><th class="layout">
納品先電話番号
</th><td class="layout">
<osx:input type="text" name="#nouhinsakitel" size="20" value="" />
</td></tr>
<tr><th class="layout">
納品先FAX番号
</th><td class="layout">
<osx:input type="text" name="#nouhinsakifax" size="20" value="" />
</td></tr>
<tr><td class="layout" colspan="2">

<table width="100%">
<tr>
<th>商品コード</th>
<th>商品名</th>
<th>規格</th>
<th>数量</th>
<th>単位</th>
</tr>
<% for (int i = 0; i < 30; i++) { %>
<tr>
<td><osx:input type="text" name='<%="#shouhin_id"+i %>' size="10" value="" /><span style="cursor:hand" onClick="openSubSearch('../shared/shouhinsearch.ev?form=main&shouhin_id=shouhin_id<%=i%>&shouhin=shouhin<%=i%>&kikaku=kikaku<%=i%>&tani=tani<%=i%>')"><osx:img src="shared/image/icon_search01.gif" width="17" height="15" /></span></td>
<td><span id="shouhin<%=i%>"></span></td>
<td><span id="kikaku<%=i%>"></span></td>
<td><osx:input type="text" name='<%="#suuryou"+i %>' size="6" value="" /></td>
<td><osxzs:taniselect name='<%="#tani"+i %>' defaultval="（単位）" /></td>
</tr>
<% } %>
</table>

</td></tr>
<tr><th class="layout" colspan="2">
備考
</th></tr>
<tr><td class="layout" colspan="2">
<osx:input type="text" name="#bikou" size="80" value="" /><br>
</td></tr>
<tr><td class="layout" colspan="2">
<input type="submit" name="submit" value="出荷予定入力"><br>
</td></tr>
</table>
</osx:form>
