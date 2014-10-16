package eu.f1io.io.baito.socketcon.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.f1io.io.baito.BaitoIOException;
import eu.f1io.io.baito.socketcon.SocketConnectionContext;
import eu.f1io.io.baito.socketcon.SocketConnectionFilter;
import eu.f1io.io.baito.socketcon.SocketConnectionHandler;

public class CloseSocket extends SocketConnectionFilter {

	protected final Logger log = LoggerFactory.getLogger(CloseSocket.class);
	
	public CloseSocket(SocketConnectionHandler nextHandler) {
		super(nextHandler);
	}

	public void handle(SocketConnectionContext cntx) {
		try {
			delegateToNext(cntx);
		} finally {
			ignorantClose(cntx);
		}
	}

	protected void ignorantClose(SocketConnectionContext cntx) {
		try {
			cntx.closeSocket();
		} catch (BaitoIOException e) {
			// ignore it
		}		
	}

}
