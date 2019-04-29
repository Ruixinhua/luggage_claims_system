package com.spring.boot.luggage_claims_system.hirbernia_sina.controller;

import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.*;
import com.spring.boot.luggage_claims_system.hirbernia_sina.service.SecurityDataService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author Liu Dairui
 * @date 2019-03-27 21:59
 */
@Controller
@RequestMapping("/claim")
public class ClaimController {

    @Autowired
    private SecurityDataService securityDataService;

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping
    public String claim() {
        return "redirect:/claim/write";
    }

    @GetMapping("/policy")
    public String policy(Authentication authentication, Model model) {
        UserInfo customer = securityDataService.getUserByEmailAddress(authentication.getName());
        List<Policy> policies = securityDataService.getAllPoliciesByCustomerId(customer.getId());
        model.addAttribute("policies", policies);
        return "claim/policy";
    }
    /**
     * @param
     * @return
     */
    @GetMapping(value = "/write")
//    @Secured(value = "EMPLOYEE")
//    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public String write() {
        return "redirect:/claim/policy";
    }

    @GetMapping("/writeClaim")
    public String writeClaim(@RequestParam("serialNo") Long serialNo, Authentication authentication, Model model) {
        Policy policy = securityDataService.getPolicyById(serialNo);
        UserInfo customer = securityDataService.getUserByEmailAddress(authentication.getName());
        model.addAttribute("policy", policy);
        model.addAttribute("customer", customer);
        return "claim/write";
    }

    @GetMapping("claims")
    public String getAllClaims(Model model, Authentication authentication) {
        UserInfo customer = securityDataService.getUserByEmailAddress(authentication.getName());
        List<ClaimInfo> claims = securityDataService.getAllClaimsByCustomerId(customer.getId());
        model.addAttribute("claims", claims);
        return "claim/claims";
    }
    @PostMapping("/finish")
    public String create(@Valid @ModelAttribute("write") WriteInfo writeInfo, BindingResult bindingResult, Model model,
                         Authentication authentication) {
        // TODO: justify the identity of user
        if (bindingResult.hasErrors()) {
            model.addAttribute("write", writeInfo);
            model.addAttribute("error", bindingResult.getFieldError().getDefaultMessage());
            return "claim/write";
        }
//        System.out.println(writeInfo);
        UserInfo userInfo;
        if(authentication != null){
            userInfo = securityDataService.getUserByEmailAddress(authentication.getName());
        }else {
            // TODO: remove, for post testing
            userInfo = securityDataService.getUserByEmailAddress(writeInfo.getEmailAddress());
        }
//        System.out.println(userInfo);
        ClaimInfo claimInfo = new ClaimInfo(writeInfo.getSerialNo(), userInfo.getId(), writeInfo.getBillingAddress(),
                writeInfo.getFlightNo(), writeInfo.getLuggageType(), 0L, writeInfo.getDetails(), new Date(),null);
        System.out.println(claimInfo);
        claimInfo.setSubmitDate(new Date());
        securityDataService.saveAndUpdateClaim(claimInfo);
        model.addAttribute("customer", userInfo);
        return "claim/finish";
    }

    @GetMapping("/details")
    public String claimDetail(@RequestParam("serialNo") Long serialNo, Authentication authentication, Model model) {
        ClaimInfo claimInfo = securityDataService.getClaimById(serialNo);
        UserInfo userInfo = securityDataService.getUserById(claimInfo.getCustomerId());
        WriteInfo writeInfo = new WriteInfo(userInfo.getFirstName(), userInfo.getLastName(), userInfo.getPassport(),
                claimInfo.getSerialNo(), userInfo.getPhoneNumber(), userInfo.getEmailAddress(), claimInfo.getBillingAddress(),
                claimInfo.getFlightNo(), claimInfo.getLostLuggage(), claimInfo.getDetails());
        model.addAttribute("customerId", claimInfo.getCustomerId());
        model.addAttribute("write", writeInfo);
        model.addAttribute("result", new Result());
        return "employee/claimdetail";
    }

    @PostMapping("/result")
    public String sendResult(@ModelAttribute("result") Result result, Authentication authentication) {
        System.out.println(result);
        ClaimInfo claimInfo = securityDataService.getClaimById(result.getSerialNo());
        String feedback = "We have received your application.\n";
        if (result.getReason() != null) {
            feedback += "The feedback is: " + result.getReason() + "\n";
        }
        switch (result.getFeedback()) {
            case "1":
                claimInfo.setResult("Approved");
                feedback += "Congratulation!Your claim is accepted.";
                break;
            case "2":
                claimInfo.setResult("Rejected");
                feedback += "Unfortunately, your claim can not be accepted by our company.If you have any question, " +
                        "You can call our customer service hot line or send email to us.";
                break;
            case "3":
                claimInfo.setResult("To be confirmed");
                feedback += "Please give us more details about your claim.Thank you very much.";
                break;
            default:
                break;
        }
        try {
            emailSender(result, feedback);
            securityDataService.saveAndUpdateClaim(claimInfo);
            UserInfo employeeDB = securityDataService.getUserByEmailAddress(authentication.getName());
            claimInfo.setEmployeeId(employeeDB.getId());
            securityDataService.saveAndUpdateClaim(claimInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/employee/employee";
    }

    public void emailSender(Result result, String feedback) throws IOException {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(result.getEmail());
        mailMessage.setSubject("We have settled your claim");
        mailMessage.setFrom("771281557@qq.com");
        mailMessage.setText(feedback);
        mailSender.send(mailMessage);
    }

}
