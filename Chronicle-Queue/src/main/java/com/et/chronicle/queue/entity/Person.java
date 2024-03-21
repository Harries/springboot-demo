package com.et.chronicle.queue.entity;

import net.openhft.chronicle.wire.Marshallable;

/**
 * 该类实现 `net.openhft.chronicle.wire.Marshallable` 并覆盖 `toString` 方法以实现更高效的序列化
 */
public class Person implements Marshallable {
  private String name;
  private int    age;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public Person() {
    super();
  }

  @Override
  public String toString() {
    return Marshallable.$toString(this);
  }
}
