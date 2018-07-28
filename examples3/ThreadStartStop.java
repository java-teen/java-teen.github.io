package examples3;

public class ThreadStartStop {
	public static void main(String[] args) throws InterruptedException {
		final Thread	t1 = new Thread(new Runnable(){		// <1>
									@Override
									public void run() {
										System.err.println("Thread ["+Thread.currentThread().getName()+"] execution");
									}
							}
						);
		t1.start();	// <2>
		Thread.sleep(500);
		t1.join();	// <3>
		System.err.println();

		final Thread	t2 = new Thread(new Runnable(){		// <4>
									@Override
									public void run() {
										System.err.println("Thread ["+Thread.currentThread().getName()+"] started");
										while(!Thread.interrupted()) {				// <5>
											System.err.println("Thread ["+Thread.currentThread().getName()+"]...");
											try{Thread.sleep(100);					// <6>
											} catch (InterruptedException exc) {	// <7>
												System.err.println("Thread ["+Thread.currentThread().getName()+"] interrupted");
												Thread.currentThread().interrupt();
												break;
											}
										}
										System.err.println("Thread ["+Thread.currentThread().getName()+"] ended");
									}
							}
						);
		t2.start();			// <8>
		Thread.sleep(500);
		t2.interrupt();		// <9>
		t2.join();			// <10>
		System.err.println();

		final Thread	t3 = new Thread(new Runnable(){		// <11>
									@Override
									public void run() {
										try{System.err.println("Thread ["+Thread.currentThread().getName()+"] started");
											while(!Thread.interrupted()) {
												System.err.println("Thread ["+Thread.currentThread().getName()+"]...");
												try{Thread.sleep(100);
												} catch (InterruptedException exc) {
													System.err.println("Thread ["+Thread.currentThread().getName()+"] interrupted");
												}
											}
										} catch (ThreadDeath exc) {	// <12>
											System.err.println("Thread ["+Thread.currentThread().getName()+"] killed");
											throw exc;				// <13>
										} finally {					
											System.err.println("Thread ["+Thread.currentThread().getName()+"] ended");
										}
									}
							}
						);
		t3.start();			// <14>
		Thread.sleep(500);
		t3.stop();			// <15>
		t3.join();			// <16>
	}
}
