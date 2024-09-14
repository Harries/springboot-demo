package com.et.akka.cluster;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class TaskMessage {
    public final String task;

    public TaskMessage(String task) {
        this.task = task;
    }
}
