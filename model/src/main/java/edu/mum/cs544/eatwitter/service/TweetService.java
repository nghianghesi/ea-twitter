package edu.mum.cs544.eatwitter.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import edu.mum.cs544.eatwitter.api.security.UserPrincipal;
import edu.mum.cs544.eatwitter.dto.TweetRequest;
import edu.mum.cs544.eatwitter.model.AbstractTweet;
import edu.mum.cs544.eatwitter.model.PersistenceContextManager;
import edu.mum.cs544.eatwitter.model.ThumbType;
import edu.mum.cs544.eatwitter.model.Tweet;
import edu.mum.cs544.eatwitter.model.User;
import edu.mum.cs544.eatwitter.repository.TweetRepository;
import edu.mum.cs544.eatwitter.repository.UserRepository;
import edu.mum.cs544.eatwitter.dto.QueueMessage.RetweetMessage;
import edu.mum.cs544.eatwitter.dto.QueueMessage.ThumbMessage;
import edu.mum.cs544.eatwitter.dto.QueueMessage.TweetMessage;
import edu.mum.cs544.eatwitter.dto.RetweetRequest;
import edu.mum.cs544.eatwitter.dto.ThumbRequest;
import edu.mum.cs544.eatwitter.util.AppConstants;

@Service
@Transactional
public class TweetService {

	@Autowired
	private RabbitTemplate template;
	@Autowired 
	private TweetRepository tweetRepository;
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	PersistenceContextManager persistenceContextManager;
	
	public UUID queueTweet(UserPrincipal currentUser,TweetRequest tweet) {
		TweetMessage msg = new TweetMessage(currentUser.getId(), tweet);
		template.convertAndSend(AppConstants.TWEET_QUEUE, msg);
		return msg.getId();
	}
	
	public UUID queueThumb(UserPrincipal currentUser,ThumbRequest thumb) {
		ThumbMessage msg = new ThumbMessage(currentUser.getId(), thumb);
		template.convertAndSend(AppConstants.THUMB_QUEUE, msg);
		return msg.getId();
	}
	
	public UUID queueRetweet(UserPrincipal currentUser,RetweetRequest retweet) {
		RetweetMessage msg = new RetweetMessage(currentUser.getId(), retweet);
		template.convertAndSend(AppConstants.THUMB_QUEUE, msg);
		return msg.getId();
	}
	
	public List<AbstractTweet> recentTweets(UserPrincipal currentUser) {
		return tweetRepository.findRecentTweets(currentUser.getId(),
											PageRequest.of(0, AppConstants.MAX_PAGE_SIZE))
								.getContent();
	}
	
	public List<Tweet> hotTweets(UserPrincipal currentUser) {
		return tweetRepository.findHotTweets(PageRequest.of(0, AppConstants.MAX_PAGE_SIZE))
				.getContent();
	}
	
	public AbstractTweet tweet(long currentUserId, TweetRequest tweet) {
		Tweet entity = new Tweet();
		User user = this.userRepository.getOne(currentUserId);
		if(user!=null) {
			entity.setByUser(user);
			entity.setDate(new Date());
			entity.setTweet(tweet.getTweet());
			return this.tweetRepository.save(entity);
		}else {
			return null;
		}
	}
	
	public AbstractTweet thumb(long currentUserId,ThumbRequest thumb) {
		AbstractTweet entity = this.tweetRepository.getOne(thumb.getTweet_id());
		User user = this.userRepository.getOne(currentUserId);		
		if(entity!=null && user!=null) {
			if(thumb.getType() == ThumbType.Down) {
				return entity.thumbDown(persistenceContextManager, user);
			}else {
				return entity.thumbUp(persistenceContextManager, user);
			}
		}else {
			return null;
		}
	}
	
	public AbstractTweet retweet(long currentUserId, RetweetRequest retweet) {
		AbstractTweet entity = this.tweetRepository.getOne(retweet.getTweet_id());
		User user = this.userRepository.getOne(currentUserId);		
		return entity.retweet(persistenceContextManager, user);
	}	
}
