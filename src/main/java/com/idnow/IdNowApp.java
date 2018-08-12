package com.idnow;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
@Configuration
public class IdNowApp implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(IdNowApp.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		
	
	}

}
