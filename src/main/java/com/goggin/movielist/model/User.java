package com.goggin.movielist.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.ToString;

@Entity
@Table(name = "users")
@ToString
public class User {

    @Id // used to mark it as the primary key of the table
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer user_id; // only integer as takes less storage and am not anticipating loads of accounts
    private String username;
    private String password;

    // cascadetype means if i delete a movieconnection record, all connections to a
    // user will be deleted too
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<MovieConnection> favouriteMovies;

    // In order to be compatible with certain frameworks or tools that use
    // reflection to create instances of classes, it's a convention to provide a
    // default (empty) constructor in addition to any parameterized constructors.
    // Hibernate expects entities to have a no-arg constructor
    public User() {

    }

    public User(Integer user_id, String username, String password) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
