<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="com.oisix.oisystemzs.ejb.*,java.util.*,com.oisix.oisystemzs.eventhandler.*,com.oisix.oisystemzs.objectmap.*,java.text.*" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<jsp:useBean id="event" scope="request" class="com.oisix.oisystemzs.eventhandler.YoteizaikoEvent" />
<%
DecimalFormat df = new DecimalFormat("##########.##");
boolean kubunflg = event.getKubun();
TreeMap yoteimap = event.getYoteimap();
Set ids = yoteimap.keySet();
Iterator iter = ids.iterator();
java.util.Date inputdate = event.getInputdate();
int nissuu = event.getDates();
Calendar cal = Calendar.getInstance();
cal.setTime(inputdate);
cal.add(Calendar.DATE, nissuu - 1);
java.util.Date shousaidate = cal.getTime();
SimpleDateFormat sdf1 = new SimpleDateFormat("M/d(EEE)");
SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
TreeMap datesmap = event.getDatesmapbase();
Set dates = datesmap.keySet();
Iterator diter = dates.iterator();
int datesnum = 0;
while (diter.hasNext()) {
  java.util.Date date = (java.util.Date)diter.next();
  if (!date.before(inputdate)) {
    datesnum++;
  }
}
String type = event.getType();
String year = null;
String month = null;
String datestr = null;
if (event.isHacchuu()) {
  String selectdate = request.getParameter("date");
  year = selectdate.substring(0, 4);
  month = selectdate.substring(4, 6);
  datestr = selectdate.substring(6, 8);
}
%>
<script language="JavaScript1.1">
function changeHacchuu(id, irisuu) {
  hacchuusuuryoustr = document.main.elements["suuryou" + id].value;
  hacchuusuuryou = parseFloat(hacchuusuuryoustr);
  if (isNaN(hacchuusuuryou)) { return; }
  for (i=0; i<<%=datesnum-3 %>; i++) {
    suuryouelementname = "document.all.hiddenz" + id + "_" + i;
    orgzaiko = eval(suuryouelementname);
    orgzaikosuu = parseFloat(orgzaiko.value);
    elementname = "document.all.z" + id + "_" + i;
    zaikosuu = eval(elementname);
    newzaiko = orgzaikosuu + hacchuusuuryou * irisuu;
    newzaiko = Math.round(newzaiko * 10) / 10;
    zaikosuu.innerText = newzaiko;
  }
}
</script>
<%
UserLocal user = (UserLocal)session.getAttribute("USER");
SoukoData souko = (SoukoData)session.getAttribute("SOUKO");
int result = event.getResult();
if (result == 1) { %>
<%=event.getErrormsg() %>
<% return; } %> 

<% if ((type != null) && (type.equals("shiiresaki") || event.isHacchuu())) { %>
仕入先：<%=event.getShiiresaki() %><br>
<% } %>

<osx:form action="hacchuu/ikkatsuhacchuu.ev" method="post" option="name='main'" >
<input type="hidden" name="year" value="<%=year %>">
<input type="hidden" name="month" value="<%=month %>">
<input type="hidden" name="date" value="<%=datestr %>">
<table width="100%">
<%
while (iter.hasNext()) {
  String shouhin_id = (String)iter.next();
  TreeMap ukeharaimap = (TreeMap)yoteimap.get(shouhin_id);
  HashMap nyuukomap = (HashMap)ukeharaimap.get("01nyuuko");
  HashMap shukkomap = (HashMap)ukeharaimap.get("02shukko");
  int nyuukorow = nyuukomap.size();
  int shukkorow = shukkomap.size();
  int row = nyuukorow + shukkorow + 2;
  if (event.isHacchuu()) {
    row = row + 2;
  }
  int hacchuuten = ((Integer)ukeharaimap.get("hacchuuten")).intValue();
  String irisuu = (String)ukeharaimap.get("irisuu");
%>
<tr>
<td rowspan=<%=row %> valign="top">
<font style="font-weight:bold">
<%=shouhin_id %>
<%=ukeharaimap.get("shouhin") %>
<%=ukeharaimap.get("kikaku") %>
</font>
<br>
<br>
<%
if (event.isHacchuu()) {
  Boolean timeover = (Boolean)ukeharaimap.get("timeover");
  if ((timeover != null) && (timeover.booleanValue())) {
%>
<font style="font-weight:bold">
※※※※※※※※※※※※※<br>
発注期限を過ぎています<br>
※※※※※※※※※※※※※<br>
</font>
<%
  }
}
%>
仕入リードタイム：<%=ukeharaimap.get("shiireleadtime") %>日<br>
最低発注数：<%=ukeharaimap.get("saiteihacchuusuu") %><br>
発注単位数：<%=ukeharaimap.get("hacchuutanisuu") %><br>
発注規格：<%=ukeharaimap.get("hacchuukikaku") %><br>
入り数：<%=irisuu %><br>
出荷期限：<%=ukeharaimap.get("shukkakigennissuu") %>日<br>
要注意区分：<font style="color:red"><%=ukeharaimap.get("youchuuikubun") %></font><br>
<%
String hacchuucomment = (String)ukeharaimap.get("hacchuucomment");
if (hacchuucomment != null) {
%>
発注コメント：<%=hacchuucomment %><br>
<%
}
Integer mochikoshi_flg = (Integer)ukeharaimap.get("mochikoshi_flg");
if ((mochikoshi_flg != null) && (mochikoshi_flg.intValue() == 1)) {
%>
<font style="font-weight:bold">
●在庫を翌週もちこしできません<br>
</font>
<% } %>
</td>
<th></th>
<%
  if (kubunflg) {
%><th></th><%
  }
  diter = dates.iterator();
  while (diter.hasNext()) {
    java.util.Date date = (java.util.Date)diter.next();
    if (!date.before(inputdate)) {
%>
<th><%=sdf1.format(date) %></th>
<% } } %>
</tr>
<%
if (event.isHacchuu()) {
%>
<tr>
<th>発注</th>
<% if (kubunflg) { %>
<th></th>
<% } %>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td><input name="suuryou<%=shouhin_id %>" type="text" size="6" onchange="changeHacchuu('<%=shouhin_id %>',<%=irisuu %>)" ></td>
<% for (int i=0; i<(datesnum-4); i++) { %>
<td>&nbsp;</td>
<% } %>
</tr>
<%
}
Set nkeys = nyuukomap.keySet();
Iterator niter = nkeys.iterator();
if (nyuukorow > 0) {
%>
<tr>
<th rowspan=<%=nyuukorow %> valign="top">入庫</th>
<%
}
boolean first = true;
while (niter.hasNext()) { //入庫区分ごとのループ
  Integer kubun = (Integer)niter.next();
  if (kubunflg) {
    String kubunstr = NyuukokubunMap.getNyuukokubun(kubun.intValue()).
      getNyuukokubun();
    if (!first) {
%>
<tr>
<% } else { first = false; } %>
<th><%=kubunstr %></th>
<%
  }
  datesmap = (TreeMap)nyuukomap.get(kubun);
  diter = dates.iterator();
  while (diter.hasNext()) {
    java.util.Date date = (java.util.Date)diter.next();
    Float suuryou = (Float)datesmap.get(date);
    if (!date.before(inputdate)) {
      if (suuryou != null) {
%>
<td align="right"><%=df.format(suuryou) %></td>
<% } else { %>
<td></td>
<% } } } %>
</tr>
<% } %>

<%
Set skeys = shukkomap.keySet();
Iterator siter = skeys.iterator();
if (shukkorow > 0) {
%>
<tr>
<th rowspan=<%=shukkorow %> valign="top">出庫</th>
<%
}
first = true;
while (siter.hasNext()) { //出庫区分ごとのループ
  Integer kubun = (Integer)siter.next();
  if (kubunflg) {
    String kubunstr = ShukkokubunMap.getShukkokubun(kubun.intValue()).
      getShukkokubun();
    if (!first) {
%>
<tr>
<% } else { first = false; } %>
<th><%=kubunstr %></th>
<%
  }
  datesmap = (TreeMap)shukkomap.get(kubun);
  diter = dates.iterator();
  while (diter.hasNext()) {
    java.util.Date date = (java.util.Date)diter.next();
    Float suuryou = (Float)datesmap.get(date);
    if (!date.before(inputdate)) {
      if (suuryou != null) {
%>
<td align="right"><%=df.format(suuryou) %></td>
<% } else { %>
<td></td>
<% } } } %>
</tr>
<% } %>

<%
HashMap zaikomap = (HashMap)ukeharaimap.get("03zaiko");
datesmap = (TreeMap)zaikomap.get("zaiko");
diter = dates.iterator();
%>
<tr>
<th>在庫</th>
<% if (kubunflg) { %>
<th></th>
<%
}
  //boolean isfirst = true;
  int col = 0;
  int zindex = 0;
  while (diter.hasNext()) {
    java.util.Date date = (java.util.Date)diter.next();
    Float suuryou = (Float)datesmap.get(date);
    if (!date.before(inputdate)) {
      col++;
      if (suuryou != null) {
%>
<td align="right">
<%-- if (!isfirst) { --%>
<% if (col > 3) { %>
<input type="hidden" name="hiddenz<%=shouhin_id %>_<%=zindex%>" value="<%=suuryou %>">
<span id="z<%=shouhin_id %>_<%=zindex %>">
<% zindex++; } %>
<%=df.format(suuryou) %>
<%-- if (!isfirst) { --%>
<% if (col > 3) { %>
</span>
<%-- } else { isfirst = false; } --%>
<% } %>
</td>
<% } else { %>
<td></td>
<% } } } %>
</tr>
<%
if (event.isHacchuu()) {
%>
<tr>
<th>発注点</th>
<% if (kubunflg) { %>
<th></th>
<% } %>
<% for (int i=0; i<datesnum; i++) { %>
<td align="right"><%=hacchuuten %></td>
<% } %>
<tr>
<% } } %>
</table>
<% if (event.isHacchuu() && (user.getPriv() != 3)) { %>
<input type="submit" value="発注">
<% } %>
</osx:form>
