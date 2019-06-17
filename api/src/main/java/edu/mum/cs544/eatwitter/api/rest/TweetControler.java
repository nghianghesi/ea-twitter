package edu.mum.cs544.eatwitter.api.rest;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.mum.cs544.eatwitter.api.security.CurrentUser;
import edu.mum.cs544.eatwitter.api.security.UserPrincipal;
import edu.mum.cs544.eatwitter.dto.QueueMessage.TweetMessage;
import edu.mum.cs544.eatwitter.dto.QueueMessage.RetweetMessage;
import edu.mum.cs544.eatwitter.dto.QueueMessage.ThumbMessage;
import edu.mum.cs544.eatwitter.dto.RetweetRequest;
import edu.mum.cs544.eatwitter.dto.ThumbRequest;
import edu.mum.cs544.eatwitter.dto.AcknowledgementResponse;
import edu.mum.cs544.eatwitter.dto.TweetRequest;
import edu.mum.cs544.eatwitter.model.AbstractTweet;
import edu.mum.cs544.eatwitter.util.AppConstants;

@RestController
@RequestMapping("/api/tweet")
public class TweetControler {
	@Autowired
	private RabbitTemplate template;
	
	@PostMapping
	public ResponseEntity<?> tweet(@Valid @RequestBody TweetRequest tweet,
									@CurrentUser UserPrincipal currentUser){
		TweetMessage msg = new TweetMessage(currentUser.getId(), tweet);
		template.convertAndSend(AppConstants.THUMB_QUEUE, msg);
        return ResponseEntity.ok(new AcknowledgementResponse(msg.getId()));
	}
	
	@PostMapping(value="/retweet")
	public ResponseEntity<?> retweet(@Valid @RequestBody RetweetRequest retweet,
									@CurrentUser UserPrincipal currentUser){
		RetweetMessage msg = new RetweetMessage(currentUser.getId(), retweet);
		template.convertAndSend(AppConstants.RETWEET_QUEUE, msg);
        return ResponseEntity.ok(new AcknowledgementResponse(msg.getId()));
	}	
	
	@PostMapping(value="/thumb")
	public ResponseEntity<?> thumbup(@Valid @RequestBody ThumbRequest thumb,
									@CurrentUser UserPrincipal currentUser){
		ThumbMessage msg = new ThumbMessage(currentUser.getId(), thumb);
		template.convertAndSend(AppConstants.THUMB_QUEUE, msg);
        return ResponseEntity.ok(new AcknowledgementResponse(msg.getId()));
	}

	@GetMapping(value="/recents")
	public ResponseEntity<?> recentTweets(){
		return ResponseEntity.ok(new ArrayList<AbstractTweet>());
	}
	
	@GetMapping(value="/hot")
	public ResponseEntity<?> topTweets(){
		return ResponseEntity.ok(new ArrayList<AbstractTweet>());
	}	
}
