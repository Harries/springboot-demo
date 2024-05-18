package com.et.spring.shell.command;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@ShellCommandGroup("Downloader")
public class Downloader {
    private boolean connected = false;

    @ShellMethod("Connect server")
    public void connect() {
        connected = true;
    }

    @ShellMethod("Download file")
    public void download() {
        System.out.println("Downloaded.");
    }

    // 为命令download提供可用行支持
    public Availability downloadAvailability() {
        return connected ? Availability.available():Availability.unavailable("you are not connected");
    }
}