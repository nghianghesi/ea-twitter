package edu.mum.cs544.eatwitter.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

public class RetweetRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	@NotEmpty
	private long tweet_id;

	public long getTweet_id() {
		return tweet_id;
	}

	public void setTweet_id(long tweetid) {
		this.tweet_id = tweetid;
	}
	
}
