package com.et.akka.cluster;

import akka.actor.typed.ActorSystem;
import akka.cluster.ClusterEvent;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;

public class ClusterApp {

    public static void main(String[] args) {
        // 读取外部配置文件 node1.conf 启动第一个节点
        Config configNode1 = ConfigFactory.parseFile(new File("/Users/liuhaihua/IdeaProjects/springboot-demo/akka/src/main/resources/node1.conf"))
            .withFallback(ConfigFactory.load());

        ActorSystem<ClusterEvent.ClusterDomainEvent> systemNode1 = ActorSystem.create(ClusterListener.create(), "ClusterSystem", configNode1);
        System.out.println("Node 1 started with config from node1.conf");

        // 读取外部配置文件 node2.conf 启动第二个节点
        Config configNode2 = ConfigFactory.parseFile(new File("D:/IdeaProjects/ETFramework/akka/src/main/resources/node2.conf"))
            .withFallback(ConfigFactory.load());

        ActorSystem<ClusterEvent.ClusterDomainEvent> systemNode2 = ActorSystem.create(ClusterListener.create(), "ClusterSystem", configNode2);
        System.out.println("Node 2 started with config from node2.conf");
    }
}
