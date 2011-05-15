<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<form:form action="login.do">
	<p>Email <input name="email" value="" /></p>
	<p>Password <input name="password" type="password" value="" /></p>
	<p><input type="submit" value="Submit" /></p>
</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
