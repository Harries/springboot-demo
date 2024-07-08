package om.et.rmi.common;

import java.io.Serializable;

public class CustomerDTO implements Serializable {

    private String firstName;
    private String lastName;
    private String socialSecurityCode;

    public CustomerDTO() {
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

    public String getSocialSecurityCode() {
        return socialSecurityCode;
    }

    public void setSocialSecurityCode(String socialSecurityCode) {
        this.socialSecurityCode = socialSecurityCode;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CustomerDTO{");
        sb.append("firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", socialSecurityCode='").append(socialSecurityCode).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
