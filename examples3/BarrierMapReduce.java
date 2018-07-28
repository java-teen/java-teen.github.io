package examples3;

import java.util.concurrent.CountDownLatch;

public class BarrierMapReduce {
	public static final int		THREAD_AMOUNT = 10; 

	public static volatile int	value = -1;	// <1>
	
	public static void main(String[] args) throws InterruptedException {
		final CountDownLatch	cdlStart = new CountDownLatch(1), cdlEnd = new CountDownLatch(THREAD_AMOUNT); // <2>
		final Runnable			exec = new Runnable(){	// <3>
			@Override
			public void run() {
				try{cdlStart.await();	// <4>
					System.err.println("Thread ["+Thread.currentThread().getName()+"] : "+value);	// <5>
				} catch (Exception exc) {
					exc.printStackTrace();
				} finally {
					cdlEnd.countDown();	// <6>
				}
			}
		};
		for (int index = 0; index < THREAD_AMOUNT; index++) { // <7>
			new Thread(exec).start();
		}
		value = 999;	// <8>
		cdlStart.countDown(); // <9>
		System.err.println("All threads starting...");
		cdlEnd.await();	// <10>
		System.err.println("All threads ended");
	}
}
