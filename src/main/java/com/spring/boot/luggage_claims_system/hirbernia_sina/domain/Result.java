package com.spring.boot.luggage_claims_system.hirbernia_sina.domain;

/**
 * @author Liu Dairui
 * @date 2019-04-06 15:53
 */
public class Result {
    private String feedback;
    private String reason;
    private String email;
    private Long serialNo;
    public Result(){}

    public String getFeedback() {
        return feedback;
    }

    public String getReason() {
        return reason;
    }

    public String getEmail() {
        return email;
    }

    public Long getSerialNo() {
        return serialNo;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSerialNo(Long serialNo) {
        this.serialNo = serialNo;
    }

    @Override
    public String toString() {
        return String.format("Result[feedback=%s, reason=%s", feedback, reason);
    }
}
