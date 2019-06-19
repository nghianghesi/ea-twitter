package edu.mum.cs544.eatwitter.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

public class TweetRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	@NotBlank
	private String tweet;

	public String getTweet() {
		return tweet;
	}

	public void setTweet(String tweet) {
		this.tweet = tweet;
	}
	
}
