package com.et.selenium.config;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.SimpleThreadScope;

// custom scope to prevent multiple browsers from launching
// see video: https://bah.udemy.com/course/cucumber-with-spring-boot/learn/lecture/20184630#overview
public class BrowserScope extends SimpleThreadScope {

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        return super.get(name, objectFactory);
        // Object o = super.get(name, objectFactory);
        //
        // SessionId sessionId = ((RemoteWebDriver)o).getSessionId();
        // if (Objects.isNull(sessionId)){
        //     super.remove(name);
        //     super.get(name,objectFactory);
        // }
        // return o;
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
    }
}
