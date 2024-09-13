package com.et.akka.cluster;

import akka.actor.typed.ActorSystem;
import akka.cluster.ClusterEvent;
import com.typesafe.config.ConfigFactory;

public class ClusterApp {
    public static void main(String[] args) {
        // Load the configuration file for the node (can vary based on args)
        ActorSystem<ClusterEvent.ClusterDomainEvent> system = ActorSystem.create(ClusterListener.create(), "ClusterSystem", ConfigFactory.load());

        System.out.println("Cluster node started.");
    }
}
