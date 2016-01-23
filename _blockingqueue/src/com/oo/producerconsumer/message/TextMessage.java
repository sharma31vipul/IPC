package com.oo.producerconsumer.message;

import java.util.logging.Logger;

public class TextMessage implements Message {
	
	private static final Logger logger = Logger.getLogger(TextMessage.class.getName());
	
	private long messageId;
	
	public TextMessage(long messageId){
		this.messageId = messageId;
	}
	
	@Override
	public void process() {
		logger.info("Message with ID " +messageId+ " processed.");
	}

	@Override
	public String toString() {
		return "TextMessage [messageId=" + messageId + "]";
	}
	
}
