package com.et.zookeeper.api;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

@Slf4j
public class WatcherApi implements Watcher {

    @Override
    public void process(WatchedEvent event) {
        log.info("【Watcher event】={}", event.getState());
        log.info("【Watcher  path】={}", event.getPath());
        log.info("【Watcher type】={}", event.getType()); //  three type： create，delete，update
    }
}