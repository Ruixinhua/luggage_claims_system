package com.spring.boot.luggage_claims_system.hirbernia_sina.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Liu Dairui
 * @date 2019-03-31 17:22
 */
@Data
@AllArgsConstructor
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
}
