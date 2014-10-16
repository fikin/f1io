package eu.f1io.io.baito.http;

public abstract class HttpRequestFilterFilter 
	extends HttpRequestFilterChainLink 
	implements HttpRequestHandler 
{

	public HttpRequestFilterFilter(HttpRequestHandler next) {
		super(next);
	}

}
