package com.goggin.movielist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.goggin.movielist")
public class MovielistApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovielistApplication.class, args);
	}

}
