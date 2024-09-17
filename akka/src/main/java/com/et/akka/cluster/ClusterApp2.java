package com.et.akka.cluster;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.cluster.Cluster;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class ClusterApp2 {

    public static void main(String[] args) {
        // Load configuration
        Config config = ConfigFactory.load();
        ActorSystem system = ActorSystem.create("ClusterSystem", config);

        // Create WorkerRouterActor with 5 workers
        ActorRef workerRouter = system.actorOf(WorkerRouterActor.props(5), "workerRouter");

        // Create MasterActor
        ActorRef masterActor = system.actorOf(MasterActor.props(workerRouter), "masterActor");

        // Log cluster membership
        Cluster cluster = Cluster.get(system);
        System.out.println("Cluster initialized with self member: " + cluster.selfAddress());

        // Submit tasks
        masterActor.tell(new TaskMessage("Task 1"), ActorRef.noSender());
        masterActor.tell(new TaskMessage("Task 2"), ActorRef.noSender());
        masterActor.tell(new TaskMessage("Task 3"), ActorRef.noSender());

        // Keep system alive for demonstration purposes
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        system.terminate();
    }
}
