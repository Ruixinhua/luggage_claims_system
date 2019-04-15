package com.spring.boot.luggage_claims_system.hirbernia_sina.controller;

import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.*;
import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.UserInfo;
import com.spring.boot.luggage_claims_system.hirbernia_sina.service.SecurityDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.mail.SimpleMailMessage;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;

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
    public String claim(){
        return "redirect:/claim/write";
    }
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
    public String create(@Valid @ModelAttribute("write") WriteInfo writeInfo, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("write", writeInfo);
            model.addAttribute("error", bindingResult.getFieldError().getDefaultMessage());
            return "claim/write";
        }
//        System.out.println(writeInfo);
        // TODO: add customer to database
        UserInfo userInfo = new UserInfo();
        userInfo = securityDataService.saveAndUpdateUser(userInfo);
//        System.out.println(userInfo);
        ClaimInfo claimInfo = new ClaimInfo(writeInfo.getSerialNo(), userInfo.getId(), writeInfo.getBillingAddress(),
                writeInfo.getFlightNo(), writeInfo.getLuggageType(), 0L, writeInfo.getDetails(), new Date());
        System.out.println(claimInfo);
        claimInfo.setDate(new Date());
        securityDataService.saveAndUpdateClaim(claimInfo);
        model.addAttribute("customer", userInfo);
        return "claim/finish";
    }

    @GetMapping("/details")
    public String claimDetail(@RequestParam("serialNo") Long serialNo, Authentication authentication, Model model) {
        ClaimInfo claimInfo = securityDataService.getClaimById(serialNo);
        UserInfo employeeDB = securityDataService.getUserByEmailAddress(authentication.getName());
        claimInfo.setEmployeeId(employeeDB.getId());
        securityDataService.saveAndUpdateClaim(claimInfo);
        UserInfo userInfo = securityDataService.getUserById(claimInfo.getCustomerId());
        WriteInfo writeInfo = new WriteInfo(userInfo.getFirstName(), userInfo.getLastName(), userInfo.getPassport(),
                claimInfo.getSerialNo(), userInfo.getPhoneNumber(), userInfo.getEmailAddress(), claimInfo.getBillingAddress(),
                claimInfo.getFlightNo(),claimInfo.getLostLuggage(),claimInfo.getDetails());
        model.addAttribute("customerId", claimInfo.getCustomerId());
        model.addAttribute("write", writeInfo);
        model.addAttribute("result", new Result());
        return "employee/claimdetail";
    }

    @PostMapping("/result")
    public String sendResult(@ModelAttribute("result")Result result){
        System.out.println(result);
        ClaimInfo claimInfo = securityDataService.getClaimById(result.getSerialNo());
        String feedback = "We have received your application.\n";
        if(result.getReason() != null){
            feedback += "The feedback is: " + result.getReason()+"\n";
        }
        switch (result.getFeedback()){
            case "1":
                feedback+="Congratulation!Your claim is accepted.";
                break;
            case "2":
                feedback+="Unfortunately, your claim can not be accepted by our company.If you have any question, " +
                        "You can call our customer service hot line or send email to us.";
                break;
            case "3" :
                feedback+="Please give us more details about your claim.Thank you very much.";
                break;
            default:
                break;
        }
        try {
            emailSender(result, feedback);
        }catch(IOException e){
            e.printStackTrace();
        }
        return "redirect:/employee/employee?employeeId="+claimInfo.getEmployeeId();
    }

    public void emailSender(Result result, String feedback) throws IOException {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(result.getEmail());
        mailMessage.setSubject("We have settled your claim");
        mailMessage.setFrom("1213994171@qq.com");
        mailMessage.setText(feedback);
        mailSender.send(mailMessage);
    }

}
