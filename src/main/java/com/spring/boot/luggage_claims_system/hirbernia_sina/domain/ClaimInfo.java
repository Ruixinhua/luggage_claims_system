package com.spring.boot.luggage_claims_system.hirbernia_sina.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Liu Dairui
 * @date 2019-03-26 21:26
 */
@Data
@AllArgsConstructor
@Entity
@Table(name = "claim")
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

    @Column
    private Long employeeId;

    @Column(nullable = false, length = 50)
    private String details;

    @Column
    private Date date;

    protected ClaimInfo() {
    }
}
