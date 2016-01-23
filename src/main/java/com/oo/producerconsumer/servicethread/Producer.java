package com.oo.producerconsumer.servicethread;

import java.util.logging.Logger;

import com.oo.producerconsumer.message.Message;
import com.oo.producerconsumer.message.MessageFactory;
import com.oo.producerconsumer.message.MessageQueue;

public class Producer extends Thread {

	private static final Logger logger = Logger.getLogger(Producer.class.getName());

	private volatile boolean running = true;

	private MessageQueue messageQueue;

	public Producer(MessageQueue messageQueue){
		this.messageQueue = messageQueue;
	}

	private static long messageCount = 1;

	private long messageIDGenerator(){
		return messageCount++;
	}

	@Override
	public void run() {

		while(running){

			synchronized (messageQueue) {

				while(messageQueue.isFull()){
					try {
						logger.info("Message queue is full. Lets wait until a message is processed by the consumer thread.");
						messageQueue.wait();
					} catch (InterruptedException e) {

					}
				}

				// little delay for verifying the program's output.
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				Message msg = MessageFactory.generate(messageIDGenerator());
				messageQueue.put(msg);
				logger.info(msg+" produced and added to queue. Lets notify the other threads.");
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
