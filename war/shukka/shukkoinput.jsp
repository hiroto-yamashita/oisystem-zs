<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="java.util.*,com.oisix.oisystemfr.TransactionServlet,com.oisix.oisystemfr.TransactionEvent,com.oisix.oisystemzs.ejb.*,com.oisix.oisystemzs.eventhandler.DoshukkoinputEvent" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<script language="JavaScript1.1">
function changeKingaku(ind) {
  suuryoustring = document.main.elements["#suuryou" + ind].value;
  suuryou = parseFloat(suuryoustring);
  if (isNaN(suuryou)) { return; }
  tankastring = document.main.elements["#tanka" + ind].value;
  tanka = parseFloat(tankastring);
  if (isNaN(tanka)) { return; }
  document.main.elements["#kingaku" + ind].value = suuryou * tanka;
}
</script>
<%
TransactionEvent eventerr = (TransactionEvent)
  request.getAttribute(TransactionServlet.RESULTKEY);
if (eventerr != null) {
  LinkedList errlist = eventerr.getErrorlist();
  if (errlist.size() == 0) {
	DoshukkoinputEvent checkevent = (DoshukkoinputEvent)
	  request.getAttribute(TransactionServlet.RESULTKEY);
    Iterator iter = checkevent.getChecklist().iterator();
%>
入力項目をチェックしてください。<br>
<%
    while (iter.hasNext()) {
%>
・<%=iter.next() %><br>
<%
    }
%>
よろしければもう一度送信ボタンを押してください。
<p>
<%
  } else {
    Iterator iter = eventerr.getErrorlist().iterator();
%>
エラーが発生しました。
<p>
<%
    while (iter.hasNext()) {
%>
・<%=iter.next() %><br>
<%
    }
  }
}
// ここまでエラーだった場合
%>
<osx:form transaction="doshukkoinput.ev" result="shukka/shukkoinputsuccess.ev" method="post" option="name='main'">
出庫日&nbsp;
<osx:yearselect name="#shukko_year" from="2000" to="2020" />年
<osx:monthselect name="#shukko_month" />月
<osx:dateselect name="#shukko_date" />日
<table>
<tr><th>
出荷予定番号
</th><th>
商品コード
</th><th>
商品名
</th><th>
規格
</th><th>
賞味期限
</th><th>
数量
</th><th>
単位
</th><th>
単価
</th><th>
金額
</th><th>
出庫区分
</th></tr>
<% for (int i = 0; i < 10; i++) { %>
<% String onchange = "onchange='changeKingaku(" + i + ")'"; %>
<% String shouhinmei = (String)session.getAttribute("shouhinmei" + i); %>
<% if (shouhinmei == null) { shouhinmei = ""; } %>
<% String kikaku = (String)session.getAttribute("kikaku" + i); %>
<% if (kikaku == null) { kikaku = ""; } %>
<tr><td>
<osx:input type="text" name='<%="#shukkayotei_bg"+i %>' size="14" value="" />
</td><td>
<osx:input type="text" name='<%="#shouhin_id"+i %>' size="6" value="" /><span style="cursor:hand" onClick="openSubSearch('../shared/shouhinsearch.ev?form=main&shouhin_id=shouhin_id<%=i%>&shouhin=shouhin<%=i%>&kikaku=kikaku<%=i%>&tanimei=tani<%=i%>')"><osx:img src="shared/image/icon_search01.gif" width="17" height="15" /></span>
</td><td>
<span id="shouhin<%=i%>"><%=shouhinmei%></span>
</td><td>
<span id="kikaku<%=i%>"><%=kikaku%></span>
</td><td>
<osx:input type="text" name='<%="#shoumikigen"+i %>' size="12" value="" />
</td><td>
<osx:input type="text" name='<%="#suuryou"+i %>' size="6" value="" option="<%=onchange %>" />
</td><td>
<osxzs:tanimeiselect name='<%="#tani"+i %>' defaultval="（単位）" /></td><td>
<osx:input type="text" name='<%="#tanka"+i %>' size="6" value="" option="<%=onchange %>" />
</td><td>
<osx:input type="text" name='<%="#kingaku"+i %>' size="6" value="" />
</td><td>
<osxzs:shukkokubunselect name='<%="#shukkokubun"+i %>' defaultval="（区分）" />
</td></tr>
<% } %>
</table>
<input type="submit" name="submit" value="出庫確定データ入力"><br>
</osx:form>
