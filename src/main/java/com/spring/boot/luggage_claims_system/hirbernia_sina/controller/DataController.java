package com.spring.boot.luggage_claims_system.hirbernia_sina.controller;

import com.alibaba.fastjson.JSONObject;
import com.spring.boot.luggage_claims_system.hirbernia_sina.authentication.UserAuthenticationProvider;
import com.spring.boot.luggage_claims_system.hirbernia_sina.service.SecurityDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


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

}
