package com.spring.boot.luggage_claims_system.hirbernia_sina.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Liu Dairui
 * @date 2019-04-27 17:53
 */
@Entity
@Data
@AllArgsConstructor
@Table(name = "policy")
public class Policy {
    @Id
    private Long serialNo;
    @Column
    private Long customerId;
    @Column
    private String flightNo;
    @Column
    private String insuranceType;
    @Column
    private String policyHolder;
    @Column
    private String policyType;
    @Column
    private Date validateFrom;
    @Column
    private Date validateTo;
    @Column
    private String placeFrom;
    @Column
    private String placeTo;
    @Column
    private int piecesOfLuggage;

    protected Policy() {
    }
}
