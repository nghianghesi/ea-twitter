package edu.mum.cs544.eatwitter.service;

import java.util.List;
import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mum.cs544.eatwitter.api.security.UserPrincipal;
import edu.mum.cs544.eatwitter.dto.TweetRequest;
import edu.mum.cs544.eatwitter.model.AbstractTweet;
import edu.mum.cs544.eatwitter.model.Tweet;
import edu.mum.cs544.eatwitter.repository.TweetRepository;
import edu.mum.cs544.eatwitter.dto.QueueMessage.RetweetMessage;
import edu.mum.cs544.eatwitter.dto.QueueMessage.ThumbMessage;
import edu.mum.cs544.eatwitter.dto.QueueMessage.TweetMessage;
import edu.mum.cs544.eatwitter.dto.RetweetRequest;
import edu.mum.cs544.eatwitter.dto.ThumbRequest;
import edu.mum.cs544.eatwitter.util.AppConstants;

@Service
public class TweetService {

	@Autowired
	private RabbitTemplate template;
	@Autowired 
	private TweetRepository tweetRepository;
	
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
		return tweetRepository.findRecentTweets(currentUser.getId(), AppConstants.MAX_PAGE_SIZE);
	}
	
	public List<Tweet> hotTweets(UserPrincipal currentUser) {
		return tweetRepository.findHotTweets(AppConstants.MAX_PAGE_SIZE);
	}
}
