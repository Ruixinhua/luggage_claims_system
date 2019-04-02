package com.spring.boot.luggage_claims_system.hirbernia_sina.controller;

import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.ClaimInfo;
import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.CustomerInfo;
import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.EmployeeInfo;
import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.WriteInfo;
import com.spring.boot.luggage_claims_system.hirbernia_sina.repository.ClaimRepository;
import com.spring.boot.luggage_claims_system.hirbernia_sina.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * @author Liu Dairui
 * @date 2019-03-27 21:59
 */
@Controller
@RequestMapping("/claim")
public class ClaimController {

    @Autowired
    private ClaimRepository claimRepository;
    @Autowired
    private CustomerRepository customerRepository;

    /**
     *
     * @param model
     * @return
     */
    @GetMapping("/write")
    public String write(Model model){
        model.addAttribute("write", new WriteInfo());
        return "claim/write";
    }

    @PostMapping("/finish")
    public String create(@Valid @RequestBody WriteInfo writeInfo){
        System.out.println(writeInfo);
        CustomerInfo customerInfo = new CustomerInfo(null,writeInfo.getPassport(),writeInfo.getFirstName(),
                writeInfo.getLastName(),writeInfo.getPhoneNumber(),writeInfo.getEmailAddress());
        customerInfo = customerRepository.save(customerInfo);
        System.out.println(customerInfo);
        ClaimInfo claimInfo = new ClaimInfo(writeInfo.getSerialNo(),customerInfo.getId(),writeInfo.getBillingAddress(),
                writeInfo.getFlightNo(),"",0L,writeInfo.getDetails());
        System.out.println(claimInfo);
        claimRepository.save(claimInfo);
        return "claim/finish";
    }

}
