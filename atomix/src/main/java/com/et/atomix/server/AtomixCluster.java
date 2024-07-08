package com.et.atomix.server;

import io.atomix.cluster.discovery.BootstrapDiscoveryProvider;
import io.atomix.core.Atomix;
import io.atomix.core.map.AsyncAtomicMap;
import io.atomix.primitive.Recovery;
import io.atomix.protocols.raft.MultiRaftProtocol;
import io.atomix.protocols.raft.partition.RaftPartitionGroup;
import io.atomix.utils.time.Versioned;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
//https://raft.github.io/
public class AtomixCluster {
    private static String LOCAL_DATA_DIR = "db/partitions/";
    private static String groupName = "raft";
    private static Integer MAX_RETRIES = 5;

    public static void main(String[] args) throws Exception {
        Atomix atomix = buildAtomix();
        //atomix启动并加入集群
        atomix.start().join();

        //创建atomixMap
        AsyncAtomicMap<Object, Object> asyncAtomicMap = atomix.atomicMapBuilder("myCfgName")
                .withProtocol(MultiRaftProtocol.builder(groupName)
                        .withRecoveryStrategy(Recovery.RECOVER)
                        .withMaxRetries(MAX_RETRIES)
                        .build())
                .withReadOnly(false)
                .build()
                .async();
        //进行数据存储
        asyncAtomicMap.put("HBLOG", "http://www.liuhaihua.cn");
        //进行查询
        CompletableFuture<Versioned<Object>> myBlog = asyncAtomicMap.get("HBLOG");
        Versioned<Object> objectVersioned = myBlog.get();
        System.out.printf("value:%s version:%s%n", objectVersioned.value(), objectVersioned.version());
    }

    private static Atomix buildAtomix() {
        List<String> raftMembers = Collections.singletonList("node1");
        //创建atomix
        return Atomix.builder(AtomixCluster.class.getClassLoader())
                .withClusterId("my-cluster")
                .withMemberId("node1")
                .withHost("127.0.0.1")
                .withPort(6789)
                .withMembershipProvider(BootstrapDiscoveryProvider.builder().build())
                .withManagementGroup(RaftPartitionGroup.builder("system")
                        .withNumPartitions(1)
                        .withDataDirectory(new File(LOCAL_DATA_DIR, "system"))
                        .withMembers(raftMembers)
                        .build())
                .addPartitionGroup(RaftPartitionGroup.builder(groupName)
                        .withNumPartitions(raftMembers.size())
                        .withDataDirectory(new File(LOCAL_DATA_DIR, "data"))
                        .withMembers(raftMembers)
                        .build())
                .build();
    }
}
