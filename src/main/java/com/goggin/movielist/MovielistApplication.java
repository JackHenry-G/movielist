package com.goggin.movielist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@ComponentScan(basePackages = "com.goggin.movielist")
@Slf4j
public class MovielistApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovielistApplication.class, args);
		log.info("App started running!");
	}

}
