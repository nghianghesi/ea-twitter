package edu.mum.cs544.eatwitter.dto;

import java.util.Date;

import edu.mum.cs544.eatwitter.model.AbstractTweet;
import edu.mum.cs544.eatwitter.model.ReTweet;
import edu.mum.cs544.eatwitter.model.Thumb;
import edu.mum.cs544.eatwitter.model.ThumbType;
import edu.mum.cs544.eatwitter.model.User;

public class TweetViewModel {
	private long id;	
	private String tweet;
	private Date date;	
	private String byUser;		
	private long thumbStats;
	private long retweetStats;
	private ThumbType thumbtype;
	private boolean isRetweeted;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public String getByUser() {
		return byUser;
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
	public TweetViewModel(AbstractTweet tweet, User currentUser) {
		this.id = tweet.getId();
		this.date = tweet.getDate();
		this.byUser = tweet.getByUser().getUsername();
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
