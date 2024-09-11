package com.et.akka.actor;

import akka.actor.AbstractActor;
import com.et.akka.model.User;


public class ActorStruct extends AbstractActor {
    private final User user;

    public ActorStruct(User userModel){
        this.user = userModel;
    }

    //process msg
    @Override
    public Receive createReceive() {

        Receive build = receiveBuilder().match(String.class,(msg)-> {
            System.out.println(msg);
            sender().tell(" I am  a result of ActorStruct:"+user.getName(), self());
        }).match(Integer.class,(msg)-> {
            System.out.println(msg+"1");
        }).build();
        return build;
    }
}