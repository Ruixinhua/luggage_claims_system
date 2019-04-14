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
        securityDataService.saveAndUpdateUser(userInfo);
        return "employee/result";
    }

    @GetMapping("/signin")
    public String getLogin(Model model){
        model.addAttribute("employee", new UserInfo());
        return "employee/signin";
    }

    @PostMapping("/signin")
    public String postLogin(@ModelAttribute(value = "employee") UserInfo userInfo, Model model) {
//        System.out.println(userInfo);
        UserInfo employeeDB = securityDataService.getUserByEmailAddress(userInfo.getEmailAddress());
//        System.out.println("employee receive: "+userInfo);
//        System.out.println("employee in DB: "+employeeDB);
        // TODO: add feedback
        if(employeeDB == null){
            System.out.println("The password is not correct");
            model.addAttribute("employee", userInfo);
            model.addAttribute("error","The employee is not exist");
            return "employee/signin";
        }
        if (!employeeDB.getPassword().equals(userInfo.getPassword())) {
            System.out.println("The employee is not exist");
            model.addAttribute("employee", userInfo);
            model.addAttribute("error","The password is not correct");
            return "employee/signin";
        }
        employeeDB.setLoginDate(new Date());
        securityDataService.saveAndUpdateUser(employeeDB);
        return "redirect:/employee/employee?employeeId="+employeeDB.getId();

    }

    @GetMapping("/employee")
    public String employeeHomepage(Authentication authentication, Model model) {
        UserInfo employeeDB = securityDataService.getUserByEmailAddress(authentication.getName());
        List<ClaimInfo> claims = securityDataService.getAllClaims();
        model.addAttribute("employee", employeeDB);
        model.addAttribute("claimList", claims);
        return "employee/employee";
    }
//
//    @GetMapping(value = "/{id}")
//    public String employee(@PathVariable("id") Long id, Model model){
//        UserInfo employeeInfo = employeeRepository.getOne(id);
//        List<ClaimInfo> claims = claimRepository.findAll();
//        model.addAttribute("employee", employeeInfo);
//        model.addAttribute("claimList", claims);
//        return "employee/employee";
//    }
}
