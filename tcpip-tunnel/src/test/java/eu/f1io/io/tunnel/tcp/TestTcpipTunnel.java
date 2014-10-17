package eu.f1io.io.tunnel.tcp;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestTcpipTunnel {

	@Test
	public void usage() {
		final int r = new TcpipTunnel()._main(new String[]{ "-usage" });
		assertEquals(0,r);
	}
	
	@Test
	public void help() {
		final int r = new TcpipTunnel()._main(new String[]{ "-help" });
		assertEquals(0,r);
	}
	
	@Test(expected = NullPointerException.class)
	public void nullArgsList() {
		new TcpipTunnel()._main(null);
	}
	
	@Test
	public void start() {
		final TcpipTunnel app = new TcpipTunnel();
		new Thread() {
			@Override public void run() {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				app.stopListener();
			}
		}.start();
		final int r = app._main(new String[]{});
		assertEquals(0,r);
	}
	
}
