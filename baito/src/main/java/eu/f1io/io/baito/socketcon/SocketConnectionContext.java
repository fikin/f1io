package eu.f1io.io.baito.socketcon;

import eu.f1io.io.baito.BaitoIOException;

public interface SocketConnectionContext {

	String geRemoveHost();

	int getRemotePort();

	void closeSocket() throws BaitoIOException;

}
