<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>
<form:form action="${action}">
	Please enter your work email address:
	<input name="email" />
	<p/>
	<input type="submit" value="Submit" />
</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>