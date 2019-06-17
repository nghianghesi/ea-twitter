package edu.mum.cs544.eatwitter.processor;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import edu.mum.cs544.eatwitter.util.AppConstants;

@SpringBootApplication(
		scanBasePackages= {"edu.mum.cs544.eatwitter"}
)
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"edu.mum.cs544.eatwitter"})
@EntityScan(basePackages= {"edu.mum.cs544.eatwitter.model"})
public class ProcessorApp {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ProcessorApp.class)
        .web(WebApplicationType.NONE) //
        .run(args);
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