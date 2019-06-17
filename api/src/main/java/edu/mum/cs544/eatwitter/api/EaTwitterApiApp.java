package edu.mum.cs544.eatwitter.api;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
@EnableJpaRepositories
public class EaTwitterApiApp {
	public static void main(String[] args) {
		SpringApplication.run(EaTwitterApiApp.class, args);
	}
	
	
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
}
