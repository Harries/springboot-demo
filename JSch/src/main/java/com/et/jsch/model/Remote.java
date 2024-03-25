package com.et.jsch.model;

import lombok.Data;

@Data
public class Remote {
  private String host;
  private final int port = 22;
  private String user;
  private String password;
  private final String identity = "~/.ssh/id_rsa";
  private String passphrase;
}