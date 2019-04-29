package com.spring.boot.luggage_claims_system.hirbernia_sina.controller;

import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.UserInfo;
import com.spring.boot.luggage_claims_system.hirbernia_sina.service.SecurityDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Liu Dairui
 * @date 2019-03-27 16:36
 */
@Controller
@RequestMapping("/customer")
//@PreAuthorize("hasAuthority('CUSTOMER')")
public class CustomerController {
    @Autowired
    private SecurityDataService securityDataService;

    @GetMapping("profile")
    public String getProfile(Model model, Authentication authentication) {
        UserInfo customer = securityDataService.getUserByEmailAddress(authentication.getName());
        model.addAttribute("customer", customer);
        return "claim/profile";
    }

    @PostMapping("update")
    public String updateProfile(Model model, Authentication authentication, UserInfo customer) {
        UserInfo old = securityDataService.getUserByEmailAddress(authentication.getName());
        old.setFirstName(customer.getFirstName());
        old.setLastName(customer.getLastName());
        old.setPhoneNumber(customer.getPhoneNumber());
        old.setEmailAddress(customer.getEmailAddress());
        old.setNickname(customer.getNickname());
        old.setPassport(customer.getPassport());
        securityDataService.updateUser(old);
        model.addAttribute("customer", customer);
        return "claim/profile";
    }
}
