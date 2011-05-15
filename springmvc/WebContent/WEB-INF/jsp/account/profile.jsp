<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>
<p>Email: <c:out value="${user.email}" /></p>
<p>Password: <c:out value="${user.password}" /></p>
<p>First Name: <c:out value="${user.firstName}" /></p>
<p>Last Name: <c:out value="${user.lastName}" /></p>		
<p>Company: <c:out value="${user.company.name}" /></p>			
<%@ include file="/WEB-INF/jsp/footer.jsp" %>
