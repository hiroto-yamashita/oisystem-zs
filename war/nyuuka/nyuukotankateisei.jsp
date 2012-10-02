<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="java.util.*,com.oisix.oisystemfr.TransactionServlet,com.oisix.oisystemfr.TransactionEvent,com.oisix.oisystemzs.ejb.*" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
入庫単価訂正
<osx:form action="nyuuka/nyuukotankateiseifinish.ev" method="post" option="name='main'">
<input type="hidden" name="nyuuko_id" value="<%=request.getParameter("nyuuko_id") %>" >
現在の単価：<%=request.getParameter("tanka") %>
<p>
訂正後の単価：<osx:input type="text" name="newtanka" size="6" value="<%=request.getParameter("tanka") %>" />
<br>
<input type="submit" value="訂正">
</osx:form>
