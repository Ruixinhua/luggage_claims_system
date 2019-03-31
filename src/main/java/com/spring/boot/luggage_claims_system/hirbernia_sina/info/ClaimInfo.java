package com.spring.boot.luggage_claims_system.hirbernia_sina.info;

/**
 * @author Liu Dairui
 * @date 2019-03-26 21:26
 */
public class ClaimInfo {

    private String claimNo;

    private CustomerInfo customerInfo;

    private String billingAddress;

    private String flightNo;

    private String lostLuggage;

    private EmployeeInfo employeeInfo;

    private String details;

    public ClaimInfo(String claimNo, CustomerInfo customerInfo, String billingAddress, String flightNo, String lostLuggage, EmployeeInfo employeeInfo, String details) {
        this.claimNo = claimNo;
        this.customerInfo = customerInfo;
        this.billingAddress = billingAddress;
        this.flightNo = flightNo;
        this.lostLuggage = lostLuggage;
        this.employeeInfo = employeeInfo;
        this.details = details;
    }

    public String getClaimNo() {
        return claimNo;
    }

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public String getLostLuggage() {
        return lostLuggage;
    }

    public EmployeeInfo getEmployeeInfo() {
        return employeeInfo;
    }

    public String getDetails() {
        return details;
    }

    public void setClaimNo(String claimNo) {
        this.claimNo = claimNo;
    }

    public void setCustomerInfo(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public void setLostLuggage(String lostLuggage) {
        this.lostLuggage = lostLuggage;
    }

    public void setEmployeeInfo(EmployeeInfo employeeInfo) {
        this.employeeInfo = employeeInfo;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
