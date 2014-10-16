package eu.f1io.io.baito.socketcon;

public abstract class SocketConnectionFilterChainLink {

	protected final SocketConnectionHandler next;
	
	public SocketConnectionFilterChainLink(SocketConnectionHandler nextHandler) {
		this.next = nextHandler;
	}

	public SocketConnectionHandler getNext() {
		return next;
	}
	
}
