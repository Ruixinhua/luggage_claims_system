package com.spring.boot.luggage_claims_system.hirbernia_sina.info;

/**
 * @author Liu Dairui
 * @date 2019-03-26 21:26
 */
public class EmployeeInfo {

    private String id;

    private String name;

    private String nickname;

    private String emailAddress;

    private String phoneNumber;

    private String password;

    private boolean status;

    public EmployeeInfo(String id, String name, String nickname, String emailAddress, String phoneNumber, String password, boolean status) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.status = status;
    }

    public String getId() {
        return id;
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

    public boolean isStatus() {
        return status;
    }

    public void setId(String id) {
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

    public void setStatus(boolean status) {
        this.status = status;
    }
}
