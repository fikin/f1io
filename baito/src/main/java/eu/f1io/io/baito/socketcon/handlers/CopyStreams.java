package eu.f1io.io.baito.socketcon.handlers;

import eu.f1io.io.baito.socketcon.SocketConnectionContext;
import eu.f1io.io.baito.socketcon.SocketConnectionHandler;
import eu.f1io.thread.ThreadProvider;

public class CopyStreams implements SocketConnectionHandler {

	protected final ThreadProvider threadProvider;
	
	public CopyStreams(ThreadProvider threadProvider2) {
		this.threadProvider = threadProvider2;
	}

	@Override
	public void handle(SocketConnectionContext cntx) {
		// TODO Auto-generated method stub

	}

}
