package com.et.akka.remoting;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;

public class ClientApp {

    static class ClientActor extends AbstractActor {

        private final String serverActorPath;

        public ClientActor(String serverActorPath) {
            this.serverActorPath = serverActorPath;
        }

        @Override
        public void preStart() {
            ActorSelection serverActor = getContext().actorSelection(serverActorPath);
            serverActor.tell("Hello from Client", getSelf());
        }

        @Override
        public Receive createReceive() {
            return receiveBuilder()
                    .match(String.class, msg -> {
                        System.out.println("Client received message: " + msg);
                    })
                    .build();
        }
    }

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("RemoteSystem", ConfigFactory.load("client"));
        String serverPath = "akka://RemoteSystem@127.0.0.1:2552/user/serverActor";
        ActorRef clientActor = system.actorOf(Props.create(ClientActor.class, serverPath), "clientActor");
        System.out.println("Client is running...");
    }
}
