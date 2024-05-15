package com.et.picocli.command;

import picocli.CommandLine;

@CommandLine.Command(
        subcommands = {
                GitAddCommand.class,
                GitCommitCommand.class
        }
)
public class GitCommand implements Runnable {
    public static void main(String[] args) {
        //CommandLine.run(new GitCommand(), args);
        int exitCode = new CommandLine(new GitCommand()).execute(args); // |7|
        System.exit(exitCode); // |8|
    }

    @Override
    public void run() {
        System.out.println("The popular git command");
    }
}