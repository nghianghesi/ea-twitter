package edu.mum.cs544.eatwitter.notification.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import edu.mum.cs544.eatwitter.util.AppConstants;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
    	config.enableSimpleBroker("/topic", "/queue", "/user");
    	config.setApplicationDestinationPrefixes("/"+AppConstants.AppName);
    	config.setUserDestinationPrefix("/user");    
    }
 
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
    	logger.info("setup end point {}",AppConstants.AppName);
    	registry.addEndpoint("/"+AppConstants.AppName)
    	.setAllowedOrigins("*")    	
    	.withSockJS();
    }
}