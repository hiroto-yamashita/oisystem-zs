<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="com.oisix.oisystemfr.*,java.util.*,java.text.*" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<%
java.util.Date today = DateUtil.getDate();
Calendar cal = Calendar.getInstance();
cal.setTime(today);
SimpleDateFormat sdf1 = new SimpleDateFormat("M �� d �� (EEE)");
SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
%>
<osx:form action="hacchuu/yoteizaiko.ev" method="post" option="name='hacchuuform'" >
<input type="hidden" name="type" value="hacchuu">
�������p����<br>
<select name="date">
<%
for (int i=0; i<10; i++) {
  today = cal.getTime();
%>
<osx:option name="dates" value="<%=sdf2.format(today) %>" /><%=sdf1.format(today) %>  <%=i %>����
<%
  cal.add(Calendar.DATE, 1);
}
%>
</select>
����
<select name="dates">
<osx:option name="dates" value="10" defaultval="3" />10
<osx:option name="dates" value="9" defaultval="3" />9
<osx:option name="dates" value="8" defaultval="3" />8
<osx:option name="dates" value="7" defaultval="3" />7
<osx:option name="dates" value="6" defaultval="3" />6
<osx:option name="dates" value="5" defaultval="3" />5
<osx:option name="dates" value="4" defaultval="3" />4
<osx:option name="dates" value="3" defaultval="3" />3
<osx:option name="dates" value="2" defaultval="3" />2
<osx:option name="dates" value="1" defaultval="3" />1
</select>����<br>
�d����R�[�h <osx:input type="text" name="shiiresaki_id" size="6" value="" /><span style="cursor:hand" onClick="openSubSearch('../shared/shiiresakisearch.ev?form=hacchuuform&shiiresaki_id=shiiresaki_id')"><osx:img src="shared/image/icon_search01.gif" width="17" height="15" /></span><br>
<osx:input type="checkbox" name="shuubai_flg" value="1" />�I�����i����������<br>
<input type="submit" name="submit" value="�Ɖ�">
</osx:form>

<%
cal.setTime(DateUtil.getDate());
cal.add(Calendar.DATE, -6);
java.util.Date date = cal.getTime();
request.setAttribute("date", date);
%>
<osx:form action="hacchuu/yoteizaiko.ev" method="post" option="name='main'" >
<input type="hidden" name="type" value="date-code">
�����t�Ə��i�R�[�h�Ō��� <osx:a href="hacchuu/yoteizaikosearch.ev?num=50">���₵�Ă݂�</osx:a><br>
<osx:yearselect name="year" from="2000" to="2010" datekey="date" />�N
<osx:monthselect name="month" datekey="date" />��
<osx:dateselect name="date" datekey="date" />������
<select name="dates">
<osx:option name="dates" value="14" />14
<osx:option name="dates" value="13" />13
<osx:option name="dates" value="12" />12
<osx:option name="dates" value="11" />11
<osx:option name="dates" value="10" />10
<osx:option name="dates" value="9" />9
<osx:option name="dates" value="8" />8
<osx:option name="dates" value="7" />7
<osx:option name="dates" value="6" />6
<osx:option name="dates" value="5" />5
<osx:option name="dates" value="4" />4
<osx:option name="dates" value="3" />3
<osx:option name="dates" value="2" />2
<osx:option name="dates" value="1" />1
</select>����<br>
<table>
<tr>
<td class="layout">���i�R�[�h</td><td class="layout">���i��</td><td class="layout">�K�i</td>
</tr>
<%
int num = 10;
String numstr = request.getParameter("num");
if (numstr != null) {
  try {
    num = Integer.parseInt(numstr);
  } catch (NumberFormatException nfe) {
    num = 10;
  }
}
for (int i=0; i<num; i++) {
%>
<tr>
<td class="layout">
<osx:input type="text" name='<%="shouhin_id"+i %>' size="6" /><span style="cursor:hand" onClick="openSubSearch('../shared/shouhinsearch.ev?form=main&shouhin_id=shouhin_id<%=i%>&shouhin=shouhin<%=i%>&kikaku=kikaku<%=i%>')"><osx:img src="shared/image/icon_search01.gif" width="17" height="15" /></span>
</td>
<td class="layout"><span id="shouhin<%=i%>"></span></td>
<td class="layout"><span id="kikaku<%=i%>"></span></td>
</tr>
<% } %>
</table>
<input type="hidden" name="shuubai_flg" value="1">
<input type="submit" name="submit" value="�Ɖ�">
</osx:form>

<osx:form action="hacchuu/yoteizaiko.ev" method="post" option="name='shiireform'" >
<input type="hidden" name="type" value="shiiresaki">
���d����R�[�h�Ō���<br>
<osx:yearselect name="year" from="2000" to="2010" datekey="date" />�N
<osx:monthselect name="month" datekey="date" />��
<osx:dateselect name="date" datekey="date" />������
<select name="dates">
<osx:option name="dates" value="14" />14
<osx:option name="dates" value="13" />13
<osx:option name="dates" value="12" />12
<osx:option name="dates" value="11" />11
<osx:option name="dates" value="10" />10
<osx:option name="dates" value="9" />9
<osx:option name="dates" value="8" />8
<osx:option name="dates" value="7" />7
<osx:option name="dates" value="6" />6
<osx:option name="dates" value="5" />5
<osx:option name="dates" value="4" />4
<osx:option name="dates" value="3" />3
<osx:option name="dates" value="2" />2
<osx:option name="dates" value="1" />1
</select>���� 
�d����R�[�h <osx:input type="text" name="shiiresaki_id" size="6" value="" /><span style="cursor:hand" onClick="openSubSearch('../shared/shiiresakisearch.ev?form=shiireform&shiiresaki_id=shiiresaki_id')"><osx:img src="shared/image/icon_search01.gif" width="17" height="15" /></span><br>
<osx:input type="checkbox" name="shuubai_flg" value="1" />�I�����i����������<br>
<input type="submit" name="submit" value="�Ɖ�">
</osx:form>


<osx:form action="hacchuu/yoteizaiko.ev" method="post">
<input type="hidden" name="type" value="kobetsu">
���ʔ����Ώەi������<br>
<osx:yearselect name="year" from="2000" to="2010" datekey="date" />�N
<osx:monthselect name="month" datekey="date" />��
<osx:dateselect name="date" datekey="date" />������
<select name="dates">
<osx:option name="dates" value="14" />14
<osx:option name="dates" value="13" />13
<osx:option name="dates" value="12" />12
<osx:option name="dates" value="11" />11
<osx:option name="dates" value="10" />10
<osx:option name="dates" value="9" />9
<osx:option name="dates" value="8" />8
<osx:option name="dates" value="7" />7
<osx:option name="dates" value="6" />6
<osx:option name="dates" value="5" />5
<osx:option name="dates" value="4" />4
<osx:option name="dates" value="3" />3
<osx:option name="dates" value="2" />2
<osx:option name="dates" value="1" />1
</select>����<br>
<osx:input type="checkbox" name="shuubai_flg" value="1" />�I�����i����������<br>
<input type="submit" name="submit" value="�Ɖ�">
</osx:form>

<osx:form action="hacchuu/yoteizaiko.ev" method="post">
<input type="hidden" name="type" value="chuui">
���v���Ӌ敪�Ō���<br>
<osxzs:youchuuikubunselect name="youchuuikubun" defaultval="0"/><br>
<osx:yearselect name="year" from="2000" to="2010" datekey="date" />�N
<osx:monthselect name="month" datekey="date" />��
<osx:dateselect name="date" datekey="date" />������
<select name="dates">
<osx:option name="dates" value="14" />14
<osx:option name="dates" value="13" />13
<osx:option name="dates" value="12" />12
<osx:option name="dates" value="11" />11
<osx:option name="dates" value="10" />10
<osx:option name="dates" value="9" />9
<osx:option name="dates" value="8" />8
<osx:option name="dates" value="7" />7
<osx:option name="dates" value="6" />6
<osx:option name="dates" value="5" />5
<osx:option name="dates" value="4" />4
<osx:option name="dates" value="3" />3
<osx:option name="dates" value="2" />2
<osx:option name="dates" value="1" />1
</select>����<br>
<osx:input type="checkbox" name="shuubai_flg" value="1" />�I�����i����������<br>
<input type="submit" name="submit" value="�Ɖ�">
</osx:form>
