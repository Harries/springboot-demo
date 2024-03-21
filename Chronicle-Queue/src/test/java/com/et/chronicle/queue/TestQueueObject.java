package com.et.chronicle.queue;

import com.alibaba.fastjson.JSONObject;
import com.et.chronicle.queue.entity.Person;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.openhft.chronicle.core.OS;
import net.openhft.chronicle.queue.ChronicleQueue;
import net.openhft.chronicle.queue.ExcerptAppender;
import net.openhft.chronicle.queue.ExcerptTailer;
import net.openhft.chronicle.queue.RollCycles;

class TestQueueObject {
  ChronicleQueue queue;

  @BeforeEach
  void setUp() throws Exception {
    String basePath = OS.getTarget() + "/QueueDocument";
    queue = ChronicleQueue.singleBuilder(basePath).rollCycle(RollCycles.FIVE_MINUTELY).build();
  }

  @AfterEach
  void tearDown() throws Exception {
    queue.close();
  }

  /**
   * 测试读写实现了Marshallable接口的对象
   */
  @Test
  void testMarshallable() {
    ExcerptAppender appender = queue.acquireAppender();

    try {
      for (int i = 0; i < 5; i++) {
        Person person = new Person();
        person.setName("Rob");
        person.setAge(40 + i);
        appender.writeDocument(person);
      }
    } finally {
      appender.close();
    }

    ExcerptTailer tailer = queue.createTailer("reader1");
    try {
      Person person2 = new Person();
      while (tailer.readDocument(person2)) {
        System.out.println(JSONObject.toJSON(person2));
      }
    } finally {
      appender.close();
    }
  }
}
