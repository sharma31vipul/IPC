package com.oo.producerconsumer.client;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.oo.producerconsumer.message.Message;
import com.oo.producerconsumer.servicethread.Consumer;
import com.oo.producerconsumer.servicethread.Producer;

public class ProducerConsumerClient {

	public static void main(String[] args) {

		int queueSize = 10;

		BlockingQueue<Message> sharedMessageQueue = new LinkedBlockingQueue<Message>(queueSize);

		Runnable consumerRunnable = new Consumer(sharedMessageQueue);
		Runnable producerRunnable = new Producer(sharedMessageQueue);

		Thread consumerThread = new Thread(consumerRunnable);
		Thread producerThread = new Thread(producerRunnable);

		producerThread.start();
		consumerThread.start();

	}
}
