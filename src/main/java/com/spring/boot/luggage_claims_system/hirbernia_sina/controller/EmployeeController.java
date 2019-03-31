package com.spring.boot.luggage_claims_system.hirbernia_sina.controller;

import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.EmployeeInfo;
import com.spring.boot.luggage_claims_system.hirbernia_sina.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    public String register(Model model){
        model.addAttribute("employee", new EmployeeInfo(null, null,null, null, null, null, null));
        model.addAttribute("passwordCheck", "");
        return "employee/register";
    }

    @PostMapping("/result")
    public String create(@ModelAttribute(value = "employee")EmployeeInfo employeeInfo,
                         @ModelAttribute(value = "passwordCheck")String passwordCheck,Model model){
        System.out.println(employeeInfo);
        System.out.println(passwordCheck);
        if (employeeInfo.getPassword().equals(passwordCheck)){
            model.addAttribute("employee", employeeInfo);
            employeeRepository.save(employeeInfo);
            return "employee/register_test";
        }else{
            return "employee/register";
        }

    }
}
