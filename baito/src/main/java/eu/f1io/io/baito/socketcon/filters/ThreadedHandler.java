package eu.f1io.io.baito.socketcon.filters;

import eu.f1io.io.baito.socketcon.SocketConnectionContext;
import eu.f1io.io.baito.socketcon.SocketConnectionFilter;
import eu.f1io.io.baito.socketcon.SocketConnectionHandler;
import eu.f1io.thread.ThreadProvider;

public class ThreadedHandler extends SocketConnectionFilter {

	protected final ThreadProvider threadProvider;
	
	public ThreadedHandler(ThreadProvider threadProvider2, 
			SocketConnectionHandler nextHandler) 
	{
		super(nextHandler);
		this.threadProvider = threadProvider2;
	}

	@Override
	public void handle(final SocketConnectionContext cntx) {
		final Runnable r = new Runnable() {
			@Override public void run() {
				getNext().handle(cntx);
			}
		};
		threadProvider.startInOwnThread(r);
	}

}
