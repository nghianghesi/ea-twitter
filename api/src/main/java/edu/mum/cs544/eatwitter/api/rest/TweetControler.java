package edu.mum.cs544.eatwitter.api.rest;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.mum.cs544.eatwitter.api.security.CurrentUser;
import edu.mum.cs544.eatwitter.api.security.UserPrincipal;
import edu.mum.cs544.eatwitter.dto.RetweetRequest;
import edu.mum.cs544.eatwitter.dto.ThumbRequest;
import edu.mum.cs544.eatwitter.dto.AcknowledgementResponse;
import edu.mum.cs544.eatwitter.dto.TweetRequest;
import edu.mum.cs544.eatwitter.dto.TweetViewModel;
import edu.mum.cs544.eatwitter.model.AbstractTweet;
import edu.mum.cs544.eatwitter.model.User;
import edu.mum.cs544.eatwitter.service.TweetService;

@RestController
@RequestMapping("/api/tweet")
public class TweetControler {
	
	@Autowired
	private TweetService tweetService;
	
	@PostMapping
	public ResponseEntity<?> tweet(@Valid @RequestBody TweetRequest tweet,
									@CurrentUser UserPrincipal currentUser){	
		UUID acknowledgeId= tweetService.queueTweet(currentUser,tweet);
        return ResponseEntity.ok(new AcknowledgementResponse(acknowledgeId));
	}
	
	@PostMapping(value="/retweet")
	public ResponseEntity<?> retweet(@Valid @RequestBody RetweetRequest retweet,
									@CurrentUser UserPrincipal currentUser){
		UUID acknowledgeId= tweetService.queueRetweet(currentUser,retweet);
        return ResponseEntity.ok(new AcknowledgementResponse(acknowledgeId));
	}	
	
	@PostMapping(value="/thumb")
	public ResponseEntity<?> thumb(@Valid @RequestBody ThumbRequest thumb,
									@CurrentUser UserPrincipal currentUser){
		UUID acknowledgeId= tweetService.queueThumb(currentUser,thumb);
        return ResponseEntity.ok(new AcknowledgementResponse(acknowledgeId));
	}

	
	private List<TweetViewModel> toListTweetViewModel(List<? extends AbstractTweet> list, UserPrincipal currentUser){
		User u = new User(currentUser);
		return list.stream()
		.map(t -> new TweetViewModel(t, u))
		.collect(Collectors.toList());
	}
	
	@GetMapping(value="/recent")
	public ResponseEntity<?> recentTweets(@CurrentUser UserPrincipal currentUser){
		List<TweetViewModel> list=toListTweetViewModel(tweetService.recentTweets(currentUser), currentUser);
		return ResponseEntity.ok(list);
	}
	
	@GetMapping(value="/hot")
	public ResponseEntity<?> topTweets(@CurrentUser UserPrincipal currentUser){
		List<TweetViewModel> list=toListTweetViewModel(tweetService.hotTweets(currentUser), currentUser);		
		return ResponseEntity.ok(list);
	}	
	
	@GetMapping(value="/test")
	public ResponseEntity<?> test(){		
		return ResponseEntity.ok("This is test");
	}
}
