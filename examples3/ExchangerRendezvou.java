package examples3;

import java.util.concurrent.Exchanger;

public class ExchangerRendezvou implements Runnable {
	public static final int				THREAD_COUNT = 3;
	public static final int				LOOP_COUNT = 3;
	
	private final int					from;
	private final Exchanger<Integer>	ex;
	
	public ExchangerRendezvou(final int from, final Exchanger<Integer> ex) {	// <1>
		this.from = from;
		this.ex = ex;
	}
	
	public static void main(String[] args) {
		final Exchanger<Integer>	ex = new Exchanger<>();		// <2>
		final Runnable				consumer = new Runnable(){
			@Override
			public void run() {
				for (int index = 0; index < THREAD_COUNT * LOOP_COUNT; index++) {
					try{System.err.println("Received: "+ex.exchange(null));	// <3>
					} catch (InterruptedException e) {
					}
				}
			}
		};
		for (int index = 0; index < THREAD_COUNT; index++) {	// <4>
			new Thread(new ExchangerRendezvou(10*index,ex)).start();
		}
		new Thread(consumer).start();	// <5>
	}

	@Override
	public void run() {
		for (int index = from; index < from + LOOP_COUNT; index++) {
			synchronized(ex) {		// <6>
				try{ex.exchange(index);	// <7>
					Thread.currentThread().sleep(100);
				} catch (InterruptedException e) {
				}
			}
		}
	}
}
