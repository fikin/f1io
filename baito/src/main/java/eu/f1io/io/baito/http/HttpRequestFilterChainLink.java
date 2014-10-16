package eu.f1io.io.baito.http;

public class HttpRequestFilterChainLink {

	protected final HttpRequestHandler next;
	
	public HttpRequestFilterChainLink(HttpRequestHandler next) {
		this.next = next;
	}

	public HttpRequestHandler getNext() {
		return next;
	}
}
