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
<osxzs:shoumisearch>
<osx:form transaction="doshoumihenkou.ev" result="zaiko/shoumihenkousuccess.ev" method="post" option="name='main'" >
<table>
<tr>
<th>���i�R�[�h</th>
<th>���i��</th>
<th>�K�i</th>
<th>�ܖ�����</th>
<th>�o�׊���</th>
<th>����</th>
<th>�ܖ�����<br>����</th>
<th>�o�׊���<br>����</th>
</tr>
<%
String preshouhin = null;
boolean newShouhin = true;
int index = 0;
%>
<osx:items>
<%
index++;
HashMap item = (HashMap)request.getAttribute("ITEM");
String shouhin_id = (String)item.get("shouhin_id");
if (shouhin_id.equals(preshouhin)) {
    newShouhin = false;
} else {
    newShouhin = true;
    preshouhin = shouhin_id;
}
java.sql.Date shoumi = (java.sql.Date)item.get("shoumi");
request.setAttribute("shoumi", shoumi);
%>
<input type="hidden" name="#shouhin_id_<%=index %>" value="<%=shouhin_id %>">
<tr>
<td>
<% if (newShouhin) { %>
<osx:item field="shouhin_id" />
<% } else { %>
&nbsp;
<% } %>
</td>
<td>
<% if (newShouhin) { %>
<osx:item field="shouhin" />
<% } else { %>
&nbsp;
<% } %>
</td>
<td><osx:item field="kikaku" /></td>
<td>
<osx:yearselect name='<%="#year_"+shouhin_id+"_"+index %>' from="2000" to="2010" datekey="shoumi" />�N
<osx:monthselect name='<%="#month_"+shouhin_id+"_"+index %>' datekey="shoumi" />��
<osx:dateselect name='<%="#date_"+shouhin_id+"_"+index %>' datekey="shoumi" />��
<input type="hidden" name="#orgyear_<%=shouhin_id %>_<%=index %>" value="<osx:item field="shoumiyear" />">
<input type="hidden" name="#orgmonth_<%=shouhin_id %>_<%=index %>" value="<osx:item field="shoumimonth" />">
<input type="hidden" name="#orgdate_<%=shouhin_id %>_<%=index %>" value="<osx:item field="shoumidate" />">
</td>
<td><osx:item field="shukkakigen" /></td>
<td align=right>
<osx:item field="suuryou" />
<input type="hidden" name="#suuryou_<%=shouhin_id %>_<%=index %>" value="<osx:item field="suuryou" />">
</td>
<td align=right>
<osx:item field="shoumikigennissuu" />
<input type="hidden" name="#shoumi_<%=shouhin_id %>_<%=index %>" value="<osx:item field="shoumikigennissuu" />">
</td>
<td align=right>
<osx:item field="shukkakigennissuu" />
<input type="hidden" name="#shukka_<%=shouhin_id %>_<%=index %>" value="<osx:item field="shukkakigennissuu" />">
</td>
</tr>
</osx:items>
</table>
<input type="submit" value="�ύX">
</osx:form>
</osxzs:shoumisearch>
