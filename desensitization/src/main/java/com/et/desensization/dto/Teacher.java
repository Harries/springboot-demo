package com.et.desensization.dto;

import red.zyc.desensitization.annotation.*;

public class Teacher {

    private Integer id;
    
    @ChineseNameSensitive
    private String name;
    
    @IdCardNumberSensitive
    private String idCard;
    
    @PhoneNumberSensitive
    private String tel;
    
    @EmailSensitive
    private String email;
    
    @PasswordSensitive
    private String password;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Teacher() {
    }

    public Teacher(Integer id, String name, String idCard, String tel, String email, String password) {
        this.id = id;
        this.name = name;
        this.idCard = idCard;
        this.tel = tel;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", idCard='" + idCard + '\'' +
                ", tel='" + tel + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
