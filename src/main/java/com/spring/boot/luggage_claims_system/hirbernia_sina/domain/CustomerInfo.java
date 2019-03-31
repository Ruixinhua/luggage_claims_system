package com.spring.boot.luggage_claims_system.hirbernia_sina.domain;

import javax.persistence.*;

/**
 * @author Liu Dairui
 * @date 2019-03-26 19:23
 */
@Entity
public class CustomerInfo {

    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String passport;

    @Column(nullable = false, length = 20)
    private String firstName;

    @Column(nullable = false, length = 20)
    private String lastName;

    @Column(nullable = false, length = 20)
    private String phoneNumber;

    @Column(nullable = false, length = 50)
    private String emailAddress;

    protected CustomerInfo(){

    }

    public CustomerInfo(Long id, String passport, String firstName, String lastName, String phoneNumber, String emailAddress) {
        this.id = id;
        this.passport = passport;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    public Long getId() {
        return id;
    }

    public String getPassport() {
        return passport;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return String.format("Customer[id=%d,firstName=%s,lastName=%s,passport=%s,phoneNumber=%s,emailAddress=%s]"
                ,id,firstName,lastName,passport,phoneNumber,emailAddress);
    }
}
