<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="java.util.*,java.text.*,com.oisix.oisystemfr.TransactionServlet,com.oisix.oisystemfr.TransactionEvent,com.oisix.oisystemzs.ejb.*,com.oisix.oisystemzs.objectmap.*" %>
<jsp:useBean id="event" scope="request" class="com.oisix.oisystemzs.eventhandler.NyuushukkoshoukaiEvent" />
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<%
SimpleDateFormat ymdj = new SimpleDateFormat("yyyy�NM��d��");
SimpleDateFormat mde = new SimpleDateFormat("M/d(EEE)");
DecimalFormat df = new DecimalFormat("##########.##");
LinkedList list = event.getNyuushukkolist();
Iterator dateiter;
HashMap suuryoumap;
int kubunhyouji = event.getKubunhyouji();
int nextkubunhyouji = 1 - event.getKubunhyouji();
String nextkubunhyoujistr = "�\��";
if (nextkubunhyouji == 0) nextkubunhyoujistr = "��\��";
%>
<%=ymdj.format(event.getNyuushukkodate())%>����<%=event.getNyuushukkospan()%>���Ԃ̓��o��
<osx:form action="zaiko/nyuushukkoshoukai.ev" method="post">
<input type="hidden" name="nyuushukkoyear" value="<%=event.getNyuushukkoyear()%>">
<input type="hidden" name="nyuushukkomonth" value="<%=event.getNyuushukkomonth()%>">
<input type="hidden" name="nyuushukkodate" value="<%=event.getNyuushukkoday()%>">
<input type="hidden" name="nyuushukkospan" value="<%=event.getNyuushukkospan()%>">
<%
Iterator shouhin_iditer = event.getShouhin_idlist().iterator();
int n = 0;
while (shouhin_iditer.hasNext()) {
%>
<input type="hidden" name="shouhin_id<%=n%>" value="<%=(String)shouhin_iditer.next()%>">
<%
n++;
}
%>
<input type="hidden" name="kubunhyouji" value="<%=nextkubunhyouji%>">
<input type="submit" value="�敪��<%=nextkubunhyoujistr%>">
</osx:form>
<table>
<%
Iterator listiter = list.iterator();
while (listiter.hasNext()) {
HashMap nyuushukko = (HashMap)listiter.next();
int shouhinrow = ((Integer)nyuushukko.get("SHOUHINROW")).intValue();
%>
<tr>
<th>���i�R�[�h</th>
<th>���i��</th>
<th>�K�i</th>
<th><br></th>
<% if (kubunhyouji == 1) { %><th>�敪</th><% } %>
<% dateiter = event.getDatelist().iterator(); %>
<% while (dateiter.hasNext()) { %>
<th><%=mde.format((java.util.Date)dateiter.next())%></th>
<% } %>
</tr>
<tr>
<td valign="top" rowspan="<%=shouhinrow%>"><%=(String)nyuushukko.get("SHOUHIN_ID")%></td>
<td valign="top" rowspan="<%=shouhinrow%>"><%=(String)nyuushukko.get("SHOUHINMEI")%></td>
<td valign="top" rowspan="<%=shouhinrow%>"><%=(String)nyuushukko.get("KIKAKU")%></td>
<%
HashMap nyuukoinfo = (HashMap)nyuushukko.get("NYUUKOINFO");
LinkedList nyuukokubunlist = (LinkedList)nyuukoinfo.get("NYUUKOKUBUNLIST");
int nyuukorow = nyuukokubunlist.size();
%>
<td valign="top" rowspan="<%=nyuukorow%>">����</td>
<%
HashMap nyuukosuuryou = (HashMap)nyuukoinfo.get("NYUUKOSUURYOU");
Iterator nyuukokubuniter = nyuukokubunlist.iterator();
int nyuukoindex = 0;
while (nyuukokubuniter.hasNext()) {
int nyuukokubun_id = ((Integer)nyuukokubuniter.next()).intValue();
if (nyuukoindex > 0) {
%>
</tr><tr>
<%
}
if (kubunhyouji == 1) {
String nyuukokubun = "";
if (nyuukokubun_id > 0) {
nyuukokubun = NyuukokubunMap.getNyuukokubun(nyuukokubun_id).getNyuukokubun();
}
%>
<td><%=nyuukokubun%></td>
<%
}
suuryoumap = (HashMap)nyuukosuuryou.get(new Integer(nyuukokubun_id));
dateiter = event.getDatelist().iterator();
while (dateiter.hasNext()) {
Float suuryou = (Float)suuryoumap.get((java.util.Date)dateiter.next());
String suuryoustr = "";
if (suuryou != null) suuryoustr = df.format(suuryou.floatValue());
%>
<td align="right"><%=suuryoustr%></td>
<%
}
nyuukoindex++;
}
%>
</tr>
<tr>
<%
HashMap shukkoinfo = (HashMap)nyuushukko.get("SHUKKOINFO");
LinkedList shukkokubunlist = (LinkedList)shukkoinfo.get("SHUKKOKUBUNLIST");
int shukkorow = shukkokubunlist.size();
%>
<td valign="top" rowspan="<%=shukkorow%>">�o��</td>
<%
HashMap shukkosuuryou = (HashMap)shukkoinfo.get("SHUKKOSUURYOU");
Iterator shukkokubuniter = shukkokubunlist.iterator();
int shukkoindex = 0;
while (shukkokubuniter.hasNext()) {
int shukkokubun_id = ((Integer)shukkokubuniter.next()).intValue();
if (shukkoindex > 0) {
%>
</tr><tr>
<%
}
if (kubunhyouji == 1) {
String shukkokubun = "";
if (shukkokubun_id > 0) {
shukkokubun = ShukkokubunMap.getShukkokubun(shukkokubun_id).getShukkokubun();
}
%>
<td><%=shukkokubun%></td>
<%
}
suuryoumap = (HashMap)shukkosuuryou.get(new Integer(shukkokubun_id));
dateiter = event.getDatelist().iterator();
while (dateiter.hasNext()) {
Float suuryou = (Float)suuryoumap.get((java.util.Date)dateiter.next());
String suuryoustr = "";
if (suuryou != null) suuryoustr = df.format(suuryou.floatValue());
%>
<td align="right"><%=suuryoustr%></td>
<%
}
shukkoindex++;
}
%>
</tr>
<tr>
<td valign="top">�݌�</td>
<% if (kubunhyouji == 1) { %>
<td><br></td>
<% } %>
<%
suuryoumap = (HashMap)nyuushukko.get("ZAIKOSUURYOU");
dateiter = event.getDatelist().iterator();
while (dateiter.hasNext()) {
Float suuryou = (Float)suuryoumap.get((java.util.Date)dateiter.next());
String suuryoustr = "";
if (suuryou != null) {
suuryoustr = df.format(suuryou.floatValue());
}
%>
<td align="right"><%=suuryoustr%></td>
<%
}
%>
</tr>
<% } %>
</table>
