<%@ page contentType="text/html; charset=SJIS" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<jsp:useBean id="event" scope="request" class="com.oisix.oisystemzs.eventhandler.YoteihyoupdfEvent" />

入荷予定表を作成しました。<br>
入荷予定表<a href="../<%=event.getFileName() %>">ダウンロード</a>
