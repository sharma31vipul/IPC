package com.oo.producerconsumer.servicethread;

import java.util.logging.Logger;

import com.oo.producerconsumer.message.Message;
import com.oo.producerconsumer.message.MessageFactory;
import com.oo.producerconsumer.message.ThreadSafeBlockingQueue;

public class Producer implements Runnable {

	private static final Logger logger = Logger.getLogger(Producer.class.getName());

	private volatile boolean running = true;

	private final ThreadSafeBlockingQueue<Message> queue;

	public Producer(ThreadSafeBlockingQueue<Message> queue){
		this.queue = queue;
	}

	private static long messageCount = 1;

	private long messageIDGenerator(){
		return messageCount++;
	}

	public void run() {
		
		while(running){
			
			// little delay for verifying the program's output.
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			Message msg = MessageFactory.generate(messageIDGenerator());
			try {
				logger.info(msg+ " produced.");
				queue.put(msg);
			} catch (InterruptedException e) { }

		}
	}

	public void stop(){
		this.running = false;
	}

}
