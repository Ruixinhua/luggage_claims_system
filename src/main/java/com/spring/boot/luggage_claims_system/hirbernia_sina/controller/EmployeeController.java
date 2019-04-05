package com.spring.boot.luggage_claims_system.hirbernia_sina.controller;

import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.ClaimInfo;
import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.EmployeeInfo;
import com.spring.boot.luggage_claims_system.hirbernia_sina.repository.ClaimRepository;
import com.spring.boot.luggage_claims_system.hirbernia_sina.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    private EmployeeRepository employeeRepository;
    @Autowired
    private ClaimRepository claimRepository;

    @GetMapping
    public String employeeRegister(){
        return "redirect:/employee/register";
    }

    @GetMapping("/register")
    public String getRegister(Model model){
        model.addAttribute("employee", new EmployeeInfo(null, null,null,
                null, null, null, null));
        model.addAttribute("passwordCheck", "");
        return "employee/register";
    }

    @PostMapping("/result")
    public String postRegister(@Valid @ModelAttribute(value = "employee")EmployeeInfo employeeInfo,
                               BindingResult bindingResult, @ModelAttribute(value = "passwordCheck")String passwordCheck,
                               Model model){
//        System.out.println(employeeInfo);
//        System.out.println(passwordCheck);
        if(bindingResult.hasErrors()){
            model.addAttribute("error",bindingResult.getFieldError().getDefaultMessage());
            return "employee/register";
        }
        if (!employeeInfo.getPassword().equals(passwordCheck)){
            model.addAttribute("employee", employeeInfo);
            model.addAttribute("passwordCheck", "");
            return "employee/register";
        }
        employeeInfo.setRegisterDate(new Date());
        employeeInfo.setLoginDate(null);
        model.addAttribute("employee", employeeInfo);
        employeeRepository.save(employeeInfo);
        return "employee/result";
    }

    @GetMapping("/signin")
    public String getLogin(Model model){
        model.addAttribute("employee", new EmployeeInfo(null,null, null,
                null, null, null, null));
        return "employee/signin";
    }

    @PostMapping("/signin")
    public String postLogin(@ModelAttribute(value = "employee")EmployeeInfo employeeInfo, Model model){
//        System.out.println(employeeInfo);
        EmployeeInfo employeeDB = employeeRepository.findByEmailAddress(employeeInfo.getEmailAddress());
//        System.out.println("employee receive: "+employeeInfo);
//        System.out.println("employee in DB: "+employeeDB);
        // TODO: add feedback
        if(employeeDB == null){
            System.out.println("The password is not correct");
            model.addAttribute("employee", employeeInfo);
            model.addAttribute("error","The employee is not exist");
            return "employee/signin";
        }
        if(!employeeDB.getPassword().equals(employeeInfo.getPassword())){
            System.out.println("The employee is not exist");
            model.addAttribute("employee", employeeInfo);
            model.addAttribute("error","The password is not correct");
            return "employee/signin";
        }
        employeeDB.setLoginDate(new Date());
        employeeRepository.saveAndFlush(employeeDB);
        List<ClaimInfo> claims = claimRepository.findAll();
        model.addAttribute("employee", employeeDB);
        model.addAttribute("claimList", claims);
        return "employee/employee";

    }
//
//    @GetMapping(value = "/{id}")
//    public String employee(@PathVariable("id") Long id, Model model){
//        EmployeeInfo employeeInfo = employeeRepository.getOne(id);
//        List<ClaimInfo> claims = claimRepository.findAll();
//        model.addAttribute("employee", employeeInfo);
//        model.addAttribute("claimList", claims);
//        return "employee/employee";
//    }
}
