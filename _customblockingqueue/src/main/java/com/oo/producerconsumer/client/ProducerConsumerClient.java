package com.oo.producerconsumer.client;

import com.oo.producerconsumer.message.Message;
import com.oo.producerconsumer.message.ThreadSafeBlockingQueue;
import com.oo.producerconsumer.servicethread.Consumer;
import com.oo.producerconsumer.servicethread.Producer;

public class ProducerConsumerClient {

	public static void main(String[] args) {

		int queueSize = 10;

		ThreadSafeBlockingQueue<Message> sharedMessageQueue = new ThreadSafeBlockingQueue<Message>(queueSize);

		Runnable consumerRunnable = new Consumer(sharedMessageQueue);
		Runnable producerRunnable = new Producer(sharedMessageQueue);

		Thread consumerThread = new Thread(consumerRunnable);
		Thread producerThread = new Thread(producerRunnable);

		producerThread.start();
		consumerThread.start();

	}
}
