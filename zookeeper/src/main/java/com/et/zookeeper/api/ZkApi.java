package com.et.zookeeper.api;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@Slf4j
public class ZkApi {

    @Autowired
    private ZooKeeper zkClient;

    /**
     * check node is exist
     *
     * @param path
     * @param needWatch
     * @return
     */
    public Stat exists(String path, boolean needWatch) {
        try {
            return zkClient.exists(path, needWatch);
        } catch (Exception e) {
            log.error("【 node exception】{},{}", path, e);
            return null;
        }
    }

    /**
     * check node is exist and set watcher
     * @param path
     * @param watcher
     * @return
     */
    public Stat exists(String path, Watcher watcher) {
        try {
            return zkClient.exists(path, watcher);
        } catch (Exception e) {
            log.error("【node  exception】{},{}", path, e);
            return null;
        }
    }

    /**
     * create persist node
     *
     * @param path
     * @param data
     */
    public boolean createNode(String path, String data) {
        try {
            zkClient.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            return true;
        } catch (Exception e) {
            log.error("【create persist node exception】{},{},{}", path, data, e);
            return false;
        }
    }

    /**
     * update persist node
     *
     * @param path
     * @param data
     */
    public boolean updateNode(String path, String data) {
        try {
            //The data version of zk starts counting from 0. If the client passes -1, it means that the zk server needs to be updated based on the latest data. If there is no atomicity requirement for the update operation of zk's data node, you can use -1.
            //The version parameter specifies the version of the data to be updated. If the version is different from the real version, the update operation will fail. Specify version as -1 to ignore the version check.
            zkClient.setData(path, data.getBytes(), -1);
            return true;
        } catch (Exception e) {
            log.error("【update persist node exception】{},{},{}", path, data, e);
            return false;
        }
    }

    /**
     * delete persist node
     *
     * @param path
     */
    public boolean deleteNode(String path) {
        try {
            //The version parameter specifies the version of the data to be updated. If the version is different from the real version, the update operation will fail. Specify version as -1 to ignore the version check.
            zkClient.delete(path, -1);
            return true;
        } catch (Exception e) {
            log.error("【delete persist node exception】{},{}", path, e);
            return false;
        }
    }

    /**
     * Get the child nodes of the current node (excluding grandchild nodes)
     *
     * @param path
     */
    public List<String> getChildren(String path) throws KeeperException, InterruptedException {
        List<String> list = zkClient.getChildren(path, false);
        return list;
    }

    /**
     * Get the value of the specified node
     *
     * @param path
     * @return
     */
    public String getData(String path, Watcher watcher) {
        try {
            Stat stat = new Stat();
            byte[] bytes = zkClient.getData(path, watcher, stat);
            return new String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}