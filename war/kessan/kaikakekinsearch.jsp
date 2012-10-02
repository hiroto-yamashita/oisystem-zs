<%@ page contentType="text/html; charset=SJIS" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<%@ page import="java.util.*,com.oisix.oisystemfr.*,com.oisix.oisystemzs.ejb.*" %>

<osx:form action="kessan/kaikakekinshoukai.ev" method="post" option="name='main'">
入庫日
<%
Calendar cal = DateUtil.getCalendar();
cal.add(Calendar.MONTH,-1);
cal.set(Calendar.DATE,1);
java.util.Date date1 = cal.getTime();
cal.add(Calendar.MONTH, 1);
cal.add(Calendar.DATE, -1);
java.util.Date date2 = cal.getTime();
request.setAttribute("date1" , date1);
request.setAttribute("date2" , date2);
%>
&nbsp;&nbsp;&nbsp;
<osx:yearselect name="year1" from="2000" to="2010" datekey="date1"/>年
<osx:monthselect name="month1" datekey="date1"/>月
<osx:dateselect name="date1" datekey="date1"/>日
～
<osx:yearselect name="year2" from="2000" to="2010" datekey="date2"/>年
<osx:monthselect name="month2" datekey="date2"/>月
<osx:dateselect name="date2" datekey="date2"/>日
<br>
仕入先コード
<osx:input type="text" name="shiiresaki_id" value="" size="6"/>
<span style="cursor:hand" onClick="openSubSearch('../shared/shiiresakisearch.ev?form=main&shiiresaki_id=shiiresaki_id&shiiresakimei=shiiresakimei')"><osx:img src="shared/image/icon_search01.gif" width="17" height="15" /></span><span id="shiiresakimei"></span>
<span id="name"><br>
<input type="submit" value="作成">
</osx:form>