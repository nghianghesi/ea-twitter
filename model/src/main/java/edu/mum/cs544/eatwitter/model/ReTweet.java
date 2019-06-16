package edu.mum.cs544.eatwitter.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class ReTweet extends AbstractTweet{	
	
	public ReTweet() {}
	public ReTweet(Tweet p, User by) {
		this.parent=p;
		this.setDate(new Date());
		this.setByUser(by);
	}
	
	@ManyToOne
	private Tweet parent;

	@Override
	protected boolean thumb(PersistenceContextManager em, User by, ThumbType type) {
		return this.parent.thumb(em, by, type);
	}

	@Override
	public boolean retweet(PersistenceContextManager em, User by) {
		return this.parent.retweet(em, by);
	}
	
	@Override
	public String getTweet() {
		return this.parent.getTweet();
	}
	
	@Override
	public int getThumbStats() {
		return this.parent.getThumbStats();
	}
	
	@Override
	public int getRetweetStats() {
		return this.parent.getRetweetStats();
	}
}
