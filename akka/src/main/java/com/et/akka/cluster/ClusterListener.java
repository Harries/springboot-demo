package com.et.akka.cluster;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import akka.cluster.typed.Cluster;
import akka.cluster.typed.Subscribe;
import akka.cluster.ClusterEvent;

public class ClusterListener extends AbstractBehavior<ClusterEvent.ClusterDomainEvent> {

    public ClusterListener(ActorContext<ClusterEvent.ClusterDomainEvent> context) {
        super(context);

        Cluster cluster = Cluster.get(context.getSystem());
        cluster.subscriptions().tell(Subscribe.create(getContext().getSelf(), ClusterEvent.ClusterDomainEvent.class));
    }

    @Override
    public Receive<ClusterEvent.ClusterDomainEvent> createReceive() {
        return newReceiveBuilder()
                .onMessage(ClusterEvent.MemberUp.class, this::onMemberUp)
                .onMessage(ClusterEvent.MemberRemoved.class, this::onMemberRemoved)
                .onAnyMessage(event -> {
                    System.out.println("Received cluster event: " + event);
                    return this;
                })
                .build();
    }

    private Behavior<ClusterEvent.ClusterDomainEvent> onMemberUp(ClusterEvent.MemberUp memberUp) {
        System.out.println("Member is Up: " + memberUp.member());
        return this;
    }

    private Behavior<ClusterEvent.ClusterDomainEvent> onMemberRemoved(ClusterEvent.MemberRemoved memberRemoved) {
        System.out.println("Member is Removed: " + memberRemoved.member());
        return this;
    }

    public static Behavior<ClusterEvent.ClusterDomainEvent> create() {
        return Behaviors.setup(ClusterListener::new);
    }
}
