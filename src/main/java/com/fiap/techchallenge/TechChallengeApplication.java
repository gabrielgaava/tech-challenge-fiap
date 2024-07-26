package com.fiap.techchallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
		"com.fiap.techchallenge.drivers",
		"com.fiap.techchallenge.handlers"
})
public class TechChallengeApplication {

	public static void main(String[] args) {

		SpringApplication.run(TechChallengeApplication.class, args);
	}

}
