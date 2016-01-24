package com.oo.producerconsumer.servicethread;

import java.util.logging.Logger;

import com.oo.producerconsumer.message.Message;
import com.oo.producerconsumer.message.ThreadSafeBlockingQueue;

public class Consumer implements Runnable {

	private static final Logger logger = Logger.getLogger(Consumer.class.getName());

	private volatile boolean running = true;

	private final ThreadSafeBlockingQueue<Message> queue;

	public Consumer(ThreadSafeBlockingQueue<Message> queue){
		this.queue = queue;
	}

	public void run() {

		while(running){

			// little delay for verifying the program's output.
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			Message msg;
			try {
				msg = queue.get();
				msg.process();
			} catch (InterruptedException e) { }

		}
	}

	public void stop(){
		this.running = false;
	}
}
