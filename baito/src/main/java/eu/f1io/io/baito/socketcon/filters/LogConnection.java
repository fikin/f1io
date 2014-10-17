package eu.f1io.io.baito.socketcon.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.f1io.io.baito.socketcon.SocketConnectionContext;
import eu.f1io.io.baito.socketcon.SocketConnectionFilter;
import eu.f1io.io.baito.socketcon.SocketConnectionHandler;

public class LogConnection extends SocketConnectionFilter {

	protected final Logger log = LoggerFactory.getLogger(LogConnection.class);
	
	public LogConnection(SocketConnectionHandler nextHandler) {
		super(nextHandler);
	}

	public void handle(SocketConnectionContext cntx) {
		log.info("New connection from {}:{}", cntx.geRemoteHost(), cntx.getRemotePort());
		delegateToNext(cntx);
	}

}
