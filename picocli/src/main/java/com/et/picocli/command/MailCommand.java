package com.et.picocli.command;

import com.et.picocli.service.IMailService;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import picocli.CommandLine;
import picocli.CommandLine.*;
import java.util.List;
import java.util.concurrent.Callable;

@Component 
//@Command(name = "mailCommand")
@CommandLine.Command(
        subcommands = {
                GitAddCommand.class,
                GitCommitCommand.class
        }
)
public class

MailCommand implements Callable<Integer> {

    @Autowired
    private IMailService mailService;

    @Option(names = "--to", description = "email(s) of recipient(s)", required = true)
    List<String> to;

    @Option(names = "--subject", description = "Subject")
    String subject;

    @Parameters(description = "Message to be sent")
    String[] body = {};

    public Integer call() throws Exception {
        mailService.sendMessage(to, subject, String.join(" ", body)); 
        return 0;
    }
}