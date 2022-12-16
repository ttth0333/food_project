package com.tringuyen.food_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FoodProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodProjectApplication.class, args);
	}

}
