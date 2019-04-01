package com.spring.boot.luggage_claims_system.hirbernia_sina.controller;

import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.EmployeeInfo;
import com.spring.boot.luggage_claims_system.hirbernia_sina.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author Liu Dairui
 * @date 2019-03-29 16:16
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/register")
    public String getRegister(Model model){
        model.addAttribute("employee", new EmployeeInfo(null, null,null,
                null, null, null, null));
        model.addAttribute("passwordCheck", "");
        return "employee/register";
    }

    @PostMapping("/result")
    public String postRegister(@ModelAttribute(value = "employee")EmployeeInfo employeeInfo,
                         @ModelAttribute(value = "passwordCheck")String passwordCheck,Model model){
//        System.out.println(employeeInfo);
//        System.out.println(passwordCheck);
        if (employeeInfo.getPassword().equals(passwordCheck)){
            model.addAttribute("employee", employeeInfo);
            employeeRepository.save(employeeInfo);
            return "employee/result";
        }else{
            model.addAttribute("employee", employeeInfo);
            model.addAttribute("passwordCheck", "");
            return "employee/register";
        }

    }

    @GetMapping("/signin")
    public String getLogin(Model model){
        model.addAttribute("employee", new EmployeeInfo(null,null, null,
                null, null, null, null));
        return "employee/signin";
    }

    @PostMapping("/signin")
    public String postLogin(@ModelAttribute(value = "employee")EmployeeInfo employeeInfo, Model model){
        System.out.println(employeeInfo);
        return "employee/employee";
    }
}