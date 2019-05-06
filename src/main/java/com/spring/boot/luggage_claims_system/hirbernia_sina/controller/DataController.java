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
import org.springframework.security.core.Authentication;
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
    UserAuthenticationProvider userAuth;
    @Autowired
    private SecurityDataService securityDataService;

    @PostMapping(value = "/api/userInfo", produces = "application/json;charset=UTF-8")
    public Object getUser(@RequestBody JSONObject jsonParam) {
        String username = jsonParam.get("emailAddress").toString();
//        System.out.println(jsonParam.get("emailAddress"));
        return securityDataService.getUserByEmailAddress(username);
    }

    /**
     * @api {POST} /api/process get claims
     * @apiVersion 1.0.0
     * @apiName get specific claims
     * @apiGroup api
     * @apiDescription employee can get processed claims or unprocessed claims
     * @apiParam request 1 for processed, 0 for unprocessed
     * @apiSuccess {Object[]} results all of claims
     * @apiSuccess {String} results.serialNo serial no of claim
     * @apiSuccess {String} results.flightNo flight no
     * @apiSuccess {String} results.billingAddress billing address
     * @apiSuccess {String} results.details details of apply for a claim
     * @apiSuccess {String} results.date submit date of claim
     * @apiSuccess {String} results.result the result of claim
     * @apiSuccess {String} results.employeeId the employee who deal with the claim
     * @apiSuccessExample {json}
     * Response-Example:
     * [
     * {
     * "date": "2019-04-11 20:23:24.0",
     * "result": "Approved",
     * "flightNo": "Hibernia-Sino",
     * "nickname": "refrrir",
     * "details": "lost luggage",
     * "billingAddress": "BJUT",
     * "serialNo": "12512324"
     * },
     * {
     * "date": "2019-04-18 21:12:12.0",
     * "result": "To be confirmed",
     * "flightNo": "Hibernia-Sino",
     * "nickname": "refrrir",
     * "details": "lost luggage",
     * "billingAddress": "BJUT",
     * "serialNo": "13531254"
     * }
     * ]
     */
    @PostMapping(value = "/api/process", produces = "application/json;charset=UTF-8")
    public List<Map<String, String>> getClaims(@RequestBody JSONObject jsonParam) {
//        System.out.println(jsonParam);
        List<Map<String, String>> results = new ArrayList<>();
        List<ClaimInfo> claims;
        int request;
        if (jsonParam.get("request") instanceof Integer) {
            request = Integer.parseInt(jsonParam.get("request").toString());
            if (request == 0) {
                claims = securityDataService.getAllClaimsOrderedByEmployeeId(0L);
            } else {
                claims = securityDataService.getAllClaimsOrderedByEmployeeIdIsNot(0L);
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

    @PostMapping(value = "/api/claims", produces = "application/json;charset=UTF-8")
    public List<ClaimInfo> getCustomerClaims(@RequestBody JSONObject jsonParam, Authentication authentication) {
//        System.out.println(jsonParam);
        String emailAddress = jsonParam.get("emailAddress").toString();
        UserInfo customer = securityDataService.getUserByEmailAddress(emailAddress);
        return securityDataService.getAllClaimsByCustomerId(customer.getId());
    }

    private UserInfo getUserByEmail(JSONObject jsonParam) {
        String username = jsonParam.get("emailAddress").toString();
        return securityDataService.getUserByEmailAddress(username);
    }

    private Policy getOnePolicy(JSONObject jsonParam) {
        Long serialNo = Long.parseLong(jsonParam.get("serialNo").toString());
        return securityDataService.getPolicyById(serialNo);
    }

    /**
     * @api {POST} /api/writeClaim write claim
     * @apiVersion 1.0.0
     * @apiName write claim
     * @apiGroup api
     * @apiDescription customer submit claim
     * @apiParam emailAddress email address of customer
     * @apiParam serialNo the serialNo of policy
     * @apiParam billingAddress billing address of customer
     * @apiParam details the details of customer
     * @apiParam lostLuggage the luggage what is loss of customer
     * @apiParamExample Request-Example:
     * {
     * "emailAddress":"12315@163.com",
     * "serialNo":36021758,
     * "billingAddress":"BJUT",
     * "details":"test",
     * "lostLuggage":"bag"
     * }
     * @apiSuccess {String} msg the message about result
     * @apiSuccess {String} code
     * @apiSuccessExample {json}
     * Response-Example-1:
     * {"msg":"the policy is not exist","code":"403"}
     * Response-Example-2:
     * {"msg":"success","code":"200"}
     */
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
        Policy newPolicy = new Policy(policy, 1);
        securityDataService.saveAndUpdatePolicy(newPolicy);
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
