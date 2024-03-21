package com.et.chronicle.queue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.openhft.chronicle.core.OS;
import net.openhft.chronicle.queue.ChronicleQueue;
import net.openhft.chronicle.queue.ExcerptAppender;
import net.openhft.chronicle.queue.ExcerptTailer;
import net.openhft.chronicle.queue.RollCycles;

class TestQueueMain {
  ChronicleQueue queue;

  @BeforeEach
  void setUp() throws Exception {
    String basePath = OS.getTarget() + "/Queue1";
    queue = ChronicleQueue.singleBuilder(basePath).rollCycle(RollCycles.FIVE_MINUTELY).build();
  }

  @Test
  void tearDown() throws Exception {
    queue.close();
  }

  /**
   * 测试最简单的写入字符串
   */
  @Test
  void testWtite() {
    ExcerptAppender appender = queue.acquireAppender();
    try {
      for (int i = 0; i < 1000000000; i++) {
        appender.writeText("Hello World(hello world)!--" + i);
      }
    } finally {
      appender.close();
    }
  }

  /**
   * 测试最简单的读取字符串
   */
  @Test
  void testRead() {
    ExcerptTailer tailer = queue.createTailer("reader1"); //@wjw_note: 如果是createTailer()方法,没有给定名称,会一直能读到最后的数据而不会移动索引
    try {
      String readText = null;
      while ((readText = tailer.readText()) != null) {
        System.out.println("read: " + readText);
      }
    } finally {
      tailer.close();
    }
  }

}

