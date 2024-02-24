package com.goggin.movielist.IntegrationTests;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.goggin.movielist.controller.MovieController;

@SpringBootTest // tells spring boot to look for a main config claass (@SpringBootApplication)
				// and use that to start a spring application context
class MovielistApplicationIntegrationTests {

	@Autowired // inject the controller before the test runs e.g. = new MovieController()
	private MovieController movieController;

	@Test
	void contextLoads() {
		// firstly, there's no assertthat() for this, but it will do a simple sanity
		// check that the applicaitoncontext is started

		assertThat(movieController).isNotNull(); // does the context create the controller?

	}

}
