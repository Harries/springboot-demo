package com.et.python.util;

import org.python.util.PythonInterpreter;

public class JavaPythonFile {

    public static void main(String[] args) {
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.execfile("D:\\IdeaProjects\\ETFramework\\python\\src\\main\\resources\\py\\test.py");
    }
}