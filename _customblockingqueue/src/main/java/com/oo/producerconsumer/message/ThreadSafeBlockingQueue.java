package com.oo.producerconsumer.message;

import java.util.LinkedList;
import java.util.List;

public class ThreadSafeBlockingQueue <T extends Message> {
	
	/*
	 * A linked list to hold entries
	 */
	private final List<T> queue = new LinkedList<T>();
	
	/*
	 * Maximum number of entries allowed in the list (Size of the queue)
	 */
	private final int limit;
	
	public ThreadSafeBlockingQueue(int limit){
		this.limit = limit;
	}
	
	/*
	 * Thread safe method to add entry into the queue
	 */
	public synchronized void put(T entry) throws InterruptedException {
		
		// wait until space becomes available in queue
		while(queue.size() == limit){
			wait();
		}
		
		notifyAll();
		queue.add(entry);
	}
	
	/*
	 * Thread safe method to fetch entry from the queue
	 */
	public synchronized T get() throws InterruptedException{
		
		// wait until a message arrives
		while(queue.isEmpty()){
			wait();
		}

		notifyAll();
		
		return queue.remove(0);
	}
	
}
