package edu.mum.cs544.eatwitter.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class QueueMessage<T extends Serializable> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private T request;
	private UUID id;
	private long by_user;
	private Date createdAt;
	
	public QueueMessage() {		
	}
	
	public QueueMessage(long by_user, T data) {
		this.by_user = by_user;
		this.request = data;
		this.createdAt = new Date();
		this.id = UUID.randomUUID();
	}

	public T getRequest() {
		return request;
	}

	public void setRequest(T request) {
		this.request = request;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public long getBy_user() {
		return by_user;
	}

	public void setBy_user(long by_user) {
		this.by_user = by_user;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	public static class TweetMessage extends QueueMessage<TweetRequest>{
		private static final long serialVersionUID = 1L;
		public TweetMessage() {};
		public TweetMessage(long by_user, TweetRequest data) {
			super(by_user, data);
		}
	}
	public static class ThumbMessage extends QueueMessage<ThumbRequest>{
		private static final long serialVersionUID = 1L;
		public ThumbMessage() {};
		public ThumbMessage(long by_user, ThumbRequest data) {
			super(by_user, data);
		}
	}
	public static class RetweetMessage extends QueueMessage<RetweetRequest>{		
		private static final long serialVersionUID = 1L;
		public RetweetMessage() {};
		public RetweetMessage(long by_user, RetweetRequest data) {
			super(by_user, data);
		}
	}
}
