package com.fiap.techchallenge;

import com.fiap.techchallenge.infrastructure.configuration.DatabaseConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
		"com.fiap.techchallenge.infrastructure",
		"com.fiap.techchallenge.adapters"
})
public class TechChallengeApplication {

	public static void main(String[] args) {

		SpringApplication.run(TechChallengeApplication.class, args);
	}

}
