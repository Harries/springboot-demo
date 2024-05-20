package com.et.validation.error;

import lombok.Data;

import java.io.Serializable;
@Data
public class Violation implements Serializable {

  private final String fieldName;

  private final String message;


  public Violation(String fieldName, String message) {
    this.fieldName = fieldName;
    this.message = message;
  }
}