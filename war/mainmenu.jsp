<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="com.oisix.oisystemzs.ejb.UserLocal" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%
// アクセス権限
UserLocal user = (UserLocal)session.getAttribute("USER");
boolean isGuest = false;
if (user.getPriv() == 3) isGuest = true;
int tdwidth = 33;
%>
<br>
<table width="90%" align="center" class="layout"><tr>
<th nowrap width="<%=tdwidth%>%" class="layout">
■発注
<hr noshade>
</th>
<th nowrap width="<%=tdwidth%>%" class="layout">
■入荷・入庫
<hr noshade>
</th>
<th nowrap width="<%=tdwidth%>%" class="layout">
■出荷・出庫
<hr noshade>
</th>
</tr><tr>
<td nowrap valign="top" class="layout" style="line-height:150%">
<% if (!isGuest) { %>
<osx:a href="hacchuu/zaikofusokusearch.ev">予定在庫不足照会</osx:a><br>
<osx:a href="inputclear.ev?inputclearnext=hacchuu/hacchuuinput.ev">発注入力</osx:a><br>
<% } %>
<osx:a href="hacchuu/yoteizaikosearch.ev">予定在庫照会</osx:a><br>
<osx:a href="inputclear.ev?inputclearnext=hacchuu/hacchuusearch.ev">発注照会</osx:a><br>
</td>
<td nowrap valign="top" class="layout" style="line-height:150%">
<% if (!isGuest) { %>
<osx:a href="nyuuka/yoteihyouselect.ev">入荷予定表</osx:a><br>
<osx:a href="nyuuka/yoteisearch.ev">入荷予定照会</osx:a><br>
<osx:a href="nyuuka/nyuukoinput.ev">入庫入力</osx:a><br>
<% } %>
<osx:a href="nyuuka/nyuukosearch.ev">入庫照会</osx:a><br>
</td>
<td nowrap valign="top" class="layout" style="line-height:150%">
<osx:a href="inputclear.ev?inputclearnext=shukka/shukkayoteiinput.ev">出荷予定入力</osx:a><br>
<osx:a href="shukka/shukkayoteisearch.ev">出荷予定照会</osx:a><br>
<% if (!isGuest) { %>
<osx:a href="shukka/shukkoinput.ev">出庫入力</osx:a><br>
<% } %>
<osx:a href="shukka/shukkosearch.ev">出庫照会</osx:a><br>
</td>
</tr>
<tr><td class="layout"><br></td></tr>
<tr>
<th nowrap width="<%=tdwidth%>%" class="layout">
■在庫
<hr noshade>
</th>
<th nowrap width="<%=tdwidth%>%" class="layout">
■決算
<hr noshade>
</th>
<th nowrap width="<%=tdwidth%>%" class="layout">
■マスターメンテ
<hr noshade>
</th>
</tr><tr>
<td nowrap valign="top" class="layout" style="line-height:150%">
<% if (!isGuest) { %>
<osx:a href="zaiko/zaikosearch.ev">在庫照会</osx:a><br>
<osx:a href="zaiko/tanaoroshilistselect.ev">棚卸リスト作成</osx:a><br>
<osx:a href="zaiko/shoumihenkousearch.ev">賞味期限変更</osx:a><br>
<% } %>
<osx:a href="zaiko/nyuushukkosearch.ev">入出庫照会</osx:a><br>
</td>
<td nowrap valign="top" class="layout" style="line-height:150%">
<osx:a href="kessan/tanaoroshiselect.ev">棚卸資産報告書作成</osx:a><br>
<osx:a href="kessan/kaikakekinsearch.ev">買掛金照会</osx:a><br>
</td>
<td nowrap valign="top" class="layout" style="line-height:150%">
<osx:a href="maint/shouhin.ev">商品マスター</osx:a><br>
<% if (!isGuest) { %>
<osx:a href="maint/shiiresaki.ev">仕入先マスター</osx:a><br>
<% } %>
</td>
</tr></table>
<br>
