package com.spring.boot.luggage_claims_system.hirbernia_sina.controller;

import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.ClaimInfo;
import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.Role;
import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.UserInfo;
import com.spring.boot.luggage_claims_system.hirbernia_sina.service.SecurityDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Liu Dairui
 * @date 2019-03-28 09:49
 */
@Controller
public class MainController {
    @Autowired
    private SecurityDataService securityDataService;
    @GetMapping("/")
    public String root() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/register")
    public String getRegister(Model model) {
        model.addAttribute("user", new UserInfo());
        return "register";
    }

    @PostMapping("/result")
    public String postRegister(@Valid @ModelAttribute(value = "user") UserInfo userInfo,
                               BindingResult bindingResult, @ModelAttribute(value = "passwordCheck") String passwordCheck,
                               Model model) {
        System.out.println(userInfo);
//        Role r = new Role(null,);
//        System.out.println(passwordCheck);
        model.addAttribute("user", userInfo);
        userInfo.setRegisterDate(new Date());
        userInfo.setLoginDate(null);
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", bindingResult.getFieldError().getDefaultMessage());
            return "register";
        }
        if (!userInfo.getPassword().equals(passwordCheck)) {
            model.addAttribute("error", "Password mismatch entered twice");
            model.addAttribute("passwordCheck", "");
            return "register";
        }
        UserInfo registered = securityDataService.saveAndUpdateUser(userInfo);
        if (registered == null) {
            bindingResult.rejectValue("email address", "message.regError");
            model.addAttribute("error", "The email is exist");
            return "register";
        }
        return "result";
    }

    @GetMapping("/signin")
    public String getLogin(Model model) {
        model.addAttribute("user", new UserInfo());
        return "signin";
    }
}
