<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

Request Parameters (Single Value)
<ul>
<c:forEach var="item" items="${param}">
	<li><b>${item.key}</b>: ${item.value}</li>
</c:forEach>
</ul>

Request Parameters (Multiple Values)
<ul>
<c:forEach var="item" items="${paramValues}">
	<li><b>${item.key}</b>: 
		<c:forEach var="value" items="${item.value}">
			${value}
		</c:forEach>
	</li>
</c:forEach>
</ul>

Request Headers (Single Value)
<ul>
<c:forEach var="item" items="${header}">
	<li><b>${item.key}</b>: ${item.value}</li>
</c:forEach>
</ul>

Request Headers (Multiple Values)
<ul>
<c:forEach var="item" items="${headerValues}">
	<li><b>${item.key}</b>: 
		<c:forEach var="value" items="${item.value}">
			${value}
		</c:forEach>
	</li>
</c:forEach>
</ul>

Context Parameters in web.xml
<ul>
<c:forEach var="item" items="${initParam}">
	<li><b>${item.key}</b>: ${item.value}</li>
</c:forEach>
</ul>

Cookies
<ul>
<c:forEach var="item" items="${cookie}">
	<li><b>${item.key}</b>: ${item.value.value}</li>
</c:forEach>
</ul>

Scope Variables in Search Order

page
<c:choose>
	<c:when test="${not empty pageScope}">
		<ul>
		<c:forEach var="item" items="${pageScope}">
			<li><b>${item.key}</b>: ${item.value}</li>
		</c:forEach>
		</ul>
	</c:when>
	<c:otherwise>
		<p>Empty</p>
	</c:otherwise>
</c:choose>

request
<c:choose>
	<c:when test="${not empty requestScope}">
		<ul>
		<c:forEach var="item" items="${requestScope}">
			<li><b>${item.key}</b>: ${item.value}</li>
		</c:forEach>
		</ul>
	</c:when>
	<c:otherwise>
		<p>Empty</p>
	</c:otherwise>
</c:choose>

session
<c:choose>
	<c:when test="${not empty sessionScope}">
		<ul>
		<c:forEach var="item" items="${sessionScope}">
			<li><b>${item.key}</b>: ${item.value}</li>
		</c:forEach>
		</ul>
	</c:when>
	<c:otherwise>
		<p>Empty</p>
	</c:otherwise>
</c:choose>

application
<c:choose>
	<c:when test="${not empty applicationScope}">
		<ul>
		<c:forEach var="item" items="${applicationScope}">
			<li><b>${item.key}</b>: ${item.value}</li>
		</c:forEach>
		</ul>
	</c:when>
	<c:otherwise>
		<p>Empty</p>
	</c:otherwise>
</c:choose>

<p>Page Context</p>
 
Request Properties org.apache.catalina.core.ApplicationHttpRequest
<ul>
	<li><b>characterEncoding</b>: ${pageContext.request.characterEncoding}</li>
	<li><b>contentType</b>: ${pageContext.request.contentType}</li>
	<li><b>locale</b>: ${pageContext.request.locale}</li>
	<li><b>locales</b>: ${pageContext.request.locales}</li>
	<li><b>protocol</b>: ${pageContext.request.protocol}</li>
	<li><b>remoteAddr</b>: ${pageContext.request.remoteAddr}</li>
	<li><b>remoteHost</b>: ${pageContext.request.remoteHost}</li>
	<li><b>scheme</b>: ${pageContext.request.scheme}</li>
	<li><b>serverName</b>: ${pageContext.request.serverName}</li>
	<li><b>serverPort</b>: ${pageContext.request.serverPort}</li>
	<li><b>secure</b>: ${pageContext.request.secure}</li>
</ul>

Response Properties 
<ul>
	<li><b>bufferSize</b>: ${pageContext.response.bufferSize}</li>
	<li><b>characterEncoding</b>: ${pageContext.response.characterEncoding}</li>
	<li><b>locale</b>: ${pageContext.response.locale}</li>
	<li><b>committed</b>: ${pageContext.response.committed}</li>
</ul>

Session Properties
<ul>
	<li><b>creationTime</b>: ${pageContext.session.creationTime}</li>
	<li><b>id</b>: ${pageContext.session.id}</li>
	<li><b>lastAccessedTime</b>: ${pageContext.session.lastAccessedTime}</li>
	<li><b>maxInactiveInterval</b>: ${pageContext.session.maxInactiveInterval}</li>
</ul>

Servlet Properties
<ul>
	<li><b>majorVersion</b>: ${pageContext.servletContext.majorVersion}</li>
	<li><b>minorVersion</b>: ${pageContext.servletContext.minorVersion}</li>
	<li><b>serverInfo</b>: ${pageContext.servletContext.minorVersion}</li>
	<li><b>servletContextName</b>: ${pageContext.servletContext.servletContextName}</li>
</ul>


<%@ include file="/WEB-INF/jsp/footer.jsp" %>
