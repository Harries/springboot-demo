package com.et.rabbitmq.resources;

import java.util.concurrent.CountDownLatch;

public class TaskManager {

    private final CountDownLatch latch = new CountDownLatch(1);

    public void startTask() {
        // 启动任务
        new Thread(() -> {
            try {
                // 模拟任务处理
                Thread.sleep(50000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                latch.countDown();
            }
        }).start();
    }

    public void waitForTasksToComplete() throws InterruptedException {
        latch.await();
    }
}