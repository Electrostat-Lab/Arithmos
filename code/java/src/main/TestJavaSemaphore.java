package main;

import utils.process.Semaphore;
import utils.process.Semaphore.Mutex;
import java.lang.Thread;
import java.util.logging.Logger;

/**
 * Tests java binary semaphores.
 * 
 * @author pavl_g.
 */
public class TestJavaSemaphore {

	public enum State {
		TOGGLED,
	}
	protected static final Semaphore.Mutex MUTEX = Mutex.SIMPLE_MUTEX;
	protected static final Semaphore SEMAPHORE = Semaphore.build(MUTEX);
	private static final Logger logger = Logger.getLogger(TestJavaSemaphore.class.getName());

	public static void execute() {
		logger.info("Started Java Semaphore test");

		new Initializer().start();
		new Finalizier().start();
	}

	private static class Initializer extends Thread {
		@Override
		public void run() {
			logger.info("Entered the initializer");

			initMutexWithLockData();

			SEMAPHORE.lock(Initializer.this);
			try{
				Thread.sleep(1000);
			} catch(InterruptedException exception) {
				exception.printStackTrace();
			}
			SEMAPHORE.unlock(Initializer.this);

			logger.info("Finished the initializer");
		}
		private void initMutexWithLockData() {
			MUTEX.setLockData(TestJavaSemaphore.State.TOGGLED);
			MUTEX.setMonitorObject(Initializer.this);
		}
	}

	
	private static class Finalizier extends Thread {
		@Override
		public void run() {
			SEMAPHORE.waitForUnlock();

			logger.info("Entered the finalizer");

			logger.info("Finished the finalizer");
		}
	}
}

