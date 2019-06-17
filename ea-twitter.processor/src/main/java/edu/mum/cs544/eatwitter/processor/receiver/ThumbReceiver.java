package edu.mum.cs544.eatwitter.processor.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.mum.cs544.eatwitter.dto.QueueMessage.ThumbMessage;
import edu.mum.cs544.eatwitter.service.TweetService;
import edu.mum.cs544.eatwitter.util.AppConstants;

@Component
@RabbitListener(queues = AppConstants.THUMB_QUEUE)
public class ThumbReceiver {
	@Autowired
	private TweetService tweetService;
	
	@RabbitHandler
	public void receive(ThumbMessage msg) {
		tweetService.thumb(msg.getBy_user(), msg.getRequest());
	}	
}
