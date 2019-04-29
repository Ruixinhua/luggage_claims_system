package com.spring.boot.luggage_claims_system.hirbernia_sina.controller;

import com.alibaba.fastjson.JSONObject;
import com.spring.boot.luggage_claims_system.hirbernia_sina.authentication.UserAuthenticationProvider;
import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.ClaimInfo;
import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.Policy;
import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.UserInfo;
import com.spring.boot.luggage_claims_system.hirbernia_sina.service.SecurityDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


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
//        System.out.println(jsonParam.get("emailAddress"));
        return securityDataService.getUserByEmailAddress(username);
    }

    @PostMapping(value = "/api/claims", produces = "application/json;charset=UTF-8")
    public List<Map<String, String>> getClaims(@RequestBody JSONObject jsonParam) {
//        System.out.println(jsonParam);
        List<Map<String, String>> results = new ArrayList<>();
        List<ClaimInfo> claims;
        int request;
        if (jsonParam.get("request") instanceof Integer) {
            request = Integer.parseInt(jsonParam.get("request").toString());
            if (request == 0) {
                claims = securityDataService.getAllClaimsByEmployeeId(0L);
            } else {
                claims = securityDataService.getAllClaimsByEmployeeIdIsNot(0L);
            }
        } else {
            return results;
        }
        for (ClaimInfo claim : claims) {
            Map<String, String> map = new HashMap<>();
            map.put("serialNo", claim.getSerialNo().toString());
            map.put("flightNo", claim.getFlightNo());
            map.put("billingAddress", claim.getBillingAddress());
            map.put("details", claim.getDetails());
            map.put("date", claim.getSubmitDate().toString());
            map.put("result", claim.getResult());
            map.put("employeeId", claim.getEmployeeId().toString());
            results.add(map);
        }
        return results;
    }

    private UserInfo getUserByEmail(JSONObject jsonParam) {
        String username = jsonParam.get("emailAddress").toString();
        return securityDataService.getUserByEmailAddress(username);
    }

    private Policy getOnePolicy(JSONObject jsonParam) {
        Long serialNo = Long.parseLong(jsonParam.get("serialNo").toString());
        return securityDataService.getPolicyById(serialNo);
    }

    @PostMapping(value = "/api/writeClaim")
    public Map<String, String> writeClaim(@RequestBody JSONObject jsonParam) {
        Map<String, String> response = new HashMap<>();
        UserInfo user = getUserByEmail(jsonParam);
        if (user == null) {
            response.put("msg", "the user is not exist");
            response.put("code", "403");
            return response;
        }
        Long serialNo = Long.parseLong(jsonParam.get("serialNo").toString());
        Policy policy = getOnePolicy(jsonParam);
        logger.info("writeClaim[{}]", policy);
        if (policy == null) {
            response.put("msg", "the policy is not exist");
            response.put("code", "403");
            return response;
        }
        String billingAddress = jsonParam.get("billingAddress").toString();
        String details = jsonParam.get("details").toString();
        String lostLuggage = jsonParam.get("lostLuggage").toString();
        ClaimInfo claimInfo = new ClaimInfo(serialNo, user.getId(), billingAddress, policy.getFlightNo(), lostLuggage,
                0l, details, new Date(), null);
        ClaimInfo temp = securityDataService.saveAndUpdateClaim(claimInfo);
        if (temp == null) {
            response.put("msg", "fail to insert claim");
            response.put("code", "500");
            return response;
        }
        response.put("msg", "success");
        response.put("code", "200");
        return response;
    }

    @PostMapping(value = "/api/policy")
    public List<Policy> getPolicy(@RequestBody JSONObject jsonParam) {
        UserInfo user = getUserByEmail(jsonParam);
        List<Policy> policies = securityDataService.getAllPoliciesByCustomerId(user.getId());
        return new ArrayList<>(policies);
    }
}
