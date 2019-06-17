package edu.mum.cs544.eatwitter.api;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication(scanBasePackages= {"edu.mum.cs544.eatwitter"})
@EnableJpaRepositories(basePackages = {"edu.mum.cs544.eatwitter"})
@EntityScan(basePackages= {"edu.mum.cs544.eatwitter.model"})
public class EaTwitterApiApp {
	public static void main(String[] args) {
		SpringApplication.run(EaTwitterApiApp.class, args);
	}
	
	
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
}
