package examples3;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class ThreadPoolStartStop {
	public static void main(String[] args) throws InterruptedException {
		final ExecutorService	pool = Executors.newSingleThreadExecutor(); // <1>
		
		// Аналог Thread.start()
		final Future<String>	ctrl = pool.submit(new Callable<String>(){	// <2> 
										@Override	// Аналог Runnable.run()							
										public String call() throws Exception {
											return "TEST STRING";
										}}
									);
		Thread.sleep(1000);		// <3>
		try{System.err.println("Result: "+ctrl.get());	// <4> Аналог Thread.join()
		} catch (ExecutionException exc) {	// <5>
			System.err.println("Error execution thread: "+exc.getMessage());
		}
	}
}
