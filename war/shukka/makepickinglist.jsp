<%@ page contentType="text/html; charset=SJIS" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<jsp:useBean id="event" scope="request" class="com.oisix.oisystemzs.eventhandler.MakepickinglistEvent" />

<p>
<% if (event.getIsError()) { %>
�G���[���������܂����B
<% } else { %>
�s�b�L���O���X�g���쐬���܂����B<br>
<a href="../<%=event.getFileName() %>">�_�E�����[�h</a>
<% } %>
