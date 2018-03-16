package com.challenge;

import com.challenge.model.Horse;
import com.challenge.service.HorseService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeApplication.class, args);
	}

	@Bean
	CommandLineRunner init(HorseService horseService) {
		return evt -> {
			horseService.saveHorse(new Horse("Horse1", "Jockey1", "Trainer1"));
			horseService.saveHorse(new Horse("Horse2", "Jockey2", "Trainer2"));
			horseService.saveHorse(new Horse("Horse3", "Jockey3", "Trainer3"));
		};
	}
}
