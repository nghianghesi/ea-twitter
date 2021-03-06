package edu.mum.cs544.eatwitter.notification;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.mum.cs544.eatwitter.util.AppConstants;

@SpringBootApplication(
		scanBasePackages= {"edu.mum.cs544.eatwitter"},
		exclude = {SecurityAutoConfiguration.class}
		        		
)
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"edu.mum.cs544.eatwitter"})
@EntityScan(basePackages= {"edu.mum.cs544.eatwitter.model"})
public class NotificationApp {

	public static void main(String[] args) {
		SpringApplication.run(NotificationApp.class, args);
	}

	@Bean
	public Queue tweetupdatedQueue() {
		return new Queue(AppConstants.TWEETUPDATED_QUEUE);
	}	
	
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
}
