package com.oo.producerconsumer;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

import com.oo.producerconsumer.message.Message;

public class Consumer implements Runnable {

	private static final Logger logger = Logger.getLogger(Consumer.class.getName());

	private volatile boolean running = true;

	private final BlockingQueue<Message> queue;

	public Consumer(BlockingQueue<Message> queue){
		this.queue = queue;
	}

	@Override
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
				msg = queue.take();
				msg.process();
			} catch (InterruptedException e) { }

		}
	}

	public void stop(){
		this.running = false;
	}
}
