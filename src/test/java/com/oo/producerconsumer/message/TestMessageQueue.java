package com.oo.producerconsumer.message;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.oo.producerconsumer.message.Message;
import com.oo.producerconsumer.message.MessageFactory;
import com.oo.producerconsumer.message.MessageQueue;

public class TestMessageQueue {

	private MessageQueue queue;
	private int size = 10;


	@Before
	public void before() throws Exception {
		queue = new MessageQueue(size);
	}

	private void populateQueue(){
		for(int i = 0; i < 10; i++){
			queue.put(MessageFactory.generate(i));
		}
	}

	@Test
	public void testIsFull(){
		populateQueue();
		Assert.assertEquals(queue.isFull(), true);
	}

	@Test
	public void testGetWhenQueueEmpty(){
		Assert.assertEquals(queue.get(), null);
	}

	@Test
	public void testGetWhenQueueNonEmpty(){
		populateQueue();
		Assert.assertNotNull(queue.get());
	}

	@Test
	public void testPutWhenQueueFull(){
		populateQueue();

		Assert.assertEquals(queue.getSize(),10);
		Message msg = MessageFactory.generate(10);
		queue.put(msg);
		Assert.assertEquals(queue.getSize(),10);
	}

	@Test
	public void testPutWhenQueueNotFull(){
		
		populateQueue();
		Assert.assertEquals(queue.getSize(),10);
		queue.get();
		Assert.assertEquals(queue.getSize(),9);
		Message msg = MessageFactory.generate(10);
		queue.put(msg);
		Assert.assertEquals(queue.getSize(),10);
	}


	@Test
	public void testIsEmpty(){
		Assert.assertEquals(queue.isEmpty(), true);
	}

	@After
	public void after() throws Exception {
		queue = null;
	}

}
