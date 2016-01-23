package com.oo.producerconsumer.servicethread;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.oo.producerconsumer.message.MessageFactory;
import com.oo.producerconsumer.message.MessageQueue;
import com.oo.producerconsumer.servicethread.Consumer;
import com.oo.producerconsumer.servicethread.Producer;

public class TestProducerConsumer {

	private Consumer consumer;

	private Producer producer;

	private MessageQueue messageQueue;
	private int size = 2;


	@Before
	public void setUp() throws Exception {

		messageQueue = new MessageQueue(size);

		consumer = new Consumer(messageQueue);

		producer = new Producer(messageQueue);
	}

	private void populateQueue(){
		for(int i = 0; i < size; i++){
			messageQueue.put(MessageFactory.generate(i));
		}
	}

	@Test
	public void testConsumerCreated(){
		Assert.assertEquals(consumer.getState(), java.lang.Thread.State.NEW); 
	}
	
	@Test
	public void testProducerCreated(){
		Assert.assertEquals(producer.getState(), java.lang.Thread.State.NEW); 
	}

	@Test
	public void testConsumerStart(){
		Assert.assertEquals(consumer.isAlive(), false);
		consumer.start();
		Assert.assertEquals(consumer.isAlive(), true); 
		Assert.assertEquals(consumer.getState(), java.lang.Thread.State.RUNNABLE);
	}
	
	@Test
	public void testProducerStart(){
		Assert.assertEquals(producer.isAlive(), false);
		producer.start();
		Assert.assertEquals(producer.isAlive(), true); 
		Assert.assertEquals(producer.getState(), java.lang.Thread.State.RUNNABLE);
	}

	@Test
	public void testConsumerStop(){

		consumer.start();
		Assert.assertEquals(consumer.isAlive(), true); 
		Assert.assertEquals(consumer.isRunning(), true);

		consumer.terminate();

		Assert.assertEquals(consumer.isRunning(), false);
		Assert.assertEquals(consumer.isInterrupted(), true); 
	}
	
	@Test
	public void testProducerStop(){

		producer.start();
		Assert.assertEquals(producer.isAlive(), true); 
		Assert.assertEquals(producer.isRunning(), true);

		producer.terminate();

		Assert.assertEquals(producer.isRunning(), false);
		Assert.assertEquals(producer.isInterrupted(), true); 
	}

	@Test
	public void testConsumerWhenQueueEmpty() throws InterruptedException {

		consumer.start();
		Assert.assertEquals(messageQueue.isEmpty(), true);

		Thread.sleep(2000);

		Assert.assertEquals(consumer.getState(), java.lang.Thread.State.WAITING);
		
		consumer.terminate();

	}

	@Test
	public void testConsumerWhenQueueNonEmpty() throws InterruptedException {

		populateQueue();
		Assert.assertEquals(messageQueue.isFull(), true);
		consumer.start();
		
		synchronized (messageQueue) {
			while(! messageQueue.isEmpty()){
				messageQueue.wait();
			}
			messageQueue.notifyAll();
		}
		
		consumer.terminate();
		Assert.assertEquals(messageQueue.isEmpty(), true);
		
	}

	@Test
	public void testProducerWhenQueueFull() throws InterruptedException {

		populateQueue();

		producer.start();

		Assert.assertEquals(messageQueue.isFull(), true);

		Thread.sleep(2000);

		Assert.assertEquals(producer.getState(), java.lang.Thread.State.WAITING);
		
		producer.terminate();

	}
	
	@Test
	public void testProducerWhenQueueNotFull() throws InterruptedException {
		
		Assert.assertEquals(messageQueue.isFull(), false);
		producer.start();
		
		synchronized (messageQueue) {
			while(! messageQueue.isFull()){
				messageQueue.wait();
			}
			messageQueue.notifyAll();
		}
		
		producer.terminate();
		Assert.assertEquals(messageQueue.isFull(), true);
	}

	@After
	public void tearDown() throws Exception {
		consumer.terminate();
		producer.terminate();
	}

}
