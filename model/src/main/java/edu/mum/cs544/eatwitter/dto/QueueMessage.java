package edu.mum.cs544.eatwitter.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class QueueMessage<T extends Serializable> implements Serializable{

	private static final long serialVersionUID = 1L;
	private T request;
	private UUID id;
	private long by_user;
	private String by_username;
	private Date createdAt;
	
	public QueueMessage() {		
	}
	
	public QueueMessage(UserIdAndUsername by, T data) {
		this.by_user = by.getId();
		this.by_username = by.getUsername();
		this.request = data;
		this.createdAt = new Date();
		this.id = UUID.randomUUID();
	}

	public T getRequest() {
		return request;
	}

	public UUID getId() {
		return id;
	}

	public long getBy_user() {
		return by_user;
	}	
	
	public String getBy_username() {
		return by_username;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	
	public void setRequest(T request) {
		this.request = request;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public void setBy_user(long by_user) {
		this.by_user = by_user;
	}

	public void setBy_username(String by_username) {
		this.by_username = by_username;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	public static class TweetMessage extends QueueMessage<TweetRequest>{
		private static final long serialVersionUID = 1L;
		public TweetMessage() {};
		public TweetMessage(UserIdAndUsername by, TweetRequest data) {
			super(by, data);
		}
	}
	
	public static class ThumbMessage extends QueueMessage<ThumbRequest>{
		private static final long serialVersionUID = 1L;
		public ThumbMessage() {};
		public ThumbMessage(UserIdAndUsername by, ThumbRequest data) {
			super(by, data);
		}
	}
	
	public static class RetweetMessage extends QueueMessage<RetweetRequest>{		
		private static final long serialVersionUID = 1L;
		public RetweetMessage() {};
		public RetweetMessage(UserIdAndUsername by, RetweetRequest data) {
			super(by, data);
		}
	}
	
	public static class TweetUpdatedMessage extends QueueMessage<TweetViewModel>{		
		private static final long serialVersionUID = 1L;
		public TweetUpdatedMessage() {};
		public TweetUpdatedMessage(UserIdAndUsername by, TweetViewModel data) {
			super(by, data);
		}
	}
}
