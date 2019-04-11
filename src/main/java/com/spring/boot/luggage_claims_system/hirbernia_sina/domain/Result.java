package com.spring.boot.luggage_claims_system.hirbernia_sina.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Liu Dairui
 * @date 2019-04-06 15:53
 */
@Data
@AllArgsConstructor
public class Result {
    private String feedback;
    private String reason;
    private String email;
    private Long serialNo;
    public Result(){}
}
