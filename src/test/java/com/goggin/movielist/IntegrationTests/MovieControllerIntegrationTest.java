package com.goggin.movielist.IntegrationTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.goggin.movielist.controller.MovieController;
import com.goggin.movielist.respositories.MovieConnectionRepository;
import com.goggin.movielist.respositories.MovieRepository;
import com.goggin.movielist.respositories.UserRepository;
import com.goggin.movielist.service.EmailService;

@WebMvcTest(MovieController.class)
public class MovieControllerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private MovieRepository movieRepository;

    @MockBean
    private MovieConnectionRepository movieConnectionRepository;

    @MockBean
    private EmailService EmailService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @WithMockUser // mocks an authenticated user
    public void testGetHomePage() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index.html"));

    }

}
