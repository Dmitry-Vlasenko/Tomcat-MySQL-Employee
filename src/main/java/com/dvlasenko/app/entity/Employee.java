package com.dvlasenko.app.entity;

public class Employee {

    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
    private final String positionName;
    private Long id;

    public Employee(String firstName, String lastName, String phoneNumber, String positionName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.positionName = positionName;
    }

    public Employee(Long id, String firstName, String lastName, String phoneNumber, String positionName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.positionName = positionName;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPositionName() {
        return positionName;
    }
}
