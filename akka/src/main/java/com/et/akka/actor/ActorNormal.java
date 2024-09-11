package com.et.akka.actor;

import akka.actor.AbstractActor;

public class ActorNormal extends AbstractActor {

    //process msg
    @Override
    public Receive createReceive() {
        //Process a specific type of message, such as a string type message
        Receive build = receiveBuilder().match(String.class,(msg)-> {
            System.out.println(msg);
            sender().tell("response", self());
        }).match(Integer.class,(msg)-> {
            System.out.println(msg+"1");
        }).build();
        return build;
    }
}