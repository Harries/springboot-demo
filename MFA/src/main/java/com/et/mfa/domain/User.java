package com.et.mfa.domain;


public class User {
    private String login;
    private String password;
    private String secret;

    public User(String login, String password, String secret) {
        this.login = login;
        this.password = password;
        this.secret = secret;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getSecret() {
        return secret;
    }
}
