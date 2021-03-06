package eu.f1io.io.baito.stream;

import eu.f1io.io.baito.BaitoBuffer;
import eu.f1io.io.baito.BaitoIOException;

public abstract class BaitoReaderWriterFilter implements BaitoReaderWriter {

	protected final BaitoReaderWriter next;
	
	public BaitoReaderWriterFilter(BaitoReaderWriter next2) {
		this.next = next2;
	}
	
	public BaitoReaderWriter getNext() {
		return next;
	}
	
	public void read(BaitoBuffer buf) throws BaitoIOException {
		getNext().read(buf);
	}

	public void write(BaitoBuffer buf) throws BaitoIOException {
		getNext().write(buf);
	}

}
