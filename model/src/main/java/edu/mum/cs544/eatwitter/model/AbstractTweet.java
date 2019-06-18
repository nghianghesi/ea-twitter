package edu.mum.cs544.eatwitter.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Inheritance
@Table(name="tweet")
public abstract class AbstractTweet {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="tweet_date")
	private Date date;
	
	@ManyToOne 
	@JoinColumn(name="by_user")
	private User byUser;	
	
	public long getId() {
		return id;
	}
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
	protected abstract AbstractTweet thumb(PersistenceContextManager em, User by, ThumbType type);
	public abstract AbstractTweet retweet(PersistenceContextManager em, User by);
	
	public AbstractTweet thumbUp(PersistenceContextManager em, User by) {
		return this.thumb(em, by, ThumbType.Up);
	}
	
	public AbstractTweet thumbDown(PersistenceContextManager em, User by) {
		return this.thumb(em, by, ThumbType.Down);
	}
	
	public abstract String getTweet(); 
	public abstract  int getThumbStats();
	public abstract int getRetweetStats();
	public abstract Thumb getThumb(User byUser) ;
	public abstract ReTweet getRetweet(User byUser) ;
}
