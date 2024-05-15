package com.et.picocli.command;

import picocli.CommandLine;

@CommandLine.Command(
  name = "add"
)
public class GitAddCommand implements Runnable {
    @Override
    public void run() {
        System.out.println("Adding some files to the staging area");
    }
}