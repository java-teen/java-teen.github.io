package examples3;

import java.util.concurrent.CountDownLatch;

public class DijkstraTask implements Runnable {
	public static final int			LOOPS = 100;	// <1>
	public static Object[]			sticks = new Object[]{new Object(), new Object(), new Object(), new Object(), new Object()};
	public static CountDownLatch	start = new CountDownLatch(1);  

	private final int				from;
	
	public DijkstraTask(final int from) {
		this.from = from;
	}
	
	@Override
	public void run() {
		try{start.await();			// <2>
			for (int loop = 0; loop < LOOPS; loop++) {
				synchronized(sticks[from % sticks.length]) {	// <3>
					synchronized(sticks[(from + 1)% sticks.length]) {
						System.err.println("Thread ["+Thread.currentThread().getName()+"] eating");
						Thread.sleep(100);		// <4>
					}
				}
				System.err.println("Thread ["+Thread.currentThread().getName()+"] thinking");
				Thread.sleep(100);				// <5>
			}
		} catch (InterruptedException e) {
			
		}
	}

	public static void main(String[] args) {
		for (int index = 0; index < sticks.length; index++) {	// <6>
			new Thread(new DijkstraTask(index)).start();
		}
		start.countDown();		// <7>
	}
}
