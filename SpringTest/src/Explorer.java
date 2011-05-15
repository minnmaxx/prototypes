import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.flex.core.ManageableComponentFactoryBean;
import org.springframework.flex.core.MessageBrokerFactoryBean;
import org.springframework.flex.messaging.MessageDestinationFactory;
import org.springframework.flex.security.SpringSecurityLoginCommand;
import org.springframework.security.concurrent.ConcurrentSessionControllerImpl;
import org.springframework.security.concurrent.SessionRegistryImpl;
import org.springframework.security.context.ThreadLocalSecurityContextHolderStrategy;
import org.springframework.security.ui.basicauth.BasicProcessingFilter;
import org.springframework.security.ui.logout.LogoutFilter;
import org.springframework.security.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.userdetails.ldap.LdapUserDetailsService;
import org.springframework.security.userdetails.memory.InMemoryDaoImpl;
import org.springframework.web.filter.DelegatingFilterProxy;

import flex.messaging.FlexSession;
import flex.messaging.MessageBroker;
import flex.messaging.services.MessageService;


public class Explorer {
	
	MessageBroker broker;

	SpringSecurityLoginCommand command;
	LogoutFilter logoutFilter;
	BasicProcessingFilter f1;
	DelegatingFilterProxy proxy;
	FlexSession session;
	ConcurrentSessionControllerImpl impl;
	SessionRegistryImpl i1;
	ThreadLocalSecurityContextHolderStrategy s1;
	MessageDestinationFactory f2;
	
	DefaultListableBeanFactory f3;
	ManageableComponentFactoryBean f4;
	MessageBrokerFactoryBean b3;
	MessageService ms;
	
	InMemoryDaoImpl d1;
	JdbcDaoImpl d2;
	LdapUserDetailsService d3;
	
	org.springframework.security.intercept.web.FilterSecurityInterceptor i2;
}
