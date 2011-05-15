package test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;

import test.ScissorPaperStone.Result;
import test.ScissorPaperStone.Selection;
import test.messages.ClientListMessage;
import test.messages.GameMessage;
import test.messages.SubscriptionMessage;
import edu.emory.mathcs.backport.java.util.Collections;
import flex.messaging.Destination;
import flex.messaging.MessageDestination;
import flex.messaging.config.ConfigMap;
import flex.messaging.messages.AsyncMessage;
import flex.messaging.messages.CommandMessage;
import flex.messaging.messages.Message;
import flex.messaging.services.MessageService;
import flex.messaging.services.messaging.adapters.MessagingAdapter;

public class GameAdapter extends MessagingAdapter //implements MessageClientListener
{	
	private Map<String, String> usernameToClientId = Collections.synchronizedMap(
			new HashMap<String,String>() );
	private ScissorPaperStone game = new ScissorPaperStone();
	
	public GameAdapter() {
		this( false );
	}

	public GameAdapter(boolean enableManagement) {
		super(enableManagement);
//				
//		usernameToClientId = new HashMap<String, String>();
//		game = new ScissorPaperStone();
	}
	
    /**
     * Initializes the <code>MessagingAdapter</code> with the properties.
     * Subclasses should call <code>super.initialize</code>.
     *
     * @param id Id of the <code>MessagingAdapter</code>.
     * @param properties Properties for the <code>MessagingAdapter</code>.
     */
//    public void initialize(String id, ConfigMap properties)
//    {
//        super.initialize(id, properties);
//    }
	
	
    /**
     * Casts the <code>Destination</code> into <code>MessageDestination</code>
     * and calls super.setDestination.
     *
     * @param destination The destination of the adapter.
     */
    @Override
    public void setDestination(Destination destination)
    {
        MessageDestination dest = (MessageDestination)destination;
        super.setDestination(dest);
    }
	
	
    /**
     * Starts the adapter.
     */
//    @Override
//    public void start()
//    {
//        if (isStarted())
//            return;
//
//        super.start();
//
//        // To be informed when the subscription manager finishes handling a client
//        //MessageClient.addMessageClientCreatedListener( this );
//    }
    
    /**
     * Stops the adapter.
     */
//    @Override
//    public void stop()
//    {
//        if (!isStarted())
//            return;
//
//        super.stop();
//    }
    
	

	@Override
	public Object invoke(Message message) {

		// route to the correct person
		if( message.getBody() instanceof SubscriptionMessage )
		{
			SubscriptionMessage inMessage = (SubscriptionMessage) message.getBody();
			
			if( inMessage.type == SubscriptionMessage.TYPE_SUBSCRIBE )
			{
				sendSubscriptionList();
			}
		}
		else 
		if( message.getBody() instanceof GameMessage )
        {
        	GameMessage inMessage = (GameMessage) message.getBody();
        	
        	inMessage.fromId = getUsername();
        	
        	switch( inMessage.type )
        	{
        	// forward
        	case GameMessage.TYPE_INITIATE_CHALLENGE:
        	case GameMessage.TYPE_ACCEPT_CHALLENGE:
        	case GameMessage.TYPE_DECLINE_CHALLENGE:

            	if( usernameToClientId.containsKey( inMessage.toId ) )
            	{
    	        	sendMessage(message, inMessage.toId );
            	}        	        	              		
        		break;
        		
        	// check if both parties are ready
        	case GameMessage.TYPE_SELECTED_ITEM:
        	
        		game.registerSelection( inMessage.fromId, Selection.valueOf( inMessage.payLoad ) );
        		Result fromResult = game.getResult( inMessage.fromId, inMessage.toId );
        		
        		if( fromResult != Result.NoResult )
        		{
        			GameMessage fromMessage = new GameMessage();
        			fromMessage.fromId = inMessage.fromId;
        			fromMessage.toId = inMessage.toId;
        			fromMessage.type = GameMessage.TYPE_RESULT;
        			fromMessage.payLoad = fromResult.toString() + ":" + game.getSelectionAsString( inMessage.toId );
        			sendMessage( createAsyncMessage( fromMessage ), inMessage.fromId );
        			
        			GameMessage toMessage = new GameMessage();
        			toMessage.fromId = inMessage.fromId;
        			toMessage.toId = inMessage.toId;
        			toMessage.type = GameMessage.TYPE_RESULT;
        			toMessage.payLoad = fromResult.opposite().toString() + ":" + game.getSelectionAsString( inMessage.fromId );;
        			sendMessage( createAsyncMessage( toMessage ), inMessage.toId );
        			        			
        			game.unregister( inMessage.fromId );
        			game.unregister( inMessage.toId );
        		}
        		break;
        		        		
        	default:
        		System.out.println( ">>>>Invalid GameMessage type: " + inMessage );        	
        	}        	
        }        
        	
		return null;
	}

    /**
     * Handle a CommandMessage sent by this adapter's service.
     *
     * @param commandMessage The command message to manage.
     * @return  The result of manage which is null in this case.
     */
    @Override
    public Object manage(CommandMessage commandMessage) 
    {   
    	String clientId = (String)commandMessage.getClientId();
    	String username =  getUsername();       
    	
        if (commandMessage.getOperation() == CommandMessage.SUBSCRIBE_OPERATION)
        {
    		System.out.println( ">>>> manage SUBSCRIBE_OPERATION executed by " + 
    				Thread.currentThread().getName() + " for [clientId=" + clientId + ",username=" + 
    				username + "]" ); 
        	

        	subscribeUser( username, clientId );         	
        }
        else if (commandMessage.getOperation() == CommandMessage.UNSUBSCRIBE_OPERATION)
        {
    		System.out.println( ">>>> manage UNSUBSCRIBE_OPERATION executed by " + 
    				Thread.currentThread().getName() + " for [clientId=" + clientId + ",username=" + 
    				username + "]" ); 
        	        	
        	unsubsribeUser( username, clientId );
        }

        return null;
    }
    
    private AsyncMessage createAsyncMessage( Object body )
    {
        AsyncMessage flexMessage = new flex.messaging.messages.AsyncMessage();
        
        // this is required
        flexMessage.setDestination(getDestination().getId());
        flexMessage.setMessageId( generateMsgId( ) );
        flexMessage.setBody( body );
    	
        return flexMessage;
    }
    
    private void sendSubscriptionList( )
    {
    	if( usernameToClientId.isEmpty() ) return;

        // build a list of subscribers
        ClientListMessage message = new ClientListMessage( usernameToClientId.keySet() );

        AsyncMessage flexMessage = createAsyncMessage( message );
              
        sendMessage( flexMessage, usernameToClientId.keySet().toArray( new String[]{} ) );   	
    }
    
    private String generateMsgId(  )
    {
    	return Long.toString( (new Date()).getTime() );
    }
    
    private void sendMessage( Message message, String... userIds )
    {
    	TreeSet<Object> set = new TreeSet<Object>( );
    	for( Object userId : userIds )
    	{
    		set.add( usernameToClientId.get( userId ) );
    	}
    	sendMessage( message, set );        	
    }
    
//    private void sendMessage( Message message, Collection<? extends Object> clientIds)
//    {        
//    	TreeSet<Object> set = new TreeSet<Object>( clientIds );
//    	sendMessage( message, set );    
//    }    
    
    private void sendMessage( Message message, Set<Object> clientIds)
    {        
    	MessageService msgService = (MessageService) getDestination().getService();    	
        msgService.pushMessageToClients( clientIds, message, false );
    }
    
    private void subscribeUser( String username, String clientId )
    {
		System.out.println( ">>>> subscribeUser executed by " + 
				Thread.currentThread().getName() + " for clientId " + clientId ); 
    	
    	
		// we don't want null logins
		if( username == null || clientId == null ) return;
		
		if( usernameToClientId.containsKey( username ) )
		{
			System.out.println( ">>>> clients logged out or disconnected without properly unsubscribing to" +
					"the service" );			
			// clients logged out or disconnected without properly unsubscribing to
			// the service.
			return;
		}
		
		// new client
		//messageClient.addMessageClientDestroyedListener( this );		
		usernameToClientId.put( username, clientId );	
    }
    
    private void unsubsribeUser( String username, String clientId )
    {
		System.out.println( ">>>> unsubsribeUser executed by " + 
				Thread.currentThread().getName() + " for clientId " + clientId );     	
    	
		if( usernameToClientId.containsKey( username ) )
		{
			usernameToClientId.remove( username );
		}	    	
    }
    
    private String getUsername()
    {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
  		if( auth != null )
  		{
  			return auth.getName();
  		}		

  		throw new IllegalStateException( "This session hasn't been authenticated." );
    }
	
//	/**
//	 * Signals the subscription manager has completely finished its work.
//	 */
//	@Override
//	public void messageClientCreated(MessageClient messageClient) 
//	{
//		// we can only send out the subscription list when the subscription manager 
//		// is done.  otherwise its record is 
//		//sendSubscriptionList( );
//		
//		messageClient.addMessageClientDestroyedListener( this );
//	}
//
//	@Override
//	public void messageClientDestroyed(MessageClient messageClient) 
//	{
//		//sendSubscriptionList( );			
//	}
		    
    /**
     * Controls whether the manage() function would be called.
     *
     * @return <code>true</code>.
     */
    @Override
    public boolean handlesSubscriptions()
    {
        return true;
    }

}

