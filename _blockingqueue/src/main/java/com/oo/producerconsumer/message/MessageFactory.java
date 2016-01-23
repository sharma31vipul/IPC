package com.oo.producerconsumer.message;

import com.oo.producerconsumer.message.Message;
import com.oo.producerconsumer.message.TextMessage;

/*
 * Factory class to generate messages
 */
public final class MessageFactory {

	/*
	 * No instance creation allowed
	 */
	private MessageFactory(){

	}

	/*
	 * static factory method to generate messages
	 */
	public static Message generate(long messageId){
		return new TextMessage(messageId);
	}

}
