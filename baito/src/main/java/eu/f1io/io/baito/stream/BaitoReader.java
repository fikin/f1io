package eu.f1io.io.baito.stream;

import eu.f1io.io.baito.BaitoBuffer;
import eu.f1io.io.baito.BaitoIOException;

public interface BaitoReader {

	void read(BaitoBuffer buf) throws BaitoIOException;
}
