<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="java.util.*,com.oisix.oisystemfr.TransactionServlet,com.oisix.oisystemfr.TransactionEvent,com.oisix.oisystemzs.ejb.*" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<jsp:useBean id="event" scope="request" class="com.oisix.oisystemzs.eventhandler.ShukkayoteishousaiEvent" />
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
ShukkayoteiData shukkayotei = event.getShukkayotei();
Collection shukkayoteimeisai = event.getShukkayoteimeisai();
%>
<osx:form transaction="doshukkayoteikoushin.ev" result="shukka/shukkayoteikoushinsuccess.ev" method="post" option="name='main'">
<input type="hidden" name="#shukkayotei_id" value="<%=shukkayotei.getShukkayotei_id()%>">
<table>
<tr><th class="layout">
出庫区分
</th><td class="layout">
<osxzs:shukkokubunselect name="#shukkokubun" defaultval="（区分）" selected="<%=shukkayotei.getShukkokubun()%>" />
</td></tr>
<tr><th class="layout">
出荷予定種別
</th><td class="layout">
<osxzs:shukkayoteishubetsuselect name="#shukkayoteishubetsu" defaultval="（種別）" selected="<%=shukkayotei.getShukkayoteishubetsu()%>" />
</td></tr>
<tr><th class="layout">
出荷予定日
</th><td class="layout">
<osx:yearselect name="#shukka_year" from="2000" to="2020" datekey="shukkayotei" />年
<osx:monthselect name="#shukka_month" datekey="shukkayotei" />月
<osx:dateselect name="#shukka_date" datekey="shukkayotei" />日
</td></tr>
<tr><th class="layout">
納品予定日
</th><td class="layout">
<osx:yearselect name="#nouhin_year" from="2000" to="2020" datekey="nouhinyotei" />年
<osx:monthselect name="#nouhin_month" datekey="nouhinyotei" />月
<osx:dateselect name="#nouhin_date" datekey="nouhinyotei" />日
</td></tr>
<tr><th class="layout">
出荷元コード
</th><td class="layout">
<osx:input type="text" name="#shukkamoto_id" size="6" value="<%=shukkayotei.getShukkamoto_id()%>" />
</td></tr>
<tr><th class="layout">
納品先コード
</th><td class="layout">
<osx:input type="text" name="#nouhinsaki_id" size="6" value="<%=shukkayotei.getNouhinsaki_id()%>" />
</td></tr>
<tr><th class="layout">
納品先名
</th><td class="layout">
<osx:input type="text" name="#nouhinsakimei" size="30" value="<%=shukkayotei.getNouhinsakiname()%>" />
</td></tr>
<tr><th class="layout">
納品先郵便番号
</th><td class="layout">
<osx:input type="text" name="#nohinsakiyuubin" size="8" value="<%=shukkayotei.getNouhinsakizip()%>" />
</td></tr>
<tr><th class="layout">
納品先住所
</th><td class="layout">
<osx:input type="text" name="#nouhinsakijuusho" size="80" value="<%=shukkayotei.getNouhinsakiaddr()%>" />
</td></tr>
<tr><th class="layout">
納品先電話番号
</th><td class="layout">
<osx:input type="text" name="#nouhinsakitel" size="20" value="<%=shukkayotei.getNouhinsakitel()%>" />
</td></tr>
<tr><th class="layout">
納品先FAX番号
</th><td class="layout">
<osx:input type="text" name="#nouhinsakifax" size="20" value="<%=shukkayotei.getNouhinsakifax()%>" />
</td></tr>
<tr><td class="layout" colspan="2">

<table width="100%">
<tr>
<th>商品<br>コード<br>(検索用)</th>
<th>商品<br>コード<br>(更新用)</th>
<th>商品名</th>
<th>規格</th>
<th>数量</th>
<th>単位</th>
<th>出荷状況</th>
</tr>
<%
Iterator meisaiIter = shukkayoteimeisai.iterator();
int meisaisuu = 0;
while (meisaiIter.hasNext()) {
HashMap meisaiMap = (HashMap)meisaiIter.next();
ShukkayoteimeisaiData meisai = (ShukkayoteimeisaiData)meisaiMap.get("SHUKKAYOTEIMEISAI");
ShouhinLocal shouhin = (ShouhinLocal)meisaiMap.get("SHOUHIN");
String strSuuryou = String.valueOf(meisai.getShukkayoteisuuryou());
int id = meisai.getShukkayoteimeisai_id();
%>
<tr>
<input type="hidden" name="#shukkayoteimeisai_id<%=meisaisuu%>" value="<%=id%>">
<td><%=meisai.getShouhin_id()%></td>
<td><osx:input type="text" name='<%="#shouhin_id"+meisaisuu %>' size="6" value="<%=meisai.getShouhin_id()%>" /></td>
<td><%=shouhin.getShouhin()%></td>
<td><%=shouhin.getKikaku()%></td>
<td><osx:input type="text" name='<%="#suuryou"+meisaisuu %>' size="6" value="<%=strSuuryou%>" /></td>
<td><osxzs:tanimeiselect name='<%="#tani"+meisaisuu %>' defaultval="（単位）" selected="<%=meisai.getTani()%>" /></td>
<td><osxzs:shukkajoukyouselect name='<%="#shukkajoukyou"+meisaisuu %>' defaultval="（状況）" selected="<%=meisai.getShukkajoukyou()%>" /></td>
</tr>
<%
meisaisuu++;
}
int newmeisaisuu = 0;
if (meisaisuu >= 30) {
%>
<tr>
<td></td>
<td><osx:input type="text" name='#shouhin_idnew0' size="6" value="" /><span style="cursor:hand" onClick="openSubSearch('../shared/shouhinsearch.ev?form=main&shouhin_id=shouhin_idnew0&shouhin=shouhinnew0&kikaku=kikakunew0&tanimei=taninew0')"><osx:img src="shared/image/icon_search01.gif" width="17" height="15" /></span></td>
<td><span id="shouhinnew0"></span></td>
<td><span id="kikakunew0"></span></td>
<td><osx:input type="text" name='#suuryounew0' size="6" value="" /></td>
<td><osxzs:tanimeiselect name='#taninew0' defaultval="（単位）" /></td>
<td><osxzs:shukkajoukyouselect name='#shukkajoukyounew0' defaultval="（状況）" /></td>
</tr>
<%
newmeisaisuu++;
} else {
for (int i = meisaisuu; i < 30; i++) {
%>
<tr>
<td></td>
<td><osx:input type="text" name='<%="#shouhin_idnew"+newmeisaisuu %>' size="6" value="" /><span style="cursor:hand" onClick="openSubSearch('../shared/shouhinsearch.ev?form=main&shouhin_id=shouhin_idnew<%=newmeisaisuu%>&shouhin=shouhinnew<%=newmeisaisuu%>&kikaku=kikakunew<%=newmeisaisuu%>&tanimei=taninew<%=newmeisaisuu%>')"><osx:img src="shared/image/icon_search01.gif" width="17" height="15" /></span></td>
<td><span id="shouhinnew<%=newmeisaisuu%>"></span></td>
<td><span id="kikakunew<%=newmeisaisuu%>"></span></td>
<td><osx:input type="text" name='<%="#suuryounew"+newmeisaisuu %>' size="6" value="" /></td>
<td><osxzs:tanimeiselect name='<%="#taninew"+newmeisaisuu %>' defaultval="（単位）" /></td>
<td><osxzs:shukkajoukyouselect name='<%="#shukkajoukyounew"+newmeisaisuu %>' defaultval="（状況）" /></td>
</tr>
<%
newmeisaisuu++;
}
}
%>
</table>
<input type="hidden" name="#meisaisuu" value="<%=meisaisuu%>">
<input type="hidden" name="#newmeisaisuu" value="<%=newmeisaisuu%>">
</td></tr>
<tr><th class="layout" colspan="2">
備考
</th></tr>
<tr><td class="layout" colspan="2">
<osx:input type="text" name="#bikou" size="80" value="<%=shukkayotei.getBikou()%>" /><br>
</td></tr>
<tr><td class="layout" colspan="2">
<input type="submit" name="submit" value="出荷予定入力"><br>
</td></tr>
</table>
</osx:form>
