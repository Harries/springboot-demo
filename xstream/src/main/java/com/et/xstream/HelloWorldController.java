package com.et.xstream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloWorldController {
    @RequestMapping(value = "/hello" ,produces = {"application/xml;"})
    public String showHelloWorld(){
        Employee e1 = new Employee("Sanyog", "Gautam", 1000,
                19, "Male");

        // Serializing a Java object into XML
        XStream xStream = new XStream(new DomDriver());
        String xml = xStream.toXML(e1); // Converting it to XML

        return xml;
    }
}