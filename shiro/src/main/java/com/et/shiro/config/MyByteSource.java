package com.et.shiro.config;

import org.apache.shiro.util.SimpleByteSource;

import java.io.Serializable;

public class MyByteSource extends SimpleByteSource implements Serializable {
    public MyByteSource(String string) {
        super(string);
    }
}
