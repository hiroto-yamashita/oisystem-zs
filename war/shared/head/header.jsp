<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="java.text.SimpleDateFormat,com.oisix.oisystemzs.ejb.*" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<!--this is default header-->
<%
// 日付表示用
SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
String strDate = sdf.format(new java.util.Date());
// 属性表示用
UserLocal user = (UserLocal)session.getAttribute("USER");
SoukoData souko = (SoukoData)session.getAttribute("SOUKO");
OfficeData office = (OfficeData)session.getAttribute("OFFICE");
// アクセス権限
boolean isGuest = false;
if (user.getPriv() == 3) isGuest = true;
%>
<table width="100%">
<tr>
<td class="layout" align="left" valign="top" rowspan="3" width="70%">
<osx:img src="shared/image/logo_zs.gif" width="150" height="26" />
</td>
<td class="layout" nowrap>
【<%=souko.getName()%>】
</td>
<td class="layout" nowrap>
【<%=office.getName()%>】
</td>
<td class="layout" nowrap>
【<%=user.getName1()%>】
</td>
<td class="layout" align="right" nowrap>
<%=strDate%>
</td>
</tr></table>
<script language="JavaScript">
<!--
var menuObj;

menuObj = createMenu('<osx:a href="mainmenu.ev" option="class=link-navigation">メインメニュー</osx:a>');
menuObj = createMenu("発注");
<% if (!isGuest) { %>
  menuObj.addSubMenu('<osx:a href="hacchuu/zaikofusokusearch.ev" option="class=link-navigation">予定在庫不足照会</osx:a>');
  menuObj.addSubMenu('<osx:a href="inputclear.ev?inputclearnext=hacchuu/hacchuuinput.ev" option="class=link-navigation">発注入力</osx:a>');
<% } %>
  menuObj.addSubMenu('<osx:a href="hacchuu/yoteizaikosearch.ev" option="class=link-navigation">予定在庫照会</osx:a>');
  menuObj.addSubMenu('<osx:a href="hacchuu/hacchuusearch.ev" option="class=link-navigation">発注照会</osx:a>');
menuObj = createMenu("入荷・入庫");
<% if (!isGuest) { %>
  menuObj.addSubMenu('<osx:a href="nyuuka/yoteihyouselect.ev" option="class=link-navigation">入荷予定表</osx:a>');
  menuObj.addSubMenu('<osx:a href="nyuuka/yoteisearch.ev" option="class=link-navigation">入荷予定照会</osx:a>');
  menuObj.addSubMenu('<osx:a href="nyuuka/nyuukoinput.ev" option="class=link-navigation">入庫入力</osx:a>');
<% } %>
  menuObj.addSubMenu('<osx:a href="nyuuka/nyuukosearch.ev" option="class=link-navigation">入庫照会</osx:a>');
menuObj = createMenu("出荷・出庫");
  menuObj.addSubMenu('<osx:a href="inputclear.ev?inputclearnext=shukka/shukkayoteiinput.ev" option="class=link-navigation">出荷予定入力</osx:a>');
  menuObj.addSubMenu('<osx:a href="shukka/shukkayoteisearch.ev" option="class=link-navigation">出荷予定照会</osx:a>');
<% if (!isGuest) { %>
  menuObj.addSubMenu('<osx:a href="shukka/shukkoinput.ev" option="class=link-navigation">出庫入力</osx:a>');
<% } %>
  menuObj.addSubMenu('<osx:a href="shukka/shukkosearch.ev" option="class=link-navigation">出庫照会</osx:a>');
menuObj = createMenu("在庫");
<% if (!isGuest) { %>
  menuObj.addSubMenu('<osx:a href="zaiko/zaikosearch.ev" option="class=link-navigation">在庫照会</osx:a>');
  menuObj.addSubMenu('<osx:a href="zaiko/tanaoroshilistselect.ev" option="class=link-navigation">棚卸リスト作成</osx:a>');
  menuObj.addSubMenu('<osx:a href="zaiko/shoumihenkousearch.ev" option="class=link-navigation">賞味期限変更</osx:a>');
<% } %>
  menuObj.addSubMenu('<osx:a href="zaiko/nyuushukkosearch.ev" option="class=link-navigation">入出庫照会</osx:a>');
menuObj = createMenu("決算");
  menuObj.addSubMenu('<osx:a href="kessan/tanaoroshiselect.ev" option="class=link-navigation">棚卸資産報告書作成</osx:a>');
  menuObj.addSubMenu('<osx:a href="kessan/kaikakekinsearch.ev" option="class=link-navigation">買掛金照会</osx:a>');
menuObj = createMenu("マスターメンテ");
  menuObj.addSubMenu('<osx:a href="maint/shouhin.ev" option="class=link-navigation">商品マスター</osx:a>');
<% if (!isGuest) { %>
  menuObj.addSubMenu('<osx:a href="maint/shiiresaki.ev" option="class=link-navigation">仕入先マスター</osx:a>');
<% } %>
//-->
</script>
<SCRIPT language="javascript">
drawMenu();
</SCRIPT>
