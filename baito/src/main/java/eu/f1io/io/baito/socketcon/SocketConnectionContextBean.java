package eu.f1io.io.baito.socketcon;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import eu.f1io.io.baito.BaitoIOException;

public class SocketConnectionContextBean implements SocketConnectionContext {
	protected final Socket connection;

	public SocketConnectionContextBean(Socket connection) {
		this.connection = connection;
	}

	@Override
	public String geRemoteHost() {
		return ((InetSocketAddress)connection.getRemoteSocketAddress()).getHostName();
	}

	@Override
	public int getRemotePort() {
		return ((InetSocketAddress)connection.getRemoteSocketAddress()).getPort();
	}

	@Override
	public void closeSocket() throws BaitoIOException {
		try {
			connection.close();
		} catch (IOException e) {
			throw new BaitoIOException(e);
		}
	}
}