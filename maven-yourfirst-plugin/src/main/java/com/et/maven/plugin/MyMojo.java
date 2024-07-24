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
    //https://griabcrh.github.io/2018/09/16/Maven%E8%87%AA%E5%AE%9A%E4%B9%89%E6%8F%92%E4%BB%B6%E5%AE%9E%E7%8E%B0/
    public void execute() throws MojoExecutionException,MojoFailureException{
        System.out.println("execute success:"+msg);
    }
}