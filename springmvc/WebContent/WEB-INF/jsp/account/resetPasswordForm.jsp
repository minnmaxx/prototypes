<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<form:form modelAttribute="user" action="submitPasswordReset.do">
	<form:input readonly="true" path="email" value="${user.email}" />
	<p>Password <input name="password" type="password" value="" /></p>
	<p>Confirm Password <input name="confirmPassword" type="password" value="" /></p>
	<p><input type="submit" value="Submit" /></p>
	<input type="hidden" name="token" value="${token}" />
	
</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
