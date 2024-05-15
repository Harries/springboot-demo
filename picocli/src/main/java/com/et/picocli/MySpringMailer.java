package com.et.picocli;

import com.et.picocli.command.MailCommand;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import picocli.CommandLine;
import picocli.CommandLine.IFactory;

@SpringBootApplication
public class MySpringMailer implements CommandLineRunner, ExitCodeGenerator {

    private IFactory factory;        
    private MailCommand mailCommand;
    private int exitCode;

    // constructor injection
    MySpringMailer(IFactory factory, MailCommand mailCommand) {
        this.factory = factory;
        this.mailCommand = mailCommand;
    }

    @Override
    public void run(String... args) {
        // let picocli parse command line args and run the business logic
        exitCode = new CommandLine(mailCommand, factory).execute(args);
    }

    @Override
    public int getExitCode() {
        return exitCode;
    }

    public static void main(String[] args) {
        // let Spring instantiate and inject dependencies
        System.exit(SpringApplication.exit(SpringApplication.run(MySpringMailer.class, args)));
    }
}