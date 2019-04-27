package com.spring.boot.luggage_claims_system.hirbernia_sina.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author Liu Dairui
 * @date 2019-04-27 17:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Policy {
    @Id
    private Long serialNo;
    @Column
    private String policyType;
    @Column
    private Date validateFrom;
    @Column
    private Date validateTo;
    @Column
    private String from;
    @Column
    private String to;

}
