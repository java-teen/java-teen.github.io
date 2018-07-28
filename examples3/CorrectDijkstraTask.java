package examples3;

import java.util.concurrent.CountDownLatch;

public class CorrectDijkstraTask implements Runnable {
	public static final int			LOOPS = 100;
	public static Object[]			sticks = new Object[]{new Object(), new Object(), new Object(), new Object(), new Object()};
	public static CountDownLatch	start = new CountDownLatch(1);  

	private final int				from;
	
	public CorrectDijkstraTask(final int from) {
		this.from = from;
	}
	
	@Override
	public void run() {
		try{start.await();
			for (int loop = 0; loop < LOOPS; loop++) {
				if (from == sticks.length-1) {	// <1>
					synchronized(sticks[(from + 1)% sticks.length]) {	// <2>
						synchronized(sticks[from % sticks.length]) {
							System.err.println("Thread ["+Thread.currentThread().getName()+"] eating");
							Thread.sleep(100);
						}
					}
				}
				else {
					synchronized(sticks[from % sticks.length]) {		// <3>
						synchronized(sticks[(from + 1)% sticks.length]) {
							System.err.println("Thread ["+Thread.currentThread().getName()+"] eating");
							Thread.sleep(100);
						}
					}
				}
				System.err.println("Thread ["+Thread.currentThread().getName()+"] thinking");
				Thread.sleep(100);
			}
		} catch (InterruptedException e) {
		}
	}

	public static void main(String[] args) {
		for (int index = 0; index < sticks.length; index++) {
			new Thread(new CorrectDijkstraTask(index)).start();
		}
		start.countDown();
	}
}
