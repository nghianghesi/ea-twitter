package edu.mum.cs544.eatwitter.service;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import edu.mum.cs544.eatwitter.api.security.UserPrincipal;
import edu.mum.cs544.eatwitter.dto.TweetRequest;
import edu.mum.cs544.eatwitter.dto.TweetViewModel;
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
import edu.mum.cs544.eatwitter.dto.QueueMessage.TweetUpdatedMessage;
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
		TweetMessage msg = new TweetMessage(currentUser, tweet);
		template.convertAndSend(AppConstants.TWEET_QUEUE, msg);
		return msg.getId();
	}
	
	public UUID queueThumb(UserPrincipal currentUser,ThumbRequest thumb) {
		ThumbMessage msg = new ThumbMessage(currentUser, thumb);
		template.convertAndSend(AppConstants.THUMB_QUEUE, msg);
		return msg.getId();
	}
	
	public UUID queueRetweet(UserPrincipal currentUser, RetweetRequest retweet) {
		RetweetMessage msg = new RetweetMessage(currentUser, retweet);
		template.convertAndSend(AppConstants.RETWEET_QUEUE, msg);
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
	
	public AbstractTweet tweet(long currentUserId, TweetRequest tweetRequest) {
		Tweet tweet = null;
		User user = this.userRepository.getOne(currentUserId);
		if(user!=null) {
			tweet = new Tweet(user, tweetRequest.getTweet());
			persistenceContextManager.persist(tweet);						
		}

		this.notifyTweetUpdate(tweet,user);
		return tweet;
	}
	
	public AbstractTweet thumb(long currentUserId, ThumbRequest thumb) {
		AbstractTweet entity = this.tweetRepository.getOne(thumb.getTweet_id());
		User user = this.userRepository.getOne(currentUserId);
		AbstractTweet updatedTweet = null;
		if(entity!=null && user!=null) {
			if(thumb.getType() == ThumbType.Down) {
				updatedTweet = entity.thumbDown(persistenceContextManager, user);
			}else {
				updatedTweet = entity.thumbUp(persistenceContextManager, user);
			}
		}

		this.notifyTweetUpdate(updatedTweet,user);
		return updatedTweet;
	}
	
	private void notifyTweetUpdate(AbstractTweet updatedTweet, User user) {
		if(updatedTweet != null) {
			TweetViewModel vm = new TweetViewModel(updatedTweet, user);
			TweetUpdatedMessage msg = new TweetUpdatedMessage(user, vm);
			this.template.convertAndSend(AppConstants.TWEETUPDATED_QUEUE, msg);
		}
	}
	
	public AbstractTweet retweet(long currentUserId, RetweetRequest retweet) {
		AbstractTweet entity = this.tweetRepository.getOne(retweet.getTweet_id());
		AbstractTweet updatedTweet = null;
		User user = this.userRepository.getOne(currentUserId);		
		if(entity!=null && user!=null) {
			updatedTweet = entity.retweet(persistenceContextManager, user);
		}
		
		this.notifyTweetUpdate(updatedTweet,user);
		return updatedTweet;		
	}	
}
