package com.et.groovy.service;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    public String testQuery(long id){
        return "Test query success, id is " + id;
    }

    public static void main(String[] args) {
        Binding groovyBinding = new Binding();
        groovyBinding.setVariable("testService", new TestService());
        GroovyShell groovyShell = new GroovyShell(groovyBinding);

        String scriptContent = "import pers.doublebin.example.groovy.script.service.TestService\n" +
                "def query = new TestService().testQuery(1L);\n" +
                "query";

        /*String scriptContent = "def query = testService.testQuery(2L);\n" +
                "query";*/

        Script script = groovyShell.parse(scriptContent);
        System.out.println(script.run());
    }
}
