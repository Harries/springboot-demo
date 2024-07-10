package com.et.pf4j.service;

import com.et.pf4j.Greeting;
import org.pf4j.Extension;

@Extension
public class WelcomeGreeting implements Greeting {

    public String getGreeting() {
        return "Welcome";
    }

}