package examples3;

import java.util.concurrent.Semaphore;

public class SemaphoreSender {
	public static final int					THREAD_COUNT = 10;
	public static final int					MAX_VALUE = 20;
	public static final SemaphoreSender		instance = new SemaphoreSender();  

	private final Semaphore		await = new Semaphore(0), sent = new Semaphore(0);	// <1> 
	private volatile int		value = -1;

	public static void main(String[] args) throws InterruptedException {
		final Runnable	getter = new Runnable(){	// <2>
			@Override
			public void run() {
				int		value;
				
				System.err.println("Getter ["+Thread.currentThread().getName()+"] started...");
				try{while ((value = instance.get()) < MAX_VALUE) {
						System.err.println("Getter ["+Thread.currentThread().getName()+"] : "+value);
					}
				} catch (InterruptedException e) {
				}
				System.err.println("Getter ["+Thread.currentThread().getName()+"] ended");
			}
		};
		
		for (int index = 0; index < THREAD_COUNT; index++) {	// <3>
			new Thread(getter).start();
		}
		
		System.err.println("Start sender...");					// <4>
		for (int index = 0; index < MAX_VALUE; index++) {
			instance.send(index);
		}
		
		for (int index = 0; index < THREAD_COUNT; index++) {	// <5>
			instance.send(MAX_VALUE);
		}
		System.err.println("End sender.");
	}

	void send(int value) throws InterruptedException {
		this.value = value;			// <6>
		sent.release();				// <7>
		await.acquire();			// <8>
	}

	synchronized int get() throws InterruptedException {	// <9>
		final int	value;
		
		sent.acquire();				// <10>
		value = this.value;			// <11>
		await.release();			// <12>
		return value;
	}
	
}
