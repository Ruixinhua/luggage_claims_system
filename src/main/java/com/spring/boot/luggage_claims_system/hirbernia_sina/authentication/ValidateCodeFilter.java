package com.spring.boot.luggage_claims_system.hirbernia_sina.authentication;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.ImageCode;
import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import org.thymeleaf.util.StringUtils;

import static com.spring.boot.luggage_claims_system.hirbernia_sina.domain.MyConstants.SESSION_KEY;

/**
 * @author Liu Dairui
 * @date 2019-05-12 23:45
 */
@Slf4j
@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter implements Filter {

    /**
     * failure filter
     */
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private SessionStrategy sessionStrategy;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (StringUtils.equals("/signin", request.getRequestURI()) && StringUtils.equalsIgnoreCase(request.getMethod(), "post")) {
            log.info("request : {}", request.getRequestURI());
            try {
                // verify the code
                validate(new ServletWebRequest(request));
            } catch (AuthenticationException e) {
                // catch the exception
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        // success
        filterChain.doFilter(request, response);

    }

    private void validate(ServletWebRequest request) throws ServletRequestBindingException {
        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "imageCode");
        if (StringUtils.isEmpty(codeInRequest)) {
            throw new MyException("code can not be empty");
        }

        ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(request, SESSION_KEY);
        if (Objects.isNull(codeInSession)) {
            throw new MyException("code is not exist");
        }

        if (codeInSession.isExpried()) {
            sessionStrategy.removeAttribute(request, SESSION_KEY);
            throw new MyException("code is expired");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new MyException("code is not matched");
        }

        sessionStrategy.removeAttribute(request, SESSION_KEY);
    }
}
