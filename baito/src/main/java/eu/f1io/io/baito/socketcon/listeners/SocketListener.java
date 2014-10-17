package eu.f1io.io.baito.socketcon.listeners;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.f1io.io.baito.BaitoIOException;
import eu.f1io.io.baito.socketcon.SocketConnectionContext;
import eu.f1io.io.baito.socketcon.SocketConnectionContextBean;
import eu.f1io.io.baito.socketcon.SocketConnectionFilterChainLink;
import eu.f1io.io.baito.socketcon.SocketConnectionHandler;

public class SocketListener extends SocketConnectionFilterChainLink implements ListenerInterface {

	protected final Logger log = LoggerFactory.getLogger(SocketListener.class);
	
	protected final InetSocketAddress listenTo;
	protected boolean shouldExit;
	protected ServerSocket serverSocket;

	public SocketListener(InetSocketAddress listenTo2, 
			SocketConnectionHandler nextHandler) 
	{
		super(nextHandler);
		this.listenTo = listenTo2;
	}

	@Override
	public void setReadyToExit() {
		log.debug("user requested listener to exit.");
		shouldExit = true;
		if ( false == serverSocket.isClosed() ) {
			try {
				serverSocket.close();
			} catch (IOException e) {}
		}
	}

	@Override
	public void listen() {
		log.info("Starting listening for tcp/ip connections on {} ...", listenTo);
		shouldExit = false;
		serverSocket = setupServerSocket();
		listensForNewConnections(serverSocket);
		log.info("Stoped listening for tcp/ip connections on {}.", listenTo);
	}

	protected void listensForNewConnections(final ServerSocket ss) {
		while( false == shouldExit ) {
			try {
				log.debug("accepting connections ...");
				final Socket connection = ss.accept();
				handleNewConnection(connection);
			} catch (IOException e) {
				if ( false == shouldExit ) // ignore error if it is called by #setReadyToExit
					log.error("Error while accepting socket connection", e);
			}
		}
	}

	protected void handleNewConnection(final Socket connection) {
		final SocketConnectionContext cntx = constructContext(connection);
		getNext().handle(cntx);
	}

	protected ServerSocket setupServerSocket() {
		try {
			final ServerSocket ss = new ServerSocket();
			ss.bind(listenTo);
			return ss;
		} catch (IOException e) {
			throw new BaitoIOException(e);
		}
	}
	
	protected SocketConnectionContext constructContext(final Socket connection) {
		return new SocketConnectionContextBean(connection);
	}

}
