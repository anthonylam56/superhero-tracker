package com.cec.superhero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

//@EntityScan("")
@SpringBootApplication
public class SuperheroApplication {

	public static void main(String[] args) {
		SpringApplication.run(SuperheroApplication.class, args);
	}

}
