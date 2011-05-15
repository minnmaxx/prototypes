package test;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;

import flex.messaging.Destination;
import flex.messaging.MessageBroker;
import flex.messaging.config.ChannelSettings;

public class UserService {

	@Autowired(required=true)
	private GameAdapter adapter;
	
	@PostConstruct
	public void init() {
		
//		String type = MessageService.class.getName();
//		
//		MessageService service = (MessageService) messageBroker.getServiceByType(type);
//		
//		MessageDestination destination = (MessageDestination) service.getDestination( "game" );
//		ServiceAdapter adapter = destination.getAdapter();
//
//		MessageDestination destination1 = (MessageDestination) service.getDestination( "game1" );
//		ServiceAdapter adapter1 = destination1.getAdapter();		
	}
	
	@SuppressWarnings("unchecked")
	public Collection<String> getConnectionInfo() {
		
		Destination destination = adapter.getDestination(); 
		System.out.println( destination.getId() );
		
		MessageBroker messageBroker = 
			adapter.getDestination().getService().getMessageBroker();
		
		List<String> channelIds = (List<String>) destination.getChannels();
		for( Object id : channelIds ) {
			ChannelSettings settings = messageBroker.getChannelSettings( (String) id );
			System.out.println( settings.getUri() );
		}
		
		return null;
	}
	
	public FractechUser getUser()
	{
		getConnectionInfo();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
  		UserDetailsImpl user = (UserDetailsImpl) auth.getPrincipal();
  		if( user != null )
  		{
  			return new FractechUser( user.getUsername(), getFullname( user.getUsername() ) );
  		}		
  		
  		throw new IllegalStateException( "This session hasn't been authenticated." );
	}
	
	private String getFullname( String username )
	{
		if( "cody".endsWith( username ) )
		{
			return "Cody McCain";
		}
		else if( "ariss".endsWith( username ) )
		{
			return "Ariss Zhao";
		}
		else if( "tangent".endsWith( username ) )
		{
			return "Tangent Lin";
		}
		throw new IllegalArgumentException( "Unidentified username:" + username );
	}
}
