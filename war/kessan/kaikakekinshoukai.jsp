<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="java.util.HashMap,com.oisix.oisystemfr.UrlUtil,com.oisix.oisystemzs.ejb.*,java.text.*" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<jsp:useBean id="event" scope="request" class="com.oisix.oisystemzs.taglib.KaikakekinsearchTag" />
<osxzs:kaikakekinsearch size="300">
<%
String year = request.getParameter("year1");
String month = request.getParameter("month1");
HashMap goukei = (HashMap)request.getAttribute("GOUKEI");
%>
<%=year%>�N<%=month%>���x&nbsp;<%=goukei.get("shiiresaki")%><br>
���v<%=goukei.get("goukei")%>�~<br>

<osx:total/>����<osx:startind />���ڂ���<osx:endind />���ڂ܂ł�\�����Ă��܂��B<br>
<osx:prev action="kessan/kaikakekinshoukai.ev">
�O��300��
</osx:prev>
<osx:next action="kessan/kaikakekinshoukai.ev">
����300��
</osx:next>
<table width="100%">
<tr>
<th>���t</th>
<th>�[�i��</th>
<th>���i�R�[�h</th>
<th>���i��</th>
<th>�K�i</th>
<th>���ɐ���</th>
<th>���ɒP��</th>
<th>�d������</th>
<th>�d���P��</th>
<th>�d���P��</th>
<th>���z</th>
<th></th>
</tr>
<osx:items>
<tr>
<td><osx:item field="nyuuko_date" dateformat="yyyy/MM/dd" /></td>
<td><osx:item field="nouhinsaki" /></td>
<td><osx:item field="shouhin_id" /></td>
<td><osx:item field="shouhin" /></td>
<td><osx:item field="kikaku" /></td>
<td align="right"><osx:item field="nyuukasuuryou" /></td>
<td align="right"><osx:item field="nyuukotanka" /></td>
<td align="right"><osx:item field="hacchuusuuryou" /></td>
<td><osx:item field="hacchuutani" /></td>
<td align="right"><osx:item field="hacchuutanka" /></td>
<td align="right"><osx:item field="hacchuukingaku" /></td>
<td>
<%
HashMap item = (HashMap)request.getAttribute("ITEM");
String nyuuko_id = (String)item.get("nyuuko_id");
if ((nyuuko_id != null) && (!nyuuko_id.equals("0"))) {
%>
<osx:a href="nyuuka/nyuukotankateisei.ev?" itemkey="param">
�P������
</osx:a>
<% } %>
</td>
</tr>
</osx:items>
</table>
</osxzs:kaikakekinsearch>
