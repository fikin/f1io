package eu.f1io.io.baito.socketcon.listeners;

import eu.f1io.io.baito.socketcon.SocketConnectionFilterChainLink;
import eu.f1io.io.baito.socketcon.SocketConnectionHandler;

public class SocketListener extends SocketConnectionFilterChainLink implements ListenerInterface {

	public SocketListener(SocketConnectionHandler nextHandler) {
		super(nextHandler);
	}

	@Override
	public void listen() {
		// TODO
	}
	
	@Override
	public void interrupt() {
		// TODO
	}
}
