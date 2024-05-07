package com.et.ldap.entity;
 
import lombok.Data;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.DnAttribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;
 
import javax.naming.Name;
import java.io.Serializable;
 

@Data
@Entry(base = "ou=people", objectClasses="inetOrgPerson")
public class Person implements Serializable {
 
    private static final long serialVersionUID = -337113594734127702L;
 
    /**
     *neccesary
     */
    @Id
    private Name id;
 
    @DnAttribute(value = "uid", index = 3)
    private String uid;
 
    @Attribute(name = "cn")
    private String commonName;
 
    @Attribute(name = "sn")
    private String suerName;
 
    private String userPassword;
 
}