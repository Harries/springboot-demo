package com.et.akka.cluster;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.routing.RoundRobinPool;
import akka.routing.Router;

public class WorkerRouterActor extends AbstractActor {
    private final ActorRef router;

    public WorkerRouterActor(int numberOfWorkers) {
        this.router = getContext().actorOf(new RoundRobinPool(numberOfWorkers).props(WorkerActor.props()), "workerRouter");
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(TaskMessage.class, msg -> router.tell(msg, getSelf()))
                .match(Terminated.class, t -> getContext().stop(getSelf()))
                .build();
    }

    public static Props props(int numberOfWorkers) {
        return Props.create(WorkerRouterActor.class, numberOfWorkers);
    }
}
