package com.oo.producerconsumer.message;

import java.util.LinkedList;
import java.util.Queue;

import com.oo.producerconsumer.message.Message;

public class MessageQueue {
	
	private Queue<Message> queue;
	private final int size;
	
	public MessageQueue(int size){
		queue = new LinkedList<Message>();
		this.size = size;
	}
	
	/*
	 * Add message to tail of the queue
	 */
	public void put(Message message){
		if(! isFull()){
			queue.add(message);
		}
	}
	
	/*
	 * Remove and return message from the head of the queue
	 */
	public Message get(){
		return queue.poll();
	}
	
	/*
	 * Return true if queue becomes full
	 */
	public boolean isFull(){
		return queue.size() == size;
	}
	
	/*
	 * Return true if queue is empty
	 */
	public boolean isEmpty(){
		return queue.isEmpty();
	}
	
	public int getSize() {
		return queue.size();
	}
}
