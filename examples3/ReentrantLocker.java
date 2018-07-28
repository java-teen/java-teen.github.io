package examples3;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantLocker {
	public static volatile int	READERS = 3;
	public static volatile int	value = -1;
	
	public static void main(String[] args) {
		final ReentrantReadWriteLock	lock = new ReentrantReadWriteLock();	// <1>
		final Runnable			reader = new Runnable(){
									@Override
									public void run() {
										for (int index = 0; index < 10; index++) {
											lock.readLock().lock();				// <2>
											System.err.println("Thread ["+Thread.currentThread().getName()+"] enter to read "+value);
											try{Thread.sleep(100);
											} catch (InterruptedException e) {
											}
											System.err.println("Thread ["+Thread.currentThread().getName()+"] exit read "+value);
											lock.readLock().unlock();			// <3>
											try{Thread.sleep(100);
											} catch (InterruptedException e) {
											}
										}
									}
								};
		final Runnable			writer = new Runnable(){
									@Override
									public void run() {
										for (int index = 0; index < 5; index++) {
											lock.writeLock().lock();	// <4>
											System.err.println("Thread ["+Thread.currentThread().getName()+"] enter to increment "+value);
											value++;
											try{Thread.sleep(100);
											} catch (InterruptedException e) {
											}
											System.err.println("Thread ["+Thread.currentThread().getName()+"] exit incrementing "+value);
											lock.writeLock().unlock();	// <5>
											try{Thread.sleep(200);
											} catch (InterruptedException e) {
											}
										}
									}
								};
		for(int index = 0; index < READERS; index++) {
			new Thread(reader).start();
		}
		new Thread(writer).start();
	}

}
