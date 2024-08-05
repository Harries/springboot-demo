package com.et.protobuf;

import java.util.List;

/**
 * @author liuhaihua
 * @version 1.0
 * @ClassName StudentJson
 * @Description todo
 * @date 2024/08/05/ 16:32
 */
public class StudentJson {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private List<PhoneNumJson> phoneNumList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<PhoneNumJson> getPhoneNumList() {
        return phoneNumList;
    }

    public void setPhoneNumList(List<PhoneNumJson> phoneNumList) {
        this.phoneNumList = phoneNumList;
    }
}
