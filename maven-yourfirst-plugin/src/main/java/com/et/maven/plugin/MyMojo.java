package com.et.maven.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name="maven-yourfirst-plugin")
public class MyMojo extends AbstractMojo {

    @Parameter
    private String msg;
    public void execute() throws MojoExecutionException,MojoFailureException{
        System.out.println("execute success:"+msg);
    }
}