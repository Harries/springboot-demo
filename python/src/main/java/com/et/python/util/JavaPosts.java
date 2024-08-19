package com.et.python.util;

import org.python.util.PythonInterpreter;

import java.util.Properties;

public class JavaPosts {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("python.console.encoding", "UTF-8"); // Used to prevent: console: Failed to install '': java.nio.charset.UnsupportedCharsetException: cp0.
        props.put("python.security.respectJavaAccessibility", "false"); //don't respect java accessibility, so that we can access protected members on subclasses
        props.put("python.import.site","false");
        Properties preprops = System.getProperties();
        PythonInterpreter.initialize(preprops, props, new String[0]);
        PythonInterpreter interp = new PythonInterpreter();
        interp.exec("import sys");
        interp.exec("sys.path.append('D:\\huiyida\\develop\\python\\python312.zip')");
        interp.exec("sys.path.append('D:\\huiyida\\develop\\python\\DLLs')");
        interp.exec("sys.path.append('D:\\huiyida\\develop\\python\\Lib')");
        interp.exec("sys.path.append('D:\\huiyida\\develop\\python')");
        interp.exec("sys.path.append('D:\\huiyida\\develop\\python\\Lib\\site-packages')");
       // interp.exec("sys.path.append('F:/workspace/wxserver/WebContent/py')");//我们自己写的
        interp.execfile("D:\\IdeaProjects\\ETFramework\\python\\src\\main\\resources\\py\\posts.py");
    }
}