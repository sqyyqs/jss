package com.sqy.domain;

public class Employee {
    private Long id;
    private String lastName;
    private String firstName;
    /*todo @Nullable*/ private String middleName;
    /*todo @Nullable*/ private String position;
    /*todo @Nullable, change from Object*/ private Object account;
    /*todo @Nullable*/ private String email;
    private EmployeeStatus status;

    public Employee(
            Long id,
            String lastName,
            String firstName,
            String middleName,
            String position,
            Object account,
            String email,
            EmployeeStatus status
    ) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.position = position;
        this.account = account;
        this.email = email;
        this.status = status;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Object getAccount() {
        return account;
    }

    public void setAccount(Object account) {
        this.account = account;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EmployeeStatus getStatus() {
        return status;
    }

    public void setStatus(EmployeeStatus status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", position='" + position + '\'' +
                ", account=" + account +
                ", email='" + email + '\'' +
                ", status=" + status +
                '}';
    }

    public enum EmployeeStatus {
        ACTIVE,
        DELETED
    }
}
