<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="java.util.*,java.text.*,com.oisix.oisystemfr.TransactionServlet,com.oisix.oisystemfr.TransactionEvent,com.oisix.oisystemzs.ejb.*,com.oisix.oisystemzs.objectmap.*" %>
<jsp:useBean id="event" scope="request" class="com.oisix.oisystemzs.eventhandler.ShukkokakuteiinputEvent" />
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<script language="JavaScript1.1">
function changeKingaku(ind1, ind2) {
  suuryoustring = document.main.elements["#suuryou"+ind1+"_"+ind2].value;
  suuryou = parseFloat(suuryoustring);
  if (isNaN(suuryou)) { return; }
  tankastring = document.main.elements["#tanka"+ind1+"_"+ind2].value;
  tanka = parseFloat(tankastring);
  if (isNaN(tanka)) { return; }
  document.main.elements["#kingaku"+ind1+"_"+ind2].value = suuryou * tanka;
}
</script>
<%
TransactionEvent eventerr = (TransactionEvent)
  request.getAttribute(TransactionServlet.RESULTKEY);
if (eventerr != null) {
  Iterator iter = eventerr.getErrorlist().iterator();
%>
�G���[���������܂����B
<p>
<%
  while (iter.hasNext()) {
%>
<%=iter.next() %><br>
<%
  }
}
// �����܂ŃG���[�������ꍇ

DecimalFormat df = new DecimalFormat("##########.##");
ShukkayoteiData shukkayotei = event.getShukkayotei();
int shukkokubun = shukkayotei.getShukkokubun();
ShukkokubunData kubun = ShukkokubunMap.getShukkokubun(shukkokubun);
%>
�o�ח\��ԍ�&nbsp;<%=shukkayotei.getShukkayotei_bg()%>
<osx:form action="shukka/shukkokakuteiinput.ev" method="post">
�o�ɋ敪�F<%=kubun.getShukkokubun() %><br>
<input type="hidden" name="#shukkokubun" value="<%=shukkokubun %>">
<input type="hidden" name="id" value="<%=shukkayotei.getShukkayotei_id()%>">
�o�ɓ�&nbsp;
<osx:yearselect name="#shukko_year" from="2000" to="2010" />�N
<osx:monthselect name="#shukko_month" />��
<osx:dateselect name="#shukko_date" />��
<input type="submit" value="���ʌ���">
</osx:form>
<%
if (!event.getIsFirst() && event.getErrors().size() == 0) {
Collection col = event.getShukkayoteimeisai();
%>
<osx:form transaction="doshukkokakuteiinput.ev" result="shukka/shukkoinputsuccess.ev" method="post" option="name='main'">
<input type="hidden" name="#shukko_year" value="<%=event.getShukko_year()%>">
<input type="hidden" name="#shukko_month" value="<%=event.getShukko_month()%>">
<input type="hidden" name="#shukko_date" value="<%=event.getShukko_date()%>">
<table>
<tr><th>
���i�R�[�h
</th><th>
���i��
</th><th>
�K�i
</th><th>
�ܖ�����
</th><th>
����
</th>
<%-- <th>�P��</th> --%>
<th>
�P��
</th><th>
���z
</th>
<%-- <th>�o�ɋ敪</th> --%>
</tr>
<%
Iterator iter = col.iterator();
int meisaisuu = 0;
while (iter.hasNext()) {
HashMap symeisai = (HashMap)iter.next();
LinkedList zmlist = (LinkedList)symeisai.get("ZAIKOMEISAI");
int meisai_id = ((Integer)symeisai.get("MEISAI_ID")).intValue();
String shouhin_id = (String)symeisai.get("SHOUHIN_ID");
String shouhinmei = (String)symeisai.get("SHOUHINMEI");
String kikaku = (String)symeisai.get("KIKAKU");
String tani = (String)symeisai.get("TANI");
String hyoujunbaika = df.format((Float)symeisai.get("HYOUJUNBAIKA"));
int row = zmlist.size();
%>
<tr><td rowspan="<%=row%>" valign="top">
<input type="hidden" name="#shukkayoteimeisai_id<%=meisaisuu%>" value="<%=meisai_id%>">
<input type="hidden" name="#shouhin_id<%=meisaisuu%>" value="<%=shouhin_id%>">
<%=shouhin_id%>
</td><td rowspan="<%=row%>" valign="top">
<%=shouhinmei%>
</td><td rowspan="<%=row%>" valign="top">
<%=kikaku%>
</td>
<%
Iterator zmiter = zmlist.iterator();
int n = 0;
while (zmiter.hasNext()) {
HashMap zmeisai = (HashMap)zmiter.next();
float tanka = ((Float)zmeisai.get("TANKA")).floatValue();
float shukkasuu = ((Float)zmeisai.get("SHUKKASUU")).floatValue();
float zaikosuu = ((Float)zmeisai.get("MEISAISUU")).floatValue();
float kingaku = ((Float)zmeisai.get("KINGAKU")).floatValue();
SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d");
java.util.Date shoumikigen_date = (java.util.Date)zmeisai.get("SHOUMIKIGEN");
String shoumikigen = "";
if (shoumikigen_date != null) {
  shoumikigen = sdf.format(shoumikigen_date);
}
%>
<% String onchange = "onchange='changeKingaku(" + meisaisuu + ", " + n + ")'"; %>
<% if (n > 0) { %><tr><% } %>
<td>
<%=shoumikigen%>
<input type="hidden" name='<%="#shoumikigen"+meisaisuu+"_"+n %>' value="<%=shoumikigen%>">
</td><td>
<osx:input type="text" name='<%="#suuryou"+meisaisuu+"_"+n %>' size="6" value="<%=df.format(shukkasuu)%>" option="<%=onchange %>" />/<%=df.format(zaikosuu)%>
</td>
<%-- <td><osxzs:tanimeiselect name='<%="#tani"+meisaisuu+"_"+n %>' defaultval="�i�P�ʁj" selected="<%=tani%>" /> </td> --%>
<input type="hidden" name='<%="#tani"+meisaisuu+"_"+n %>' value="<%=tani%>">
<td>
<osx:input type="text" name='<%="#tanka"+meisaisuu+"_"+n %>' size="6" value="<%=df.format(tanka)%>" option="<%=onchange %>" />
</td><td>
<osx:input type="text" name='<%="#kingaku"+meisaisuu+"_"+n %>' size="6" value="<%=df.format(kingaku)%>" />
</td>
<%-- <td>
<osxzs:shukkokubunselect name='<%="#shukkokubun"+meisaisuu+"_"+n %>' defaultval="�i�敪�j" selected="<%=shukkokubun%>" />
</td> --%>
</tr>
<input type="hidden" name='<%="#hyoujunbaika"+meisaisuu+"_"+n %>' value="<%=hyoujunbaika %>">
<% n++; %>
<% } %>
<input type="hidden" name="#zaikoindex<%=meisaisuu%>" value="<%=n%>">
<% meisaisuu++; %>
<% } %>
</table>
<input type="hidden" name="#meisaisuu" value="<%=meisaisuu%>">
<input type="submit" value="�o�Ɋm��f�[�^����"><br>
</osx:form>
<% } else if (event.getErrors().size() > 0) { %>
<%
  Iterator erriter = event.getErrors().iterator();
%>
�݌ɂɊւ���G���[���������܂����B
<p>
<%
  while (erriter.hasNext()) {
%>
<%=erriter.next() %><br>
<%
  }
  Collection errorids = event.getErrorids();
  if (errorids.size() > 0) {
    java.util.Date date = event.getShukkobi();
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(Calendar.DATE, -8);
%>
<osx:form action="zaiko/nyuushukkoshoukai.ev" method="post">
<input type="hidden" name="nyuushukkoyear" value="<%=cal.get(Calendar.YEAR) %>">
<input type="hidden" name="nyuushukkomonth" value="<%=cal.get(Calendar.MONTH) + 1 %>">
<input type="hidden" name="nyuushukkodate" value="<%=cal.get(Calendar.DATE) %>">
<input type="hidden" name="nyuushukkospan" value="9">
<%
    int index = 0;
    Iterator iditer = errorids.iterator();
    while (iditer.hasNext()) {
      String id = (String)iditer.next();
%>
<input type="hidden" name="shouhin_id<%=index %>" value="<%=id %>">
<%
      index++;
    }
%>
<input type="submit" value="�G���[�ɂȂ������i�̓��o�ɂ��Ɖ��">
</osx:form>
<%
  }
}
%>
