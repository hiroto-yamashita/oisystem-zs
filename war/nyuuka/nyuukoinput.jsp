<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="java.util.*,com.oisix.oisystemfr.TransactionServlet,com.oisix.oisystemfr.TransactionEvent,com.oisix.oisystemzs.ejb.*" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<script language="JavaScript1.1">
function changeKingaku(ind) {
  suuryoustring = document.main.elements["#shiiresuuryou" + ind].value;
  suuryou = parseFloat(suuryoustring);
  if (isNaN(suuryou)) { return; }
  tankastring = document.main.elements["#shiiretanka" + ind].value;
  tanka = parseFloat(tankastring);
  if (isNaN(tanka)) { return; }
  elementname = "document.all.kingaku" + ind;
  kingaku = eval(elementname);
  kingaku.innerText = suuryou * tanka;
}
</script>
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
<osx:form transaction="donyuukoinput.ev" result="nyuuka/nyuukoinputsuccess.ev" method="post" option="name='main'">
入庫日&nbsp;
<osx:yearselect name="#nyuukoyear" from="2000" to="2010"/>年
<osx:monthselect name="#nyuukomonth"/>月
<osx:dateselect name="#nyuukodate"/>日&nbsp;	

<table>
<tr>
<th>仕入先コード</th>
<th>商品コード</th>
<th>商品名</th>
<th>規格</th>
<th>入庫数量</th>
<th>入庫単位</th>
<th>仕入数量</th>
<th>仕入単位</th>
<th>単価</th>
<th>金額</th>
</tr>
<tr>
<th></th>
<th colspan>発注番号</th>
<th colspan=2>賞味期限</th>
<th colspan=3>出荷期限</th>
<th colspan=3>入庫区分</th>
</tr>
<% for (int i=0; i<5; i++){ %>
<% String onchange = "onchange='changeKingaku(" + i + ")'"; %>
<tr>
<td>
<osx:input type="text" name='<%="#shiiresaki_id"+i %>' size="6" value="" />
<span style="cursor:hand" onClick="openSubSearch('../shared/shiiresakisearch.ev?form=main&shiiresaki_id=shiiresaki_id<%=i%>&shiiresakimei=shiiresakimei<%=i%>')"><osx:img src="shared/image/icon_search01.gif" width="17" height="15" /></span><span id="shiiresaki_id<%=i%>"></span>
</td>
<td>
<osx:input type="text" name='<%="#shouhin_id"+i %>' size="6" value="" /><span style="cursor:hand" onClick="openSubSearch('../shared/shouhinsearch.ev?form=main&shouhin_id=shouhin_id<%=i%>&shouhin=shouhin<%=i%>&kikaku=kikaku<%=i%>&tanimei=nyuukotani<%=i%>&hacchuutanimei=shiiretani<%=i%>&tanka=shiiretanka<%=i%>&shoumi_y=shoumiyear<%=i%>&shoumi_m=shoumimonth<%=i%>&shoumi_d=shoumidate<%=i%>&shukka_y=shukkayear<%=i%>&shukka_m=shukkamonth<%=i%>&shukka_d=shukkadate<%=i%>')"><osx:img src="shared/image/icon_search01.gif" width="17" height="15" /></span>
</td>
<td>
<span id="shouhin<%=i%>"></span>
</td>
<td>
<span id="kikaku<%=i%>"></span>
</td>
<td>
<osx:input type="text" name='<%="#nyuukosuuryou"+i %>' size="6" value="" />
</td>
<td>
<osxzs:tanimeiselect name='<%="#nyuukotani"+i %>' defaultval="（単位）" />
</td>
<td>
<osx:input type="text" name='<%="#shiiresuuryou"+i %>' size="6" value="" option="<%=onchange %>" />
<span id="shiiretanka<%=i%>"></span>
</td>
<td>
<osxzs:tanimeiselect name='<%="#shiiretani"+i %>' defaultval="（単位）" />
</td>
<td>
<osx:input type="text" name='<%="#shiiretanka"+i %>' size="6" value="" option="<%=onchange %>" />
</td>
<td>
<span id="kingaku<%=i%>"></span>
</td>
</tr>

<tr>
<td></td>
<td>
<osx:input type="text" name='<%="#hacchuu_bg"+i %>' size="14" value="" />
</td>
<td colspan=2>
<osx:yearselect name='<%="#shoumiyear"+i %>' from="2000" to="2010"/>年
<osx:monthselect name='<%="#shoumimonth"+i %>'/>月
<osx:dateselect name='<%="#shoumidate"+i %>'/>日
</td>
<td colspan=3>
<osx:yearselect name='<%="#shukkayear"+i %>' from="2000" to="2010"/>年
<osx:monthselect name='<%="#shukkamonth"+i %>' />月
<osx:dateselect name='<%="#shukkadate"+i %>' />日
</td>
<td colspan=3>
<osxzs:nyuukokubunselect name='<%="#nyuukokubun"+i %>' defaultval=" (区分) " />
</td>
</tr>
<% } %>
</table>
<input type="submit" value="確定">
</osx:form>