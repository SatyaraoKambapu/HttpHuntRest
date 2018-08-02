package com.thoughtworks.httphuntrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point to play the all stages given by thoughtworks. Each stage we call
 * the Http REST endpoints.
 * 
 */
@SpringBootApplication
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
