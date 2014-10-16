package eu.f1io.io.baito.ssl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLEngineResult.HandshakeStatus;
import javax.net.ssl.SSLEngineResult.Status;
import javax.net.ssl.SSLException;

import eu.f1io.io.baito.BaitoBuffer;
import eu.f1io.io.baito.BaitoIOException;
import eu.f1io.io.baito.stream.BaitoReaderWriter;
import eu.f1io.io.baito.stream.BaitoReaderWriterFilter;

public class ServerSSLBaitoReaderWriter extends BaitoReaderWriterFilter {

	protected final SSLEngine sslEngine;
	protected final BaitoBuffer readerBuffer;
	protected final BaitoBuffer writerBuffer;
	
	public ServerSSLBaitoReaderWriter(BaitoReaderWriter next2, SSLEngine sslEngine2,
			BaitoBuffer readerBuf2, BaitoBuffer writerBuf2) 
	throws BaitoIOException
	{
		super(next2);
		this.sslEngine = sslEngine2;
		this.readerBuffer = readerBuf2;
		this.writerBuffer = writerBuf2;
		sslEngineInitializeInMode(sslEngine);
		sslEngineBeginHandshake(sslEngine2);
	}
	
	protected void sslEngineInitializeInMode(SSLEngine sslEngine2) throws BaitoIOException {
		sslEngine2.setUseClientMode(false);
	}

	protected void sslEngineBeginHandshake(SSLEngine sslEngine2) {
		try {
			sslEngine2.beginHandshake();
		} catch (SSLException e) {
			throw new BaitoIOException(e);
		}
	}

	public void write(BaitoBuffer buf) throws BaitoIOException {
		// TODO Auto-generated method stub
		
	}

	public void read(BaitoBuffer buf) throws BaitoIOException {
		super.getNext().read(readerBuffer);
		try {
			final SSLEngineResult res = sslEngine.unwrap(buf.getByteBuffer(), readerBuffer.getByteBuffer());
			
			if ( HandshakeStatus.FINISHED == res.getHandshakeStatus() )
				;
			else if ( HandshakeStatus.NEED_TASK == res.getHandshakeStatus() )
				doSslNeedTask(sslEngine);
			else if ( HandshakeStatus.NEED_UNWRAP == res.getHandshakeStatus() )
				;
			else if ( HandshakeStatus.NEED_WRAP == res.getHandshakeStatus() )
				;
			else if ( HandshakeStatus.NOT_HANDSHAKING == res.getHandshakeStatus() )
				;
			
			if ( Status.BUFFER_OVERFLOW == res.getStatus() )
				;
			else if ( Status.BUFFER_UNDERFLOW == res.getStatus() )
				;
			else if ( Status.CLOSED == res.getStatus() )
				;
			else if ( Status.OK == res.getStatus() )
				;
			
		} catch (SSLException e) {
			throw new BaitoIOException(e);
		}
	}

	protected void doSslNeedTask(SSLEngine sslEngine2) {
		final ExecutorService runner = Executors.newSingleThreadExecutor();
		while(true) {
			final Runnable task = sslEngine2.getDelegatedTask();
			if ( task == null )
				break;
			runner.execute(task);
		}
	}

}
