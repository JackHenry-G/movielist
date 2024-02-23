package com.goggin.movielist.service;

import java.util.ArrayList;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import com.goggin.movielist.exception.UsernameAlreadyExistsException;
import com.goggin.movielist.model.User;
import com.goggin.movielist.respositories.UserRepository;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    public void saveNewUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists!");
        } else {
            String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            userRepository.save(user);
        }
    }

    public void updateUser(User updatedUser) throws UsernameAlreadyExistsException {
        User currentUser = getCurrentUser();
        log.info("Update user called...");
        log.info("Current user: {}", currentUser);
        log.info("Requested to update to this user: {}", updatedUser);

        String currentUsername = currentUser.getUsername();
        String newUsername = updatedUser.getUsername();
        if (!StringUtils.isBlank(newUsername)) {
            if (userRepository.existsByUsername(newUsername)) {
                log.warn("User tried to change to a username that is taken by somebody else!");
                throw new UsernameAlreadyExistsException("New username is taken by somebody else!");
            } else if (newUsername.equals(currentUser.getUsername())) {
                log.warn("User tried to change to their own username!");
                throw new UsernameAlreadyExistsException("You cannot change to your existing username!");
            } else {
                // get current authentication from the security context
                Authentication oldAuth = SecurityContextHolder.getContext().getAuthentication();
                log.info("Current authentication succesfully retrieved.");

                // change the username
                currentUser.setUsername(newUsername); // change field
                userRepository.save(currentUser); // save to database
                log.info(
                        "Changed user's name in the database succesfully from '{}' to '{}'. Still need to set the new authetnication token.",
                        currentUsername, newUsername);

                // Create and set the new authentication token with the new username
                // this prevents user from being logged out once the details change
                UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(newUsername,
                        oldAuth.getCredentials(), oldAuth.getAuthorities());
                log.info("New authentication succesfully created. Not yet set.");

                SecurityContextHolder.getContext().setAuthentication(newAuth);
                log.info("New authentication succesfully set.");
            }
        }
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            // Handle the case where there is no authenticated user
            return null;
        }

        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Return a UserDetails object without authorities
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());

    }

}
