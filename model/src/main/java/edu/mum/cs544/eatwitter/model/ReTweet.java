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
	protected AbstractTweet thumb(PersistenceContextManager em, User by, ThumbType type) {
		return this.parent.thumb(em, by, type);
	}

	@Override
	public AbstractTweet retweet(PersistenceContextManager em, User by) {
		if(!this.getByUser().equals(by)) {
			return this.parent.retweet(em, by);
		}else {
			return null;
		}
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
	
	@Override
	public Thumb getThumb(User byUser) {
		return this.parent.getThumb(byUser);
	}
	
	@Override
	public ReTweet getRetweet(User byUser) {
		return this.parent.getRetweet(byUser);
	}
}
