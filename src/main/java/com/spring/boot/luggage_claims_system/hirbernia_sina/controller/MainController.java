package com.spring.boot.luggage_claims_system.hirbernia_sina.controller;

import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.ImageCode;
import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.MyConstants;
import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.UserInfo;
import com.spring.boot.luggage_claims_system.hirbernia_sina.service.SecurityDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

/**
 * @author Liu Dairui
 * @date 2019-03-28 09:49
 */
@Controller
public class MainController {

    @Autowired
    private SessionStrategy sessionStrategy;

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
        model.addAttribute("login_error", false);
        model.addAttribute("code_error", false);
        System.out.println("sign in");
        return "signin";
    }

    @GetMapping("/login_error")
    public String loginError(UserInfo userInfo, Model model) {
        model.addAttribute("user", userInfo);
        model.addAttribute("code_error", false);
        model.addAttribute("login_error", true);
        return "signin";
    }

    @GetMapping("/code_error")
    public String codeError(UserInfo userInfo, Model model) {
        model.addAttribute("user", userInfo);
        model.addAttribute("code_error", true);
        model.addAttribute("login_error", false);
        return "signin";
    }

    @GetMapping("/code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("get image");
        ImageCode imageCode = generate(new ServletWebRequest(request));
        sessionStrategy.setAttribute(new ServletWebRequest(request), MyConstants.SESSION_KEY, imageCode);
        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }

    /**
     * 生成图形验证码
     *
     * @param request
     * @return
     */
    private ImageCode generate(ServletWebRequest request) {
        int width = ServletRequestUtils.getIntParameter(request.getRequest(), "width", MyConstants.WIDTH);
        int height = ServletRequestUtils.getIntParameter(request.getRequest(), "height", MyConstants.HEIGHT);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics();

        Random random = new Random();

        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        String sRand = "";
        int length = ServletRequestUtils.getIntParameter(request.getRequest(), "length", MyConstants.RANDOM_SIZE);
        for (int i = 0; i < length; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 13 * i + 6, 16);
        }

        g.dispose();

        return new ImageCode(image, sRand, MyConstants.EXPIRE_SECOND);
    }

    /**
     * 生成随机背景条纹
     *
     * @param fc
     * @param bc
     * @return
     */
    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

}
