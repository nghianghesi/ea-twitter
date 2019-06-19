package edu.mum.cs544.eatwitter.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import edu.mum.cs544.eatwitter.model.ThumbType;

public class ThumbRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotNull
	private long tweet_id;
	@NotNull
	private ThumbType type;
	public long getTweet_id() {
		return tweet_id;
	}
	public void setTweet_id(long tweetId) {
		this.tweet_id = tweetId;
	}
	public ThumbType getType() {
		return type;
	}
	public void setType(ThumbType type) {
		this.type = type;
	}
	
}
