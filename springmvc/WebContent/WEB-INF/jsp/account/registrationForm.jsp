<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<!-- 
<spring:hasBindErrors name="user">
<ul>
	<c:forEach items="${errors.allErrors}" var="error">
		<li>${error}</li>
	</c:forEach>
</ul>
</spring:hasBindErrors>
 -->

<form:form modelAttribute="user" action="submitRegistration.do">
	<p>First Name <form:input path="firstName" /><form:errors path="firstName" /></p>
	<p>Last Name <form:input path="lastName" /><form:errors path="lastName" /></p>
	<p>Company ${user.company.name}</p>					
	<p>Password <form:input type="password" path="password" /><form:errors path="password" /></p>
	<p>Confirm Password <form:input type="password" path="confirmPassword" /><form:errors path="confirmPassword" /></p>
	<p><input type="submit" value="Submit" /></p>
	<form:input type="hidden" path="token" />
	<form:input type="hidden" path="email" />
</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
