package test.messages;

import java.util.Collection;

public class ClientListMessage {

	public Collection<String> clientIds;
	
	public ClientListMessage( Collection<String> ids )
	{
		clientIds = ids;
	}
}
