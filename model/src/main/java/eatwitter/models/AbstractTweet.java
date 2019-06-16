package eatwitter.models;

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
	protected abstract void thumb(EntityManager em, User by, ThumbType type);
	public abstract ReTweet retweet(EntityManager em, User by);
	
	public void thumbUp(EntityManager em, User by) {
		this.thumb(em, by, ThumbType.Up);
	}
	
	public void thumbDown(EntityManager em, User by) {
		this.thumb(em, by, ThumbType.Down);
	}
	
	public abstract String getTweet(); 
	public abstract  int getThumbStats();
	public abstract int getRetweetStats();
}
