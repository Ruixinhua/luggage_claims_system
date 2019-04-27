package com.spring.boot.luggage_claims_system.hirbernia_sina.controller;

import com.alibaba.fastjson.JSONObject;
import com.spring.boot.luggage_claims_system.hirbernia_sina.authentication.UserAuthenticationProvider;
import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.ClaimInfo;
import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.UserInfo;
import com.spring.boot.luggage_claims_system.hirbernia_sina.service.SecurityDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Liu Dairui
 * @date 2019-04-24 11:43
 */
@RestController
public class DataController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SecurityDataService securityDataService;
    @Autowired
    UserAuthenticationProvider userAuth;


    @PostMapping(value = "/api/userInfo", produces = "application/json;charset=UTF-8")
    public Object getUser(@RequestBody JSONObject jsonParam) {
        String username = jsonParam.get("emailAddress").toString();
        System.out.println(jsonParam.get("emailAddress"));
        return securityDataService.getUserByEmailAddress(username);
    }

    @PostMapping(value = "/api/claims", produces = "application/json;charset=UTF-8")
    public List<Map<String, String>> getClaims(@RequestBody JSONObject jsonParam) {
//        System.out.println(jsonParam);
        List<Map<String, String>> results = new ArrayList<>();
        List<ClaimInfo> claims = securityDataService.getAllClaims();
        for (ClaimInfo claim : claims) {
            Map<String, String> map = new HashMap<>();
            map.put("serialNo", claim.getSerialNo().toString());
            map.put("flightNo", claim.getFlightNo());
            map.put("billingAddress", claim.getBillingAddress());
            map.put("details", claim.getDetails());
            map.put("date", claim.getDate().toString());
            map.put("result", claim.getResult());
            UserInfo employee = securityDataService.getUserById(claim.getEmployeeId());
            if (employee != null) {
                map.put("nickname", employee.getNickname());
            } else {
                map.put("nickname", "0");
            }
            results.add(map);
        }
        return results;
    }

//    public

    @PostMapping(value = "/api/writeClaim")
    public ResponseEntity<String> writeClaim(@RequestBody JSONObject jsonParam) {

        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
