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
<%=request.getParameter("year") %>�N<%=request.getParameter("month") %>��<%=request.getParameter("date") %>�� �\��݌ɕs��<br>
<osxzs:yoteizaikofusoku>
<osx:form action="hacchuu/ikkatsuhacchuu.ev" method="post" option="name='main'" >
<table width="100%">
<tr>
<th>���i<br>�R�[�h</th>
<th>���i��</th>
<th>��<br>����F</th>
<th>�v���Ӌ敪</th>
<th>�K�i</th>
<th>����<br>�o�א�</th>
<th>�o�א�</th>
<th>�݌ɐ�</th>
<th>�����_</th>
<th>�s����</th>
<th>��������</th>
<th>�P��</th>
<th>���萔</th>
<th>���א���</th>
<th>�o�׊���<br>����</th>
<th>�d���於</th>
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
<input type="submit" value="����">
<% } %>
</osx:form>
</osxzs:yoteizaikofusoku>
