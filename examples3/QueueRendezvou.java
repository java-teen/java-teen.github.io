package examples3;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class QueueRendezvou implements Runnable {
	public static final int					THREAD_COUNT = 3;
	public static final int					LOOP_COUNT = 3;
	
	private final int						from;
	private final BlockingQueue<Integer>	que;
	
	public QueueRendezvou(final int from, final BlockingQueue<Integer> que) {	// <1>
		this.from = from;
		this.que = que;
	}


	public static void main(String[] args) {
		final BlockingQueue<Integer>	que = new ArrayBlockingQueue<>(10);		// <2>
		final Runnable					consumer = new Runnable(){
			@Override
			public void run() {
				for (int index = 0; index < THREAD_COUNT * LOOP_COUNT; index++) {
					try{System.err.println("Received: "+que.take());	// <3>
					} catch (InterruptedException e) {
					}
				}
			}
		};
		for (int index = 0; index < THREAD_COUNT; index++) {	// <4>
			new Thread(new QueueRendezvou(10*index,que)).start();
		}
		new Thread(consumer).start();	// <5>
	}

	@Override
	public void run() {
		for (int index = from; index < from + LOOP_COUNT; index++) {
			try{que.put(index);	// <6>
				Thread.currentThread().sleep(100);
			} catch (InterruptedException e) {
			}
		}
	}
}
