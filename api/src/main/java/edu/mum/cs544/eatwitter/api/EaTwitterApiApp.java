package edu.mum.cs544.eatwitter.api;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.mum.cs544.eatwitter.util.AppConstants;

@SpringBootApplication(scanBasePackages= {"edu.mum.cs544.eatwitter"})
@EnableJpaRepositories(basePackages = {"edu.mum.cs544.eatwitter"})
@EntityScan(basePackages= {"edu.mum.cs544.eatwitter.model"})
@EnableTransactionManagement
public class EaTwitterApiApp {
	public static void main(String[] args) {
		SpringApplication.run(EaTwitterApiApp.class, args);
	}
	
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
	
	@Bean
	public Queue tweetQueue() {
		return new Queue(AppConstants.TWEET_QUEUE);
	}
	
	@Bean
	public Queue thumbQueue() {
		return new Queue(AppConstants.THUMB_QUEUE);
	}
	
	@Bean
	public Queue retweetQueue() {
		return new Queue(AppConstants.RETWEET_QUEUE);
	}
}
