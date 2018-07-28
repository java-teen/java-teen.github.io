package examples3;

public class NotifyWaitSender {
	public static final int					THREAD_COUNT = 10;
	public static final int					MAX_VALUE = 20;
	public static final NotifyWaitSender	instance = new NotifyWaitSender();  

	private final Object		sendSync = new Object(), recvSync = new Object();	// <1>
	private int					value = 0;
	private boolean	awaited = false;
	
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
		synchronized(sendSync) {	// <6>
			while(!awaited) {		// <7>
				sendSync.wait();	// <8>
			}
			awaited = false;		// <9>
			synchronized(recvSync) {	// <10>
				this.value = value;		// <11>
				recvSync.notify();		// <12>
			}
		}
	}

	synchronized int get() throws InterruptedException {	// <13>
		synchronized(recvSync) {	// <14>
			synchronized(sendSync) {	// <15>
				if (!awaited) {			// <16>
					awaited = true;		// <17>
					sendSync.notify();	// <18>
				}
			}
			recvSync.wait();			// <19>
			return this.value;			// <20>
		}
	}
}
