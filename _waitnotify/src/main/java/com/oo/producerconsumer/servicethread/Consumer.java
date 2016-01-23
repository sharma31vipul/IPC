package com.oo.producerconsumer.servicethread;

import java.util.logging.Logger;

import com.oo.producerconsumer.message.Message;
import com.oo.producerconsumer.message.MessageQueue;

public class Consumer extends Thread {

	private static final Logger logger = Logger.getLogger(Consumer.class.getName());

	private volatile boolean running = true;

	private MessageQueue messageQueue;

	public Consumer(MessageQueue messageQueue){
		this.messageQueue = messageQueue;
	}

	@Override
	public void run() {

		while(running){

			synchronized (messageQueue) {

				while(messageQueue.isEmpty()){
					try {
						logger.info("Message queue is empty. Lets wait until the producer produces a message in to the queue.");
						messageQueue.wait();
					} catch (InterruptedException e) { }
				}

				// little delay for verifying the program's output.
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) { }

				Message msg = messageQueue.get();
				if(msg != null)
					msg.process();
				messageQueue.notifyAll();
			}
		}
	}

	public void terminate(){
		this.running = false;
		this.interrupt();
	}

	public boolean isRunning() {
		return running;
	}
}
