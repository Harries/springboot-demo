package com.et.akka.cluster;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class WorkerActor extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    public static Props props() {
        return Props.create(WorkerActor.class);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(TaskMessage.class, msg -> {
                    log.info("Processing task: {}", msg.task);
                    // Simulate task processing
                    Thread.sleep(1000);
                    log.info("Task completed: {}", msg.task);
                })
                .build();
    }
}
