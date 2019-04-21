package com.spring.boot.luggage_claims_system.hirbernia_sina.controller;

import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.UserInfo;
import com.spring.boot.luggage_claims_system.hirbernia_sina.service.SecurityDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Date;

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
    public String index(Authentication authentication, Model model) {
        if(authentication != null){
            UserInfo userInfo = securityDataService.getUserByEmailAddress(authentication.getName());
            model.addAttribute("username", userInfo.getNickname());
        }
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
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userInfo.setPassword(encoder.encode(userInfo.getPassword().trim()));
        UserInfo registered = securityDataService.saveUser(userInfo);
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

    @GetMapping("/login-error")
    public String loginError(UserInfo userInfo, Model model) {
        model.addAttribute("user", userInfo);
        model.addAttribute("error", true);
        return "signin";
    }
}
