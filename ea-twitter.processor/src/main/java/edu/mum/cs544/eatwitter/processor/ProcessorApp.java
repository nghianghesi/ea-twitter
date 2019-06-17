package edu.mum.cs544.eatwitter.processor;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(
		scanBasePackages= {"edu.mum.cs544.eatwitter"}
)
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"edu.mum.cs544.eatwitter"})
public class ProcessorApp {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ProcessorApp.class)
        .web(WebApplicationType.NONE) //
        .run(args);
	}
}
