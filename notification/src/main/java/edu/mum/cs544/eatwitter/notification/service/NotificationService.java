package edu.mum.cs544.eatwitter.notification.service;

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
import edu.mum.cs544.eatwitter.notification.controller.NotificationController;

@Service
public class NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);

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
	
	private MessageHeaders  createHeader(String sessionid) {
		SimpMessageHeaderAccessor ha =SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
		ha.setSessionId(sessionid);
		ha.setLeaveMutable(true);
		return ha.getMessageHeaders();
	}
}
