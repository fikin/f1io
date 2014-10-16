package eu.f1io.io.baito.socketcon.filters.ssl;

import javax.net.ssl.SSLEngine;

public interface SSLEngineFactory {
	SSLEngine newEngine();
}