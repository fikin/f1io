package eu.f1io.io.tunnel.tcp;

import java.net.InetSocketAddress;

import dagger.Module;
import dagger.Provides;
import eu.f1io.io.baito.socketcon.SocketConnectionHandler;
import eu.f1io.io.baito.socketcon.filters.CloseSocket;
import eu.f1io.io.baito.socketcon.filters.LogConnection;
import eu.f1io.io.baito.socketcon.filters.LogRteErrors;
import eu.f1io.io.baito.socketcon.filters.ThreadedHandler;
import eu.f1io.io.baito.socketcon.handlers.CopyStreams;
import eu.f1io.io.baito.socketcon.listeners.ListenerInterface;
import eu.f1io.io.baito.socketcon.listeners.SocketListener;
import eu.f1io.thread.NewThreadProviderImpl;
import eu.f1io.thread.ThreadProvider;

@Module(
	injects = TcpipTunnelListener.class
)
public class TcpipTunnelListenerFactory {

	protected TcpipCmdlineOptions cmdlineOpts;
	
	public TcpipTunnelListenerFactory(TcpipCmdlineOptions cmdlineOpts2) {
		this.cmdlineOpts = cmdlineOpts2;
	}

	protected String getListenAddress() {
		return cmdlineOpts.hostName;
	}

	protected int getListenPort() {
		return cmdlineOpts.port;
	}
	
	@Provides
	public ThreadProvider createThreadProvider() {
		return new NewThreadProviderImpl();
	}
	
	@Provides
	public SocketConnectionHandler createSocketConnectionHandlingPipe(
			ThreadProvider threadProvider
			) 
	{
		return
			new ThreadedHandler(
				threadProvider,
				new CloseSocket(
					new LogConnection(
						new LogRteErrors(
							new CopyStreams(threadProvider)
						)
					)
				)
			);
	}

	@Provides
	public InetSocketAddress mainListenAddress() {
		if ( getListenAddress() == null || getListenAddress().isEmpty() )
			return new InetSocketAddress(getListenPort());
		else
			return new InetSocketAddress(getListenAddress(), getListenPort());
	}
	
	@Provides
	public ListenerInterface createListener(
			InetSocketAddress listenAddress, 
			SocketConnectionHandler socketPipe
			) 
	{
		final SocketListener listener = new SocketListener(listenAddress, socketPipe);
		return listener;
	}

}
