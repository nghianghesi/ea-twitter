package edu.mum.cs544.eatwitter.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import edu.mum.cs544.eatwitter.api.security.UserPrincipal;
import edu.mum.cs544.eatwitter.dto.TweetViewModel;
import edu.mum.cs544.eatwitter.dto.UserIdAndUsername;
import edu.mum.cs544.eatwitter.model.User;
import edu.mum.cs544.eatwitter.repository.TweetRepository;
import edu.mum.cs544.eatwitter.repository.UserRepository;
import edu.mum.cs544.eatwitter.util.AppConstants;

@Service
public class NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
	
    @Autowired
	private UserRepository userRepository;
    @Autowired
	private TweetRepository tweetRepository;
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	private Map<String, String> onlineUsernameToSessionId = new HashMap<>();	

	
	public void register(UserPrincipal user, String sessionid) {
		logger.info("registered {} {}",user.getUsername(), sessionid);
		this.onlineUsernameToSessionId.put(user.getUsername(), sessionid);
	}
	
	public void unregister(UserPrincipal user) {
		this.onlineUsernameToSessionId.remove(user.getUsername());
	}
	
	public void notify(String username, String channel, Object message) {		
		String sessionId = this.onlineUsernameToSessionId.getOrDefault(username, null);
		if(sessionId!=null) {
			logger.info("Send message to {} {}",sessionId, channel);
			this.simpMessagingTemplate
			.convertAndSend("/queue/"+channel+"/"+sessionId, message, createHeader(sessionId));
		}
	}
	
	private MessageHeaders createHeader(String sessionid) {
		SimpMessageHeaderAccessor ha = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
		ha.setSessionId(sessionid);
		ha.setLeaveMutable(true);
		return ha.getMessageHeaders();
	}
	
	public void notifyAuthorAndFollowers(UserIdAndUsername author, String channel, Object message) {
		this.notify(author.getUsername(), channel, message);
		logger.info("Send message follower of {} {}", author.getId(), author.getUsername());

		for(User f : this.userRepository.findFriendsById(author.getId())) {
			logger.info("Send message follower {}",f.getUsername());
			this.notify(f.getUsername(), channel, message);
		}
	}
	
	public void nofityTweetChangeToAuthorAndFollower(TweetViewModel tw) {
		this.notifyAuthorAndFollowers(
				new UserIdAndUsername.SimpleUserIdAndUsername(tw.getByUserId(), tw.getByUser())
					, AppConstants.TWEETUPDATED_QUEUE, tw); 
		for(User f : this.tweetRepository.findRetweetUsers(tw.getId())) {
			this.notifyAuthorAndFollowers(f, AppConstants.TWEETUPDATED_QUEUE, tw);
		}
	}
}
