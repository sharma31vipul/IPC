package com.oo.producerconsumer.client;

import com.oo.producerconsumer.message.MessageQueue;
import com.oo.producerconsumer.servicethread.Consumer;
import com.oo.producerconsumer.servicethread.Producer;

public class ProducerConsumerClient {
	
	public static void main(String[] args) {
	
		int queueSize = 10;
		MessageQueue queue = new MessageQueue(queueSize);
		
		Consumer consumer = new Consumer(queue);
		Producer producer = new Producer(queue);
		
		consumer.start();
		producer.start();
		
	}
}
