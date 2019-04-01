package com.spring.boot.luggage_claims_system.hirbernia_sina.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author Liu Dairui
 * @date 2019-03-26 21:26
 */
@Entity
@Table(name = "employee")
public class EmployeeInfo {

    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "passport should not null")
    @Size(min = 2, max = 20)
    @Column(nullable = false, length = 20)
    private String passport;

    @NotEmpty(message = "name should not null")
    @Size(min = 2, max = 20)
    @Column(nullable = false, length = 20)
    private String name;

    @NotEmpty(message = "nickname should not null")
    @Size(min = 2, max = 20)
    @Column(nullable = false, length = 20)
    private String nickname;

    @NotEmpty(message = "emailAddress should not null")
    @Size(min = 2, max = 50)
    @Column(nullable = false, length = 50)
    private String emailAddress;

    @NotEmpty(message = "phoneNumber should not null")
    @Size(min = 2, max = 20)
    @Column(nullable = false, length = 20)
    private String phoneNumber;

    @NotEmpty(message = "password should not null")
    @Size(min = 2, max = 20)
    @Column(nullable = false, length = 20)
    private String password;

//    @Column(nullable = false, length = 1)
//    private boolean status;

    protected EmployeeInfo(){

    }

    public EmployeeInfo(Long id, String passport, String name, String nickname, String emailAddress, String phoneNumber, String password) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.passport = passport;
    }

    public Long getId() {
        return id;
    }

    public String getPassport() {
        return passport;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

//    public boolean isStatus() {
//        return status;
//    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public void setStatus(boolean status) {
//        this.status = status;
//    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    @Override
    public String toString(){
        return String.format("Employee[id=%d, passport=%s,name=%s, nickname=%s, emailAddress=%s,password=%s phoneNumber=%s]",
                id, passport, name, nickname, emailAddress, password, phoneNumber);
    }
}
