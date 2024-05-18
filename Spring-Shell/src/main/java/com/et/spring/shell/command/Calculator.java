package com.et.spring.shell.command;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import javax.validation.constraints.Size;
import java.util.List;

@ShellComponent
@ShellCommandGroup("Calculator")
public class Calculator {
    // 为一个命令指定多个名称
    //@ShellMethod(value = "Add numbers.", key = {"sum", "addition"})
    @ShellMethod(value = "Add numbers.",  key = {"sum", "addition"}, prefix = "-", group = "Cal")
    public void add(int a, int b) {
        int sum = a + b;
        System.out.println(String.format("%d + %d = %d", a, b, sum));
    }

    @ShellMethod("Echo command help")
    public void myhelp(@ShellOption({"-C", "--command"}) String cmd) {
        System.out.println(cmd);
    }

    // 参数为集合
    @ShellMethod("Add by list")
    public void addByList(@ShellOption(arity = 3) List<Integer> numbers) {
        int s = 0;
        for(int number : numbers) {
            s += number;
        }
        System.out.println(String.format("s=%d", s));
    }
    @ShellMethod("Echo.")
    public void echo(String what) {
        System.out.println(what);
    }
    // 使用@Size注解校验参数长度
    @ShellMethod("Change password")
    public void changePwd(@Size(min = 6, max = 30) String pwd) {
        System.out.println(pwd);
    }
}