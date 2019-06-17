package edu.mum.cs544.eatwitter.dto;

import java.util.Date;

import edu.mum.cs544.eatwitter.model.AbstractTweet;

public class TweetViewModel {
	private long id;	
	private Date date;	
	private String byUser;		
	private long thumbStats;
	private long retweetStats;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getByUser() {
		return byUser;
	}
	public void setByUser(String byUser) {
		this.byUser = byUser;
	}
	public long getThumbStats() {
		return thumbStats;
	}
	public void setThumbStats(long thumbStats) {
		this.thumbStats = thumbStats;
	}
	public long getRetweetStats() {
		return retweetStats;
	}
	public void setRetweetStats(long retweetStats) {
		this.retweetStats = retweetStats;
	}
	public TweetViewModel(AbstractTweet tweet) {
		this.id = tweet.getId();
		this.date = tweet.getDate();
		this.byUser = tweet.getByUser().getUsername();
		this.thumbStats = tweet.getThumbStats();
		this.retweetStats = tweet.getRetweetStats();
	}	
}
