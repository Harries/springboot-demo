package com.et.lombok.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;


@ToString(exclude="id")
@EqualsAndHashCode
public class ToStringExample {
  private static final int STATIC_VAR = 10;
  private String name;
  private String[] tags;
  private int id;

}
