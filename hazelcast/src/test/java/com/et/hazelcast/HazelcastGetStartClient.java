package com.et.hazelcast;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class HazelcastGetStartClient {
    public static void main(String[] args) {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setClusterName("hazelcast-cluster");
        HazelcastInstance instance = HazelcastClient.newHazelcastClient(clientConfig);
        Map<Integer, String> clusterMap = instance.getMap("map");
    }

}