package eu.f1io.io.baito.socketcon.handlers;

import eu.f1io.io.baito.socketcon.SocketConnectionContext;
import eu.f1io.io.baito.socketcon.SocketConnectionHandler;

public class HttpRequestHandler implements SocketConnectionHandler {

	protected final HttpRequestHandler httpHandler;
	
	public HttpRequestHandler(HttpRequestHandler httpHandler2) {
		this.httpHandler = httpHandler2;
	}

	@Override
	public void handle(SocketConnectionContext cntx) {
		// TODO Auto-generated method stub
		
	}

}
