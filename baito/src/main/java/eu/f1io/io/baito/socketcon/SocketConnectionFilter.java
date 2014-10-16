package eu.f1io.io.baito.socketcon;

public abstract class SocketConnectionFilter 
	extends SocketConnectionFilterChainLink 
	implements SocketConnectionHandler 
{

	public SocketConnectionFilter(SocketConnectionHandler nextHandler) {
		super(nextHandler);
	}

	protected void delegateToNext(SocketConnectionContext cntx) {
		getNext().handle(cntx);
	}
	
}
