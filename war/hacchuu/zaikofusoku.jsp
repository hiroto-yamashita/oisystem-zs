<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="com.oisix.oisystemzs.ejb.UserLocal" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<script language="JavaScript1.1">
function changeNyuukasuu(id, irisuu) {
  hacchuusuuryoustr = document.main.elements["suuryou" + id].value;
  hacchuusuuryou = parseFloat(hacchuusuuryoustr);
  if (isNaN(hacchuusuuryou)) { return; }
  elementname = "document.all.n" + id;
  nyuukasuu = eval(elementname);
  nyuukasuu.innerText = hacchuusuuryou * irisuu;
}
</script>
<% UserLocal user = (UserLocal)session.getAttribute("USER"); %>
<%=request.getParameter("year") %>年<%=request.getParameter("month") %>月<%=request.getParameter("date") %>日 予定在庫不足<br>
<osxzs:yoteizaikofusoku>
<osx:form action="hacchuu/ikkatsuhacchuu.ev" method="post" option="name='main'" >
<table width="100%">
<tr>
<th>商品<br>コード</th>
<th>商品名</th>
<th>個別<br>発注F</th>
<th>要注意区分</th>
<th>規格</th>
<th>翌日<br>出荷数</th>
<th>出荷数</th>
<th>在庫数</th>
<th>発注点</th>
<th>不足数</th>
<th>発注数量</th>
<th>単位</th>
<th>入り数</th>
<th>入荷数量</th>
<th>出荷期限<br>日数</th>
<th>仕入先名</th>
</tr>

<osx:items>
<tr>
<td><osx:item field="shouhin_id" /></td>
<td><osx:item field="shouhin" /></td>
<td align="right"><osx:item field="kobetsuhacchuu_flg" /></td>
<td><osx:item field="youchuuikubun" /></td>
<td><osx:item field="kikaku" /></td>
<td align="right"><osx:item field="shukkayoteisuu1" /></td>
<td align="right"><osx:item field="shukkayoteisuu0" /></td>
<td align="right"><osx:item field="zaikosuu" /></td>
<td align="right"><osx:item field="hacchuuten" /></td>
<td align="right"><osx:item field="fusoku" /></td>
<td><input type="text" name='suuryou<osx:item field="shouhin_id" />' value='<osx:item field="hacchuusuuryou" />' size=6 onchange="changeNyuukasuu('<osx:item field="shouhin_id" />',<osx:item field="irisuu" />)" ></td>
<td><osx:item field="hacchuutani" /></td>
<td align="right"><osx:item field="irisuu" /></td>
<td align="right"><span id="n<osx:item field="shouhin_id" />"><osx:item field="nyuukasuu" /></span></td>
<td align="right"><osx:item field="shukkakigennissuu" /></td>
<td><osx:item field="shiiresaki" /></td>
</tr>
</osx:items>
</table>
<% if (user.getPriv() != 3) { %>
<input type="submit" value="発注">
<% } %>
</osx:form>
</osxzs:yoteizaikofusoku>
