package eu.f1io.io.baito.socketcon.filters.ssl;

import java.nio.ByteBuffer;

import javax.net.ssl.SSLEngine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.f1io.io.baito.socketcon.SocketConnectionContext;
import eu.f1io.io.baito.socketcon.SocketConnectionFilter;
import eu.f1io.io.baito.socketcon.SocketConnectionHandler;

public class CommunicateViaSSL extends SocketConnectionFilter {

	protected final Logger log = LoggerFactory.getLogger(CommunicateViaSSL.class);
	
	protected final SSLEngineFactory sslFactory;
	
	public CommunicateViaSSL(SocketConnectionHandler nextHandler, 
			SSLEngineFactory sslFactory2) 
	{
		super(nextHandler);
		this.sslFactory = sslFactory2;
	}

	public void handle(SocketConnectionContext cntx) {
		SSLContextData dt = constructNewData();
		setupSsl(cntx,dt);
		delegateToNext(cntx);
		tearDownSsl(cntx,dt);
	}

	protected SSLContextData constructNewData() {
		return new SSLContextData(
				sslFactory.newEngine(),
				ByteBuffer.allocate(100),
				ByteBuffer.allocate(100)
				);
	}

	protected void setupSsl(SocketConnectionContext cntx, SSLContextData dt) {
		
	}

	protected void tearDownSsl(SocketConnectionContext cntx, SSLContextData dt) {
		// TODO Auto-generated method stub
		
	}

	public static class SSLContextData {
		protected final SSLEngine sslEngine;
		protected final ByteBuffer inBuf;
		protected final ByteBuffer outBuf;
		public SSLContextData(SSLEngine sslEngine, ByteBuffer inBuf, ByteBuffer outBuf) {
			this.sslEngine = sslEngine;
			this.inBuf = inBuf;
			this.outBuf = outBuf;
		}
	}
}
