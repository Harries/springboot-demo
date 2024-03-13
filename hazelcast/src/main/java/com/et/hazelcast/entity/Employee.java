package com.et.hazelcast.entity;
import java.io.Serializable;
public class Employee implements Serializable{
   private static final long serialVersionUID = 1L;
   private int empId;
   private String name;
   private String department;
   public Employee(Integer id, String name, String department) {
      super();
      this.empId = id;
      this.name = name;
      this.department = department;
   }
   public int getEmpId() {
      return empId;
   }
   public void setEmpId(int empId) {
      this.empId = empId;
   }
   public String getName() {
      return name;
   }
   public void setName(String name) {
      this.name = name;
   }
   public String getDepartment() {
      return department;
   }
   public void setDepartment(String department) {
      this.department = department;
   }
   @Override
   public String toString() {
      return "Employee [empId=" + empId + ", name=" + name + ", department=" + department + "]";
   }
}