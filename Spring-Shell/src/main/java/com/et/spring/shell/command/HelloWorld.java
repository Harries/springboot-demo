package com.et.spring.shell.command;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import javax.validation.constraints.Size;
import java.util.List;

@ShellComponent
@ShellCommandGroup("HelloWorld")
public class HelloWorld {

    @ShellMethod("Say hello")
    public void hello(@ShellOption(defaultValue = "World")String name) {
        System.out.println("hello, " + name + "!");
    }

}
