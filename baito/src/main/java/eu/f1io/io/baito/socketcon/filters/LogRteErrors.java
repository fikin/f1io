package eu.f1io.io.baito.socketcon.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.f1io.io.baito.socketcon.SocketConnectionContext;
import eu.f1io.io.baito.socketcon.SocketConnectionFilter;
import eu.f1io.io.baito.socketcon.SocketConnectionHandler;

public class LogRteErrors extends SocketConnectionFilter {

	protected final Logger log = LoggerFactory.getLogger(LogRteErrors.class);
	
	public LogRteErrors(SocketConnectionHandler nextHandler) {
		super(nextHandler);
	}

	public void handle(SocketConnectionContext cntx) {
		try {
			delegateToNext(cntx);
		} catch (Throwable e) {
			log.error("Failure processing request from {}:[} : {}", cntx.getRemotePort(), cntx.geRemoveHost(), e);
		}
	}

}
