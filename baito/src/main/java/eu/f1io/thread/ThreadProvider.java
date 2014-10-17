package eu.f1io.thread;


public interface ThreadProvider {

	void startInOwnThread(Runnable runIt);

}
