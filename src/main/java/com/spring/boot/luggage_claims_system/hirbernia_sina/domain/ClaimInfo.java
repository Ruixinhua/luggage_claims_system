package com.spring.boot.luggage_claims_system.hirbernia_sina.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author Liu Dairui
 * @date 2019-03-26 21:26
 */
@Entity
public class ClaimInfo implements Serializable {

    @Id //primary key
    private Long serialNo;

    @Column(nullable = false, length = 20)
    private Long customerId;

    @Column(nullable = false, length = 50)
    private String billingAddress;

    @Column(nullable = false, length = 20)
    private String flightNo;

    @Column(length = 50)
    private String lostLuggage;

    @Column(length = 20)
    private Long employeeId;

    @Column(nullable = false, length = 50)
    private String details;

    protected ClaimInfo() {
    }

    public ClaimInfo(Long serialNo, Long customerId, String billingAddress, String flightNo, String lostLuggage, Long employeeId, String details) {
        this.serialNo = serialNo;
        this.customerId = customerId;
        this.billingAddress = billingAddress;
        this.flightNo = flightNo;
        this.lostLuggage = lostLuggage;
        this.employeeId = employeeId;
        this.details = details;
    }

    public Long getSerialNo() {
        return serialNo;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getEmployeeId() {
        return employeeId;
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


    public String getDetails() {
        return details;
    }

    public void setSerialNo(Long serialNo) {
        this.serialNo = serialNo;
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

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString(){
        return String.format("Claim[serialNo=%d,billingAddress=%s,flightNo=%s,lostLuggage=%s,customerId=%d,employeeId=%d," +
                "details=%s]",serialNo,billingAddress,flightNo,lostLuggage,customerId,employeeId,details);
    }
}
