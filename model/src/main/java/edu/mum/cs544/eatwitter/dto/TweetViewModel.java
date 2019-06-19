package edu.mum.cs544.eatwitter.dto;

import java.io.Serializable;
import java.util.Date;

import edu.mum.cs544.eatwitter.model.AbstractTweet;
import edu.mum.cs544.eatwitter.model.ReTweet;
import edu.mum.cs544.eatwitter.model.Thumb;
import edu.mum.cs544.eatwitter.model.ThumbType;
import edu.mum.cs544.eatwitter.model.User;

public class TweetViewModel implements Serializable{
	private static final long serialVersionUID = 1L;
	private long id;	
	private String tweet;
	private Date date;	
	private String byUser;		
	private long byUserId;		
	private long thumbStats;
	private long retweetStats;
	private ThumbType thumbtype;
	private boolean isRetweeted;
	public long getId() {
		return id;
	}
	public Date getDate() {
		return date;
	}
	public String getByUser() {
		return byUser;
	}
	public long getByUserId() {
		return byUserId;
	}
	public long getThumbStats() {
		return thumbStats;
	}
	public long getRetweetStats() {
		return retweetStats;
	}
	public String getTweet() {
		return tweet;
	}
	
	public ThumbType getThumbtype() {
		return thumbtype;
	}
	public boolean isRetweeted() {
		return isRetweeted;
	}
	
	
	public void setId(long id) {
		this.id = id;
	}
	public void setTweet(String tweet) {
		this.tweet = tweet;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public void setByUser(String byUser) {
		this.byUser = byUser;
	}
	public void setByUserId(long byUserId) {
		this.byUserId = byUserId;
	}
	public void setThumbStats(long thumbStats) {
		this.thumbStats = thumbStats;
	}
	public void setRetweetStats(long retweetStats) {
		this.retweetStats = retweetStats;
	}
	public void setThumbtype(ThumbType thumbtype) {
		this.thumbtype = thumbtype;
	}
	public void setRetweeted(boolean isRetweeted) {
		this.isRetweeted = isRetweeted;
	}
	
	public TweetViewModel(AbstractTweet tweet, User currentUser) {
		this.id = tweet.getId();
		this.date = tweet.getDate();
		this.byUser = tweet.getByUser().getUsername();
		this.byUserId = tweet.getByUser().getId();
		this.thumbStats = tweet.getThumbStats();
		this.retweetStats = tweet.getRetweetStats();
		this.tweet = tweet.getTweet();
		Thumb thumb= tweet.getThumb(currentUser);
		if(thumb!=null) {
			this.thumbtype = thumb.getType();
		}else {
			this.thumbtype = ThumbType.Neutral;
		}
		
		ReTweet rt = tweet.getRetweet(currentUser);
		if(rt!=null) {
			this.isRetweeted = true;
		}else {
			this.isRetweeted = false;
		}
	}	
}
