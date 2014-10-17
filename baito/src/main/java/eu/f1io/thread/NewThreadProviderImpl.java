package eu.f1io.thread;


public class NewThreadProviderImpl implements ThreadProvider {

	@Override
	public void startInOwnThread(Runnable runIt) {
		final Thread th = new Thread(runIt, "Runner for "+runIt.hashCode());
		th.start();
	}

}
