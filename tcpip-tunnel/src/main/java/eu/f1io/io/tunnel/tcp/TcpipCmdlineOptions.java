package eu.f1io.io.tunnel.tcp;

import org.kohsuke.args4j.Option;

public class TcpipCmdlineOptions {

	@Option(name="-host", usage="interface to listen for connections, by default all") public String hostName;
	@Option(name="-port", usage="port to listen for connections, by default 8080") public int port = 8080;
	
	@Option(name="-usage", aliases={"-help"}, usage="prints this help") public boolean showUsage = false;

}
