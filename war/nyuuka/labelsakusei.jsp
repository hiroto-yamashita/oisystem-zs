<%@ page contentType="text/html; charset=SJIS" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<%@ page import="java.util.*,com.oisix.oisystemfr.TransactionServlet,com.oisix.oisystemfr.TransactionEvent,com.oisix.oisystemzs.ejb.*" %>
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
%>
<osx:form transaction="Makelabelpdf.ev" result="nyuuka/labelpdf.ev" method="post">
<%
int labelsuu = 0;
%>
<osxzs:labelsakusei size="20">
<osx:prev action="nyuuka/labelsakusei.ev">
�O��20��
</osx:prev>
<osx:next action="nyuuka/labelsakusei.ev">
����20��
</osx:next>
<osx:total/>����<osx:startind />���ڂ���<osx:endind />���ڂ܂ł�\�����Ă��܂��B<br>
<table><tr>
<th>���i�R�[�h</th>
<th>���i��</th>
<th>����</th>
<th>�ܖ�����</th>
<th>�o�׊���</th>
</tr>
<osx:items>
<%
HashMap item = (HashMap)request.getAttribute("ITEM");
Date shoumikigen = (Date)item.get("shoumikigen");
Date shukkakigen = (Date)item.get("shukkakigen");
Integer nyuukayotei_id = (Integer)item.get("nyuukayotei_id");
String strhacchuusuuryou = ((Float)item.get("hacchuusuuryou")).toString();
if(shoumikigen != null && shukkakigen != null){
request.setAttribute("shoumikigen" , shoumikigen);
request.setAttribute("shukkakigen" , shukkakigen);

%>
<tr>
<td><osx:item field="shouhin_id" />
<input type="hidden" name='<%="#shouhin_id"+labelsuu %>' value="<%=item.get("shouhin_id")%>" /></td>
<td><osx:item field="shouhin" />
<input type="hidden" name='<%="#shouhin"+labelsuu %>' value="<%=item.get("shouhin")%>" /></td>
<td><osx:input type="text" name='<%="#maisuu"+labelsuu %>' value="<%=strhacchuusuuryou%>" size="6" /></td>
<td>
<osx:yearselect name='<%="#shoumiyear"+labelsuu %>' from="2000" to="2020" datekey="shoumikigen" />�N
<osx:monthselect name='<%="#shoumimonth"+labelsuu %>' datekey="shoumikigen" />��
<osx:dateselect name='<%="#shoumidate"+labelsuu %>' datekey="shoumikigen" />��
</td>
<td>
<osx:yearselect name='<%="#shukkayear"+labelsuu %>' from="2000" to="2020" datekey="shukkakigen" />�N
<osx:monthselect name='<%="#shukkamonth"+labelsuu %>' datekey="shukkakigen" />��
<osx:dateselect name='<%="#shukkadate"+labelsuu %>' datekey="shukkakigen" />��
</td>
</tr>
<%}else{%>
<tr>
<td><osx:item field="shouhin_id" />
<input type="hidden" name='<%="#shouhin_id"+labelsuu %>' value="<%=item.get("shouhin_id")%>" /></td>
<td><osx:item field="shouhin" />
<input type="hidden" name='<%="#shouhin"+labelsuu %>' value="<%=item.get("shouhin")%>" /></td>
<td><osx:input type="text" name='<%="#maisuu"+labelsuu %>' value="<%=strhacchuusuuryou%>" size="6" /></td>
<td><input type="hidden" name='<%="#shoumiyear"+labelsuu %>' value="0" /></td>
<td><input type="hidden" name='<%="#shukkayear"+labelsuu %>' value="0" /></td>
</tr>
<%}%>
<input type="hidden" name="#nyuukayotei_id<%=labelsuu%>" value="<%=nyuukayotei_id%>" />
<%
labelsuu++;
%>
</osx:items>
</table>
�u�����N����&nbsp;<input type="text" name="#burankumaisuu" value="" size="6" >
<%
String strlabelsuu = Integer.toString(labelsuu);
%>
<input type="hidden" name="#labelsuu" value="<%=labelsuu%>">
<input type="submit" value="���x���쐬">
</osxzs:labelsakusei>
</osx:form>
