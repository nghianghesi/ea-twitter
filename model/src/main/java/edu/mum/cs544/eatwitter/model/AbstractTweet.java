package edu.mum.cs544.eatwitter.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
@Inheritance
public abstract class AbstractTweet {
	@Id
	@GeneratedValue
	private long id;
	
	@Column(name="tweet_date")
	private Date date;
	
	@ManyToOne 
	@JoinColumn(name="by_user")
	private User byUser;	
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public User getByUser() {
		return byUser;
	}
	
	public void setByUser(User byUser) {
		this.byUser = byUser;
	}
	protected abstract boolean thumb(PersistenceContextManager em, User by, ThumbType type);
	public abstract boolean retweet(PersistenceContextManager em, User by);
	
	public boolean thumbUp(PersistenceContextManager em, User by) {
		return this.thumb(em, by, ThumbType.Up);
	}
	
	public boolean thumbDown(PersistenceContextManager em, User by) {
		return this.thumb(em, by, ThumbType.Down);
	}
	
	public abstract String getTweet(); 
	public abstract  int getThumbStats();
	public abstract int getRetweetStats();
}
