<%@ page contentType="text/html; charset=SJIS" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<jsp:useBean id="event" scope="request" class="com.oisix.oisystemzs.eventhandler.MakelabelpdfEvent" />
ラベル作成完了しました。<br>
ラベル<a href="../<%=session.getAttribute("FILENAME") %>">ダウンロード</a>
