<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="com.oisix.oisystemfr.*,com.oisix.oisystemzs.ejb.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<%
TransactionEvent event = (TransactionEvent)
  request.getAttribute(TransactionServlet.RESULTKEY);
if (event != null) {
  Iterator iter = event.getErrorlist().iterator();
%>
�G���[�ł��B�ȉ��̃G���[���b�Z�[�W�ɏ]�����͓��e���������Ă��������B
<p>
<%
  while (iter.hasNext()) {
%>
<%=iter.next() %><br>
<%
  }
}
// �����܂ŃG���[�������ꍇ
%>
<osx:form transaction="doshiiresakiinput.ev" result="maint/shiiresakiinputsuccess.ev" method="post" option="name='main'">
<table>
<tr>
<th>�d����R�[�h</th><td><osx:input type="text" name="#shiiresaki_id" size="6" value=""/></td>
</tr>
<tr>
<th>�d���於</th><td><osx:input type="text" name="#name" size="80" value=""/></td>
</tr>
<tr>
<th>�d����t���K�i</th><td><osx:input type="text" name="#furigana" size="80" value=""/></td>
</tr>
<tr>
<th>�d����t���K�i1</th><td><osx:input type="text" name="#furigana1" size="80" value=""/></td>
</tr>
<tr>
<th>�d����t���K�i2</th><td><osx:input type="text" name="#furigana2" size="80" value=""/></td>
</tr>
<tr>
<th>�X�֔ԍ�</th><td><osx:input type="text" name="#yuubin" size="8" value=""/></td>
</tr>
<tr>
<th>�Z��</th><td><osx:input type="text" name="#addr" size="80" value=""/></td>
</tr>
<tr>
<th>�d�b�ԍ�</th><td><osx:input type="text" name="#tel" size="15" value=""/></td>
</tr>
<tr>
<th>FAX�ԍ�</th><td><osx:input type="text" name="#fax" size="15" value=""/></td>
</tr>
<tr>
<th>�S���Ҏ���</th><td><osx:input type="text" name="#tantoushaname1" size="20" value=""/><osx:input type="text" name="#tantoushaname2" size="20" value=""/></td>
</tr>
</table>
<input type="submit" value="����">
</osx:form>
