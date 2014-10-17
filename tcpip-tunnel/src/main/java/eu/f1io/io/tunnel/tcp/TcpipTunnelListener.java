package eu.f1io.io.tunnel.tcp;

import javax.inject.Inject;

import eu.f1io.io.baito.BaitoIOException;
import eu.f1io.io.baito.socketcon.listeners.ListenerInterface;

public class TcpipTunnelListener {

	protected Thread th;
	@Inject protected ListenerInterface listener;
	
	public void listen() {
		th = Thread.currentThread();
		listener.listen();
	}

	public void stop() {
		listener.setReadyToExit();
		th.interrupt();
		try {
			th.join();
		} catch (InterruptedException e) {
			throw new BaitoIOException(e);
		}
	}
	
}
