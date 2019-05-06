package com.spring.boot.luggage_claims_system.hirbernia_sina.controller;

import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.ClaimInfo;
import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.UserInfo;
import com.spring.boot.luggage_claims_system.hirbernia_sina.service.SecurityDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * @author Liu Dairui
 * @date 2019-03-29 16:16
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private SecurityDataService securityDataService;

    @GetMapping
    public String employeeRegister(){
        return "redirect:/employee/register";
    }

    @GetMapping("/register")
    public String getRegister(Model model){
        model.addAttribute("employee", new UserInfo());
        model.addAttribute("passwordCheck", "");
        return "employee/register";
    }

    @PostMapping("/result")
    public String postRegister(@Valid @ModelAttribute(value = "employee") UserInfo userInfo,
                               BindingResult bindingResult, @ModelAttribute(value = "passwordCheck")String passwordCheck,
                               Model model){
//        System.out.println(userInfo);
//        System.out.println(passwordCheck);
        if(bindingResult.hasErrors()){
            model.addAttribute("error",bindingResult.getFieldError().getDefaultMessage());
            return "employee/register";
        }
        if (!userInfo.getPassword().equals(passwordCheck)) {
            model.addAttribute("employee", userInfo);
            model.addAttribute("passwordCheck", "");
            return "employee/register";
        }
        userInfo.setRegisterDate(new Date());
        userInfo.setLoginDate(null);
        model.addAttribute("employee", userInfo);
        securityDataService.saveUser(userInfo);
        return "employee/result";
    }

    @GetMapping("/signin")
    public String getLogin(Model model){
        model.addAttribute("employee", new UserInfo());
        return "employee/signin";
    }



    @GetMapping("/employee")
    public String employeeHomepage(Authentication authentication, Model model) {
        UserInfo user = securityDataService.getUserByEmailAddress(authentication.getName());
        List<ClaimInfo> claims = securityDataService.getAllClaimsByOrderBySubmitDateAsc();
        int approved = 0, rejected = 0, unsure = 0, unprocessed = 0;
        model.addAttribute("employee", user);
        model.addAttribute("claimList", claims);
        for (ClaimInfo claim : claims) {
            if (claim.getResult() == null) unprocessed++;
            else {
                switch (claim.getResult()) {
                    case "Approved":
                        approved++;
                        break;
                    case "Rejected":
                        rejected++;
                        break;
                    case "To be confirmed":
                        unsure++;
                        break;
                    default:
                        unprocessed++;
                        break;
                }
            }
        }
        model.addAttribute("approved", approved);
        model.addAttribute("rejected", rejected);
        model.addAttribute("unsure", unsure);
        model.addAttribute("unprocessed", unprocessed);
        user.setLoginDate(new Date());
        securityDataService.updateUser(user);
        return "employee/employee";
    }
}
