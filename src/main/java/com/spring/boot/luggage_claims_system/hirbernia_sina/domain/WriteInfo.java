package com.spring.boot.luggage_claims_system.hirbernia_sina.domain;

/**
 * @author Liu Dairui
 * @date 2019-03-31 17:22
 */
public class WriteInfo {

    private String firstName;

    private String lastName;

    private String passport;

    private Long serialNo;

    private String phoneNumber;

    private String emailAddress;

    private String billingAddress;

    private String flightNo;

    private String luggageType;

    private String details;

    public WriteInfo(){

    }

    public WriteInfo(String firstName, String lastName, String passport, Long serialNo, String phoneNumber, String emailAddress, String billingAddress, String flightNo, String luggageType, String details) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.passport = passport;
        this.serialNo = serialNo;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.billingAddress = billingAddress;
        this.flightNo = flightNo;
        this.luggageType = luggageType;
        this.details = details;
    }

    public String getLuggageType() {
        return luggageType;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassport() {
        return passport;
    }

    public Long getSerialNo() {
        return serialNo;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public String getDetails() {
        return details;
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

    public void setSerialNo(Long serialNo) {
        this.serialNo = serialNo;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setLuggageType(String luggageType) {
        this.luggageType = luggageType;
    }

    @Override
    public String toString(){
        return String.format("Write[firstName=%s,lastName=%s,passport=%s,serialNo=%d,phoneNumber=%s,emailAddress=%s," +
                "billingAddress=%s,luggageType=%s,flightNo=%s,details=%s", firstName,lastName,passport,serialNo,
                phoneNumber,emailAddress,billingAddress,luggageType,flightNo,details);
    }
}
