package com.et.akka.remoting;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;

public class ServerApp {

    static class ServerActor extends AbstractActor {
        @Override
        public Receive createReceive() {
            return receiveBuilder()
                    .match(String.class, msg -> {
                        System.out.println("Server received message: " + msg);
                        getSender().tell("Hello from Server", getSelf());
                    })
                    .build();
        }
    }

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("RemoteSystem", ConfigFactory.load("server"));
        ActorRef serverActor = system.actorOf(Props.create(ServerActor.class), "serverActor");
        System.out.println("Server is running...");
    }
}
