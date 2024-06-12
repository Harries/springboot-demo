/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.et.vavr.service;

import com.et.vavr.domain.User;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Admin
 */
@Service
public class GithubService {

    @Autowired
    private RestTemplate restTemplate;

    public Try<User> findGithubUser(String username) {
        return Try.of(() -> restTemplate.getForObject("https://api.github.com/users/{username}", User.class, username));

    }

    public Try<User> findGithubUserAndFail(String username) {
        return Try.of(() -> restTemplate.getForObject("https://api.twitter.com/users/fail/{username}", User.class, username));
    }

}