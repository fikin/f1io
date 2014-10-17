package eu.f1io.io.tunnel.tcp;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionHandlerFilter;
import org.kohsuke.args4j.ParserProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dagger.ObjectGraph;

public class TcpipTunnelMain {

	protected final Logger log = LoggerFactory.getLogger(TcpipTunnelMain.class);

	protected final TcpipCmdlineOptions cmdlineOpts = new TcpipCmdlineOptions();
	
	protected TcpipTunnelListener listener;
	
	public static void main(String[] args) {
		final int ret = new TcpipTunnelMain()._main(args);
		System.exit(ret);
	}
	
	protected int _main(String[] args) {
		final CmdLineParser parser = prepareCmdlineParser(cmdlineOpts);
		try {
			parser.parseArgument(args);
		} catch (CmdLineException e) {
			log.error(e.toString());
			printUsage(parser);
			return 1;
		}
		handleCmdlineOptions(parser);
		return 0;
	}

	protected void handleCmdlineOptions(CmdLineParser parser) {
		if ( cmdlineOpts.showUsage )
			printUsage(parser);
		else
			startListener();
	}

	protected void printUsage(CmdLineParser parser) {
		final Writer writer = new OutputStreamWriter(System.out);
		parser.printUsage(writer, null, OptionHandlerFilter.ALL);
		try {
			writer.flush();
		} catch (IOException e) {}
	}

	protected void startListener() {
		final ObjectGraph graph = constructDIGraph(cmdlineOpts);
		listener = graph.get(TcpipTunnelListener.class);
		registerStopHandler(listener);
		listener.listen();
	}
	
	protected void stopListener() {
        log.info("User requested listener shutdown, initiating sequence ...");
        listener.stop();
	}

	protected void registerStopHandler(final TcpipTunnelListener l) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override public void run() {
				stopListener();
			}
		});
	}

	protected CmdLineParser prepareCmdlineParser(TcpipCmdlineOptions cmdlineOpts2) {
		ParserProperties props = ParserProperties.defaults();
		props.withUsageWidth(80);
		final CmdLineParser parser = new CmdLineParser(cmdlineOpts2, props);
		return parser;
	}

	protected ObjectGraph constructDIGraph(TcpipCmdlineOptions cmdlineOpts2) {
		final TcpipTunnelListenerFactory fact = new TcpipTunnelListenerFactory(cmdlineOpts2);
		final ObjectGraph objectGraph = ObjectGraph.create(fact);
		return objectGraph;
	}
	
}
