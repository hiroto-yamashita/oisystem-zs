<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="com.oisix.oisystemzs.ejb.*,java.util.*,java.text.*,com.oisix.oisystemzs.objectmap.*,com.oisix.oisystemzs.Names" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<%
UserLocal user = (UserLocal)session.getAttribute("USER");
%>
<osx:form action="shukka/shukkayoteisearch.ev" method="post">
<input type="hidden" name="searchoption" value="shukkayotei_bg">
■ 出荷予定番号で検索<br>
&nbsp;出荷予定番号 <osx:input type="text" name="#shukkayotei_bg" size="14" value="" />
<input type="submit" name="submit" value="検索">
</osx:form>
<p>
<osx:form action="shukka/shukkayoteisearch.ev" method="post">
<input type="hidden" name="searchoption" value="any_conditions">
■ 条件で検索<br>
&nbsp;<osx:input type="checkbox" name="#mishukko_flg" value="on" defaultval="on" />未出荷のもののみ表示<br>
&nbsp;出荷予定日
<osx:yearselect name="#fromyear" from="2000" to="2020"/>年
<osx:monthselect name="#frommonth"/>月
<osx:dateselect name="#fromdate"/>日～
<osx:yearselect name="#toyear" from="2000" to="2020"/>年
<osx:monthselect name="#tomonth"/>月
<osx:dateselect name="#todate"/>日<br>
出荷予定種別 <osxzs:shukkayoteishubetsuselect name="#shukkayoteishubetsu" defaultval="（種別）" />&nbsp;
<input type="submit" name="submit" value="検索">
</osx:form>
<p>

<osxzs:shukkayoteisearch size="20">
<osx:prev action="shukka/shukkayoteisearch.ev">
前の20件
</osx:prev>
<osx:next action="shukka/shukkayoteisearch.ev">
次の20件
</osx:next>
<osx:total/>件中<osx:startind />件目から<osx:endind />件目までを表示しています。<br>
<table width="100%">
<tr>
<th>出荷予定番号</th>
<th>出荷予定日</th>
<th>出荷元</th>
<th>納品先</th>
<th>出庫区分</th>
<th>出荷状況</th>
<th></th>
<th></th>
<th></th>
<th></th>
</tr>

<osx:items>
<tr>
<td><osx:item field="shukkayotei_bg" /></td>
<td><osx:item field="shukkayotei_date" dateformat="yyyy/MM/dd" /></td>
<td><osx:item field="shukkamoto" /></td>
<td><osx:item field="nouhinsaki" /></td>
<td><osx:item field="shukkokubun" /></td>
<td><osx:item field="shukkajoukyou" /></td>
<td><osx:a href="inputclear.ev?inputclearnext=shukka/shukkayoteishousai.ev&" itemkey="lineparam">詳細</osx:a></td>
<td>
<% if (user.getPriv() != 3) { %>
<osx:a href="shukka/shukkokakuteiinput.ev?" itemkey="lineparam">出庫確定</osx:a>
<% } %>
</td>
<td><osx:a href="shukka/makepickinglist.ev?" itemkey="lineparam">Pリスト</osx:a></td>
<td><osx:a href="shukka/makenouhinsho.ev?" itemkey="lineparam">納品書</osx:a></td>
</tr>
</osx:items>
</table>
</osxzs:shukkayoteisearch>
