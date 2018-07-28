package examples3;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ThreadForkJoinPool extends RecursiveTask<Integer> {
	private static final int[]	DATA = new int[]{1,2,3,4,5,6};

	private boolean	join;
	private int from, to;
	ThreadForkJoinPool(int from, int to) { // <1>
		this.join = false;	this.from = from;	this.to = to;		
	}
	
	ThreadForkJoinPool() { // <2>
		this.join = true;
	}
	
	public static void main(String[] args) {
		final ForkJoinPool	pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors()); // <3>
		
		System.err.println("Result="+pool.invoke(new ThreadForkJoinPool())); // <4>
	}

	@Override
	protected Integer compute() {
		if (join) {  // <5>
//			return new ThreadForkJoinPool(0,5).compute() + new ThreadForkJoinPool(1,6).fork().join();
			final ThreadForkJoinPool task1 = new ThreadForkJoinPool(0,5), task2 = new ThreadForkJoinPool(1,6);
			
			task1.fork();	task2.fork();
			return task1.join() + task2.join(); 
//			return new ThreadForkJoinPool(0,5).fork().join() + new ThreadForkJoinPool(1,6).fork().join();
		}
		else {  // <6>
			int		value = 0;
			for (int index = from; index < to; index += 2) {
				value += DATA[index];
			}
			return value;
		}
	}
}
