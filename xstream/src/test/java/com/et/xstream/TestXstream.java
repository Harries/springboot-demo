package com.et.xstream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author liuhaihua
 * @version 1.0
 * @ClassName TestXstream
 * @Description todo
 * @date 2024/06/14日 10:44
 */

public class TestXstream {
    public static void main(String[] args) {
        // Initializing XStream with Dom driver
        XStream xStream = new XStream(new DomDriver());

        // We need to configure the security framework in
        // XStream, so it deserializes the object from the
        // XML
        xStream.allowTypes(new Class[] { Employee.class });

        // Now, to make the XML outputted by XStream more
        // concise, you can create aliases for your custom
        // class names to XML element names. This is the
        // only type of mapping required to use XStream and
        // even this is optional.
        xStream.alias("employee", Employee.class);

        Employee e1 = new Employee("Sanyog", "Gautam", 1000,
                19, "Male");

        // Serializing a Java object into XML
        String xml
                = xStream.toXML(e1); // Converting it to XML

        // Deserializing a Java object from XML
        Employee employee = (Employee)xStream.fromXML(xml);

        System.out.println("First name of Employee:  "
                + employee.getFirstName());
        System.out.println("Last name of Employee:   "
                + employee.getLastName());
        System.out.println("Employee's age:          "
                + employee.getAge());
        System.out.println("Employee's gender:       "
                + employee.getGender());
        System.out.println("Employee's salary:       $"
                + employee.getSalary());
    }
}
