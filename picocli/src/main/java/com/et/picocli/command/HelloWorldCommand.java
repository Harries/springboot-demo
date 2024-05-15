package com.et.picocli.command;

import picocli.CommandLine;
@CommandLine.Command(
        name = "hello",
        description = "Says hello"
)
public class HelloWorldCommand implements Runnable {
    public static void main(String[] args) {
        CommandLine.run(new HelloWorldCommand(), args);
    }

    @Override
    public void run() {
        System.out.println("Hello World!");
    }
}