<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="com.oisix.oisystemfr.UrlUtil,com.oisix.oisystemzs.ejb.*" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<osxzs:nyuukayoteisearch size="20">
<osx:form action="nyuuka/labelsakusei.ev" method="post">
<osx:total/>件中<osx:startind />件目から<osx:endind />件目までを表示しています。<br>
<osx:prev action="nyuuka/nyuukayoteishoukai.ev">前の20件</osx:prev>
<osx:next action="nyuuka/nyuukayoteishoukai.ev">次の20件</osx:next>
<table width="100%">
<tr>
<th></th>
<th>発注番号</th>
<th>入荷予定日</th>
<th>入荷区分</th>
<th>仕入先</th>
<th>商品コード</th>
<th>商品名</th>
<th>数量</th>
<th>単位</th>
<th></th>
</tr>
<script language="JavaScript1.2">
function submitnyuukokakutei() {
  document.forms[0].action='<%=UrlUtil.encode(request, response, "nyuuka/nyuukokakuteiinput.ev") %>';
  document.forms[0].submit();
}
function toggle() {
  for (i=0; i<document.forms[0].elements.length-3; i++) {
    if (document.forms[0].elements[i].checked) {
      document.forms[0].elements[i].checked = false;
    } else {
      document.forms[0].elements[i].checked = true;
    }
  }
}
</script>
<osx:items>
<tr>
<td><input type="checkbox" name='nyuukayotei_id' value='<osx:item field="nyuukayotei_id"/>'></td>
<td><osx:item field="hacchuu_bg" /></td>
<td><osx:item field="nyuukayotei_date" dateformat="yyyy/MM/dd" /></td>
<td><osx:item field="nyuukokubun" /></td>
<td><osx:item field="name" /></td>
<td><osx:item field="shouhin_id" /></td>
<td><osx:item field="shouhin" /></td>
<td align="right"><osx:item field="nyuukasuuryou" /></td>
<td><osx:item field="nyuukatani" /></td>
<td><osx:a href="nyuuka/nyuukayoteishousai.ev?" itemkey="lineparam">詳細</osx:a></td>
</tr>
</osx:items>
</table>
<%
UserLocal user = (UserLocal)session.getAttribute("USER");
if (user.getPriv() != 3) { %>
<input type="button" value="ラベル作成" onclick="javascript:document.forms[0].submit()">
<input type="button" value="入庫確定" onclick="javascript:submitnyuukokakutei()">
<input type="button" value="チェックボックス反転" onclick="javascript:toggle()">
<% } %>
</osx:form>
</osxzs:nyuukayoteisearch>