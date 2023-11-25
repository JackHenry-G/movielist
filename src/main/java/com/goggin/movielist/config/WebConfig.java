package com.goggin.movielist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebConfig {
    // https://howtodoinjava.com/spring-boot2/resttemplate/spring-restful-client-resttemplate-example/

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
