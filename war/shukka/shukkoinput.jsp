<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="java.util.*,com.oisix.oisystemfr.TransactionServlet,com.oisix.oisystemfr.TransactionEvent,com.oisix.oisystemzs.ejb.*,com.oisix.oisystemzs.eventhandler.DoshukkoinputEvent" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<script language="JavaScript1.1">
function changeKingaku(ind) {
  suuryoustring = document.main.elements["#suuryou" + ind].value;
  suuryou = parseFloat(suuryoustring);
  if (isNaN(suuryou)) { return; }
  tankastring = document.main.elements["#tanka" + ind].value;
  tanka = parseFloat(tankastring);
  if (isNaN(tanka)) { return; }
  document.main.elements["#kingaku" + ind].value = suuryou * tanka;
}
</script>
<%
TransactionEvent eventerr = (TransactionEvent)
  request.getAttribute(TransactionServlet.RESULTKEY);
if (eventerr != null) {
  LinkedList errlist = eventerr.getErrorlist();
  if (errlist.size() == 0) {
	DoshukkoinputEvent checkevent = (DoshukkoinputEvent)
	  request.getAttribute(TransactionServlet.RESULTKEY);
    Iterator iter = checkevent.getChecklist().iterator();
%>
���͍��ڂ��`�F�b�N���Ă��������B<br>
<%
    while (iter.hasNext()) {
%>
�E<%=iter.next() %><br>
<%
    }
%>
��낵����΂�����x���M�{�^���������Ă��������B
<p>
<%
  } else {
    Iterator iter = eventerr.getErrorlist().iterator();
%>
�G���[���������܂����B
<p>
<%
    while (iter.hasNext()) {
%>
�E<%=iter.next() %><br>
<%
    }
  }
}
// �����܂ŃG���[�������ꍇ
%>
<osx:form transaction="doshukkoinput.ev" result="shukka/shukkoinputsuccess.ev" method="post" option="name='main'">
�o�ɓ�&nbsp;
<osx:yearselect name="#shukko_year" from="2000" to="2020" />�N
<osx:monthselect name="#shukko_month" />��
<osx:dateselect name="#shukko_date" />��
<table>
<tr><th>
�o�ח\��ԍ�
</th><th>
���i�R�[�h
</th><th>
���i��
</th><th>
�K�i
</th><th>
�ܖ�����
</th><th>
����
</th><th>
�P��
</th><th>
�P��
</th><th>
���z
</th><th>
�o�ɋ敪
</th></tr>
<% for (int i = 0; i < 10; i++) { %>
<% String onchange = "onchange='changeKingaku(" + i + ")'"; %>
<% String shouhinmei = (String)session.getAttribute("shouhinmei" + i); %>
<% if (shouhinmei == null) { shouhinmei = ""; } %>
<% String kikaku = (String)session.getAttribute("kikaku" + i); %>
<% if (kikaku == null) { kikaku = ""; } %>
<tr><td>
<osx:input type="text" name='<%="#shukkayotei_bg"+i %>' size="14" value="" />
</td><td>
<osx:input type="text" name='<%="#shouhin_id"+i %>' size="6" value="" /><span style="cursor:hand" onClick="openSubSearch('../shared/shouhinsearch.ev?form=main&shouhin_id=shouhin_id<%=i%>&shouhin=shouhin<%=i%>&kikaku=kikaku<%=i%>&tanimei=tani<%=i%>')"><osx:img src="shared/image/icon_search01.gif" width="17" height="15" /></span>
</td><td>
<span id="shouhin<%=i%>"><%=shouhinmei%></span>
</td><td>
<span id="kikaku<%=i%>"><%=kikaku%></span>
</td><td>
<osx:input type="text" name='<%="#shoumikigen"+i %>' size="12" value="" />
</td><td>
<osx:input type="text" name='<%="#suuryou"+i %>' size="6" value="" option="<%=onchange %>" />
</td><td>
<osxzs:tanimeiselect name='<%="#tani"+i %>' defaultval="�i�P�ʁj" /></td><td>
<osx:input type="text" name='<%="#tanka"+i %>' size="6" value="" option="<%=onchange %>" />
</td><td>
<osx:input type="text" name='<%="#kingaku"+i %>' size="6" value="" />
</td><td>
<osxzs:shukkokubunselect name='<%="#shukkokubun"+i %>' defaultval="�i�敪�j" />
</td></tr>
<% } %>
</table>
<input type="submit" name="submit" value="�o�Ɋm��f�[�^����"><br>
</osx:form>
