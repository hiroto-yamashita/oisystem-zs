<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="com.oisix.oisystemfr.UrlUtil,com.oisix.oisystemzs.ejb.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<%
UserLocal user = (UserLocal)session.getAttribute("USER");
%>
<script language="JavaScript1.1">
function submitnouhin() {
  document.forms[1].action='<%=UrlUtil.encode(request, response, "hacchuu/nouhinhenkou.ev") %>';
  document.forms[1].submit();
}

function submitstatus() {
  document.forms[1].action='<%=UrlUtil.encode(request, response, "hacchuu/updatestatus.ev") %>';
  document.forms[1].submit();
}

function toggle() {
  for (i=0; i<document.forms[1].elements.length-3; i++) {
    if (document.forms[1].elements[i].checked) {
      document.forms[1].elements[i].checked = false;
    } else {
      document.forms[1].elements[i].checked = true;
    }
  }
}

</script>
<osx:form action="hacchuu/hacchuusearch.ev" method="post" option='name="ichiran"'>
<osx:input type="checkbox" name="#hacchuubi" value="on" />発注日
<osx:yearselect name="#year" from="2000" to="2020"/>年
<osx:monthselect name="#month"/>月
<osx:dateselect name="#date"/>日&nbsp;
仕入先コード<span style="cursor:hand" onClick="openSubSearch('../shared/shiiresakisearch.ev?form=ichiran&shiiresaki_id=shiiresaki_id')"><osx:img src="shared/image/icon_search01.gif" width="17" height="15" /></span> <osx:input type="text" name="#shiiresaki_id" size="6" value="" />&nbsp;
発注番号 <osx:input type="text" name="#hacchuu_bg" size="14" value="" />
<%-- IE5.0では、submitボタンまたはname="submit"のform要素があるとform.submit()がエラーになるため --%>
<input type="button" value="照会" onclick="javascript:document.forms[0].submit()">
</osx:form>
<p>

<osxzs:hacchuusearch size="50">
<osx:form action="hacchuu/hacchuusho.ev" method="post">
<osx:total/>件中<osx:startind />件目から<osx:endind />件目までを表示しています。<osx:prev action="hacchuu/hacchuusearch.ev">
前の50件
</osx:prev>
<osx:next action="hacchuu/hacchuusearch.ev">
次の50件
</osx:next>
<br>
<table width="100%">
<tr>
<th>発注書</th>
<th>発注番号</th>
<th>発注日</th>
<th>仕入先コード</th>
<th>仕入先</th>
<th>発注ステータス</th>
<th></th><th></th>
</tr>

<osx:items>
<%
HashMap item = (HashMap)request.getAttribute("ITEM");
String status = (String)item.get("status");
String statusid = "status_" + (String)item.get("hacchuu_bg");
%>
<tr>
<td><input type="checkbox" name='id' value='<osx:item field="hacchuu_id"/>'></td>
<td><osx:item field="hacchuu_bg" /></td>
<td><osx:item field="hacchuu_date" dateformat="yyyy/MM/dd" /></td>
<td><osx:item field="shiiresaki_id" /></td>
<td><osx:item field="shiiresaki_name" /></td>
<td><osxzs:hacchuustatusselect name='<%=statusid %>' defaultval='<%=status %>' /></td>
<td><osx:a href="hacchuu/hacchuushousai.ev?" itemkey="lineparam">詳細</osx:a></td>
<td>
<% if (user.getPriv() != 3) { %>
<osx:a href="hacchuu/hacchuusakujo.ev?" itemkey="lineparam">削除</osx:a>
<% } %>
</td>
</tr>
</osx:items>
</table>
<osx:prev action="hacchuu/hacchuusearch.ev">
前の50件
</osx:prev>
<osx:next action="hacchuu/hacchuusearch.ev">
次の50件
</osx:next>
<br>
<% if (user.getPriv() != 3) { %>
<input type="button" value="発注書作成" onclick="javascript:document.forms[1].submit()">
<input type="button" value="納品日一括変更" onclick="javascript:submitnouhin()">
<input type="button" value="発注ステータス更新" onclick="javascript:submitstatus()">
<% } %>
<input type="button" value="チェックボックス反転" onclick="javascript:toggle()">
</osx:form>
</osxzs:hacchuusearch>
