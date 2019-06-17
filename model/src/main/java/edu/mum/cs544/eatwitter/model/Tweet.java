package edu.mum.cs544.eatwitter.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.SafeHtml;

@Entity
public class Tweet extends AbstractTweet{
	@SafeHtml
	@NotBlank
	private String tweet;
	
	
	@OneToMany
	@JoinColumn(name="tweet_id")
	@MapKey(name="byUser")
	private Map<User, Thumb> thumbs = new HashMap<User, Thumb>();
	
	@OneToMany(mappedBy="parent")
	@MapKey(name="byUser")
	private Map<User, ReTweet> retweets = new HashMap<User, ReTweet>();	
	
	@ManyToMany	
	@MapKey(name="hash")
	private Set<Hash> hashes = new HashSet<Hash>();
	
	private int thumbStats;
	private int retweetStats;
	
	
	
	@Override
	protected AbstractTweet thumb(PersistenceContextManager em, User by, ThumbType type) {
		if(this.thumbs.containsKey(by)) {
			Thumb existing = this.thumbs.get(by);
			if(existing.isType(type)) {
				existing.setType(ThumbType.Neutral);
				this.thumbStats-=type.getValue();
			}else {
				existing.setType(type);
				this.thumbStats-=type.getValue()*2;
			}
			em.merge(existing);
			em.merge(this);
			return this;
		}else {
			Thumb thumb = new Thumb(by, type);
			this.thumbStats+=type.getValue();			
			this.thumbs.put(by, thumb);			
			em.merge(thumb);
			em.merge(this);
			return this;
		}
	}
	
	@Override
	public AbstractTweet retweet(PersistenceContextManager em, User by) {
		if(!this.retweets.containsKey(by)) {
			ReTweet tw = new ReTweet(this, by);
			this.retweetStats+=1;
			this.retweets.put(by, tw);
			em.merge(tw);
			em.merge(this);
			return this;
		}else {
			return this;
		}
	}

	@Override
	public String getTweet() {
		return tweet;
	}

	public void setTweet(String tweet) {
		this.tweet = tweet;
	}

	@Override
	public int getThumbStats() {
		return thumbStats;
	}

	@Override
	public int getRetweetStats() {
		return retweetStats;
	}
}
