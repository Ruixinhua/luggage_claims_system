package com.spring.boot.luggage_claims_system.hirbernia_sina.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Liu Dairui
 * @date 2019-04-24 21:18
 */
@Component("SecurityAuthenticationSuccessHandler")
public class SecurityAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 成功处理
     *
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        logger.debug("login success");
//        Map<String,String> map=new HashMap<>();
//        map.put("code", "200");
//        map.put("msg", "login success");
//        response.setContentType("application/json;charset=UTF-8");
//        response.getWriter().write(objectMapper.writeValueAsString(map));//return JSON
        // TODO User login log and authority
        logger.info("Authorities{}", authentication.getAuthorities().toString());
        /** Jump to the specified page*/
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("EMPLOYEE"))) {
            new DefaultRedirectStrategy().sendRedirect(request, response, "/claim/policy");
        } else {
            new DefaultRedirectStrategy().sendRedirect(request, response, "/employee/employee");
        }
    }

}
