package edu.mum.cs544.eatwitter.notification.controler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import edu.mum.cs544.eatwitter.dto.QueueMessage.TweetUpdatedMessage;
import edu.mum.cs544.eatwitter.util.AppConstants;

@Controller
@RabbitListener(queues = AppConstants.TWEETUPDATED_QUEUE)
public class NotificationController {
    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	
	//TODO: Need implement register and convert msg.getBy_username() to active secured id/session id
	@RabbitHandler
	public void receive(TweetUpdatedMessage msg) {
		logger.info("Send socket message to {}",msg.getBy_username());
		simpMessagingTemplate.convertAndSendToUser(msg.getBy_username(), AppConstants.TWEETUPDATED_QUEUE, msg); 
	}	
}
