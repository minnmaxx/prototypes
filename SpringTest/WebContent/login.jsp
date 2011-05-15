<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ page import="org.springframework.security.ui.AbstractProcessingFilter" %>
<%@ page import="org.springframework.security.ui.webapp.AuthenticationProcessingFilter" %>
<%@ page import="org.springframework.security.AuthenticationException" %>
<%@ page import="org.springframework.security.Authentication" %>
<%@ page import="org.springframework.security.context.SecurityContextHolder" %>
<%@ page import="org.springframework.security.userdetails.User" %>

<!-- Not used unless you declare a <form-login login-page="/login.jsp"/> element -->


<%@page import="org.springframework.security.GrantedAuthority"%><html>
  <head>
    <title>CUSTOM SPRING SECURITY LOGIN</title>
  </head>

  <body onload="document.f.j_username.focus();">
  
  <%
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
  		User user = null;
  		if( auth != null && auth.getPrincipal() instanceof User )
  		{
  			user = (User) auth.getPrincipal();
  		}
  
		if( user != null ) 
		{ 
   %>
   			<form action="<c:url value='j_spring_security_logout '/>" method="POST">
      		<table>
        		<tr><td>Logged in as:</td><td><%= user.getUsername() %></td></tr>
        		<tr><td>Authorities:</td>
  <% 
  			for( GrantedAuthority authority : user.getAuthorities() )
  			{
   %>
   					<td><%= authority.toString() %></td>
  <% 				
  			}
   %>      		
        		</tr>
        		<tr><td>isAccountNonExpired:</td><td><%= user.isAccountNonExpired() %></td></tr>
        		<tr><td>isAccountNonLocked:</td><td><%= user.isAccountNonLocked() %></td></tr>
        		<tr><td>isCredentialsNonExpired:</td><td><%= user.isCredentialsNonExpired() %></td></tr>
        		<tr><td>isEnabled:</td><td><%= user.isEnabled() %></td></tr>
        		<tr><td colspan='2'><input name="exit" type="submit" value="Exit"></td></tr>
      		</table>
    		</form>  
  <% 
    	} else {
   %>
    		<h1>Please Login</h1>
    		Valid users:
			<ul>
				<li>tangent/t</li>
				<li>ariss/a</li>
				<li>cody/c</li>
			</ul>

		    <%-- this form-login-page form is also used as the
		         form-error-page to ask for a login again.
		         --%>
		    <c:if test="${not empty param.login_error}">
		      <font color="red">
		        Your login attempt was not successful, try again.<br/><br/>
		        Reason: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>.
		      </font>
		    </c:if>

    		<form name="f" action="<c:url value='j_spring_security_check'/>" method="POST">
      		<table>
        		<tr><td>User:</td><td><input type='text' name='j_username' value='<c:if test="${not empty param.login_error}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}"/></c:if>'/></td></tr>
        		<tr><td>Password:</td><td><input type='password' name='j_password'></td></tr>

        		<tr><td colspan='2'><input name="submit" type="submit"></td></tr>
        		<tr><td colspan='2'><input name="reset" type="reset"></td></tr>
      		</table>
		    </form>   
  <%  	
    	}
   %>  

  </body>
</html>
