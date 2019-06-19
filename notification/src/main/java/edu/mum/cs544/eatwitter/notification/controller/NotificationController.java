package edu.mum.cs544.eatwitter.notification.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.mum.cs544.eatwitter.api.security.UserPrincipal;
import edu.mum.cs544.eatwitter.dto.QueueMessage.TweetUpdatedMessage;
import edu.mum.cs544.eatwitter.notification.service.NotificationService;
import edu.mum.cs544.eatwitter.security.jwt.JwtTokenProvider;
import edu.mum.cs544.eatwitter.util.AppConstants;

@Controller
@RabbitListener(queues = AppConstants.TWEETUPDATED_QUEUE)
public class NotificationController {
    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);

    @Autowired
    ObjectMapper objectMapper;
    
    @Autowired NotificationService notificationService;
    
    @Autowired JwtTokenProvider jwtTokenProvider;
    
	@RabbitHandler
	public void receive(TweetUpdatedMessage msg) {
		logger.info("Send socket message to {}",msg.getBy_username());
		this.notificationService.notify(msg.getBy_username(), AppConstants.TWEETUPDATED_QUEUE, msg.getRequest()); 
	}	
	
	@MessageMapping("/register")
	public void processRegisterMessage(@Payload String jwt, SimpMessageHeaderAccessor ha) {
    	logger.info("register {}",jwt);
		
		if(jwtTokenProvider.validateToken(jwt)) {
			UserPrincipal user = jwtTokenProvider.getUserPrincipalFromJWT(jwt);
			notificationService.register(user, ha.getSessionId());
		}
	}
	
	@MessageMapping("/unregister")
	public void processUnregisterMessage(@Payload String jwt, SimpMessageHeaderAccessor ha) {
		logger.info("unregister {}",jwt);
		if(jwtTokenProvider.validateToken(jwt)) {
			UserPrincipal user = jwtTokenProvider.getUserPrincipalFromJWT(jwt);
			notificationService.unregister(user);
		}
	}
}
