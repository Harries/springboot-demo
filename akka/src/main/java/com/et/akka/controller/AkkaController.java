package com.et.akka.controller;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import com.et.akka.actor.ActorNormal;
import com.et.akka.actor.ActorStruct;

import com.et.akka.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

@RestController
public class AkkaController {

    @GetMapping(value = "/Akka/AkkaSendString")
    @ResponseBody
    public void AkkaSendString() {

        //Creates system management objects for all management actors
        ActorSystem actorSystem = ActorSystem.create();

        //use actorSystem.actorOf to define  actorNormal  as ActorRef
        ActorRef actor = actorSystem.actorOf(Props.create(ActorNormal.class), "actorNormal");
        //Send message Object msg (the content of the message, any type of data), final ActorRef sender (indicates that there is no sender (actually an Actor called deadLetters))
        actor.tell("kiba", ActorRef.noSender());
    }
    @GetMapping(value = "/Akka/AkkaSendInt")
    @ResponseBody
    public void AkkaSendInt() {

        ActorSystem actorSystem = ActorSystem.create();
        ActorRef actor = actorSystem.actorOf(Props.create(ActorNormal.class), "actorNormal");
        actor.tell(518, ActorRef.noSender());//send int
    }

    @GetMapping(value = "/Akka/AkkaAsk")
    @ResponseBody
    public void AkkaAsk() {

        ActorSystem actorSystem = ActorSystem.create();
        ActorRef actor = actorSystem.actorOf(Props.create(ActorNormal.class), "actorNormal");

        Timeout timeout = new Timeout(Duration.create(2, TimeUnit.SECONDS));
        Future<Object> future = Patterns.ask(actor, "hello", timeout);
        try {
            Object obj = Await.result(future, timeout.duration());
            String reply = obj.toString();
            System.out.println("reply msg: " + reply);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @GetMapping(value = "/Akka/AkkaAskStruct")
    @ResponseBody
    public void AkkaAskStruct() {

        ActorSystem actorSystem = ActorSystem.create();
        ActorRef actor = actorSystem.actorOf(Props.create(ActorStruct.class,new User(1,"kiba")), "actorNormal");

        Timeout timeout = new Timeout(Duration.create(2, TimeUnit.SECONDS));
        Future<Object> future = Patterns.ask(actor, "hello", timeout);
        try {
            Object obj = Await.result(future, timeout.duration());
            String reply = obj.toString();
            System.out.println("reply msg: " + reply);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}