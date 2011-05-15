<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<h2>Official</h2>
<c:choose>
	<c:when test='${ not empty sender and 
	                 sender=="initiation" }'>
		<p><spring:message code="initiation.complete" /></p>
	</c:when>
	<c:otherwise>
		<p><spring:message code="${key}" /></p>
	</c:otherwise>

</c:choose>


<h2>Debug</h2>

<spring:hasBindErrors name="retrieveFormRequest">
org.springframework.validation.FieldError
<ul>
	<c:forEach items="${errors.allErrors}" var="error">
		<li>${error.field} <spring:message code="${error.code}" /></li>
	</c:forEach>
</ul>
</spring:hasBindErrors>

<c:if test="${key != null}">
	<p>From resources: <fmt:message key="${key}" /></p>
</c:if>

<c:if test="${messsage != null}">
	<p>message: <c:out value="${message}" /></p>
</c:if>

<c:if test="${link != null}">
	<p/>Click <a href="<c:out value='${link}' />" />here</a>
</c:if>



<%@ include file="/WEB-INF/jsp/footer.jsp" %>