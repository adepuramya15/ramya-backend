//package com.accesshr.emsbackend.Entity;
//
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "employee")
//public class Employee {
//
//    @Id
//    @Column(name="employee_id", length = 45)
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private int employeeid;
//
//    @Column(name="employee_name", length = 255)
//    private String employeename;
//
//    @Column(name="email", length = 255)
//    private String email;
//
//    @Column(name="companyname", length = 255)
//    private String companyname;
//
//    @Column(name="password", length = 255)
//    private String password;
//
//    @Column(name="role", length = 255) // Add this line for the role
//    private String role; // Add the role field
//
//    // Updated constructor to include role
//    public Employee(int employeeid, String employeename, String email, String companyname, String password, String role) {
//        this.employeeid = employeeid;
//        this.employeename = employeename;
//        this.email = email;
//        this.companyname = companyname;
//        this.password = password;
//        this.role = role; // Initialize the role
//    }
//
//    public Employee() {
//    }
//
//    public int getEmployeeid() {
//        return employeeid;
//    }
//
//    public void setEmployeeid(int employeeid) {
//        this.employeeid = employeeid;
//    }
//
//    public String getEmployeename() {
//        return employeename;
//    }
//
//    public void setEmployeename(String employeename) {
//        this.employeename = employeename;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getCompanyname() {
//        return companyname;
//    }
//
//    public void setCompanyname(String companyname) {
//        this.companyname = companyname;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getRole() { // Add getter for role
//        return role;
//    }
//
//    public void setRole(String role) { // Add setter for role
//        this.role = role;
//    }
//
//    @Override
//    public String toString() {
//        return "Employee{" +
//                "employeeid=" + employeeid +
//                ", employeename='" + employeename + '\'' +
//                ", email='" + email + '\'' +
//                ", companyname='" + companyname + '\'' +
//                ", password='" + password + '\'' +
//                ", role='" + role + '\'' + // Include role in toString
//                '}';
//    }
//}
