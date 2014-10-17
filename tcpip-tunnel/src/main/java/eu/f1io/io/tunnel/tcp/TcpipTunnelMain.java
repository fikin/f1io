package eu.f1io.io.tunnel.tcp;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.ParserProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.Signal;
import sun.misc.SignalHandler;
import dagger.ObjectGraph;

public class TcpipTunnelMain {

	protected final Logger log = LoggerFactory.getLogger(TcpipTunnelMain.class);

	@Option(name="-host") protected String hostName;
	@Option(name="-port") protected int port = 8080;
	
	@Option(name="-usage", aliases={"-help"}) protected boolean showUsage = false;

	protected TcpipTunnelListener listener;
	
	public static void main(String[] args) {
		final int ret = new TcpipTunnelMain()._main(args);
		System.exit(ret);
	}
	
	protected int _main(String[] args) {
		final CmdLineParser parser = prepareCmdlineParser();
		try {
			parser.parseArgument(args);
		} catch (CmdLineException e) {
			log.error(e.toString());
			parser.printUsage(System.out);
			return 1;
		}
		handleCmdlineOptions(parser);
		return 0;
	}

	protected void handleCmdlineOptions(CmdLineParser parser) {
		if ( showUsage )
			parser.printUsage(System.out);
		else
			startListener();
	}

	protected void startListener() {
		final ObjectGraph graph = constructDIGraph(hostName, port);
		listener = graph.get(TcpipTunnelListener.class);
		registerStopHandler(listener);
		listener.listen();
	}
	
	protected void stopListener() {
        log.info("User requested listener shutdown, initiating sequence ...");
        listener.stop();
	}

	protected void registerStopHandler(final TcpipTunnelListener l) {
		Signal.handle(new Signal("TSTP"), new SignalHandler () {
	        public void handle(Signal sig) {
	        	stopListener();
	        }
	      });
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override public void run() {
				stopListener();
			}
		});
	}

	protected CmdLineParser prepareCmdlineParser() {
		ParserProperties props = ParserProperties.defaults();
		props.withUsageWidth(80);
		final CmdLineParser parser = new CmdLineParser(this, props);
		return parser;
	}

	protected ObjectGraph constructDIGraph(String listenOnInterface2, int listenOnPort2) {
		final TcpipTunnelListenerFactory fact = new TcpipTunnelListenerFactory(listenOnInterface2, listenOnPort2);
		final ObjectGraph objectGraph = ObjectGraph.create(fact);
		return objectGraph;
	}
	
}
