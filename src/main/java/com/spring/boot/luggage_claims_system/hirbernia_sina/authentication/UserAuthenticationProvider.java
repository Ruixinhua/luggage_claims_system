package com.spring.boot.luggage_claims_system.hirbernia_sina.authentication;

import com.spring.boot.luggage_claims_system.hirbernia_sina.service.HSUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;


/**
 * @author Liu Dairui
 * @date 2019-04-11 20:46
 */
@Component
public class UserAuthenticationProvider implements AuthenticationProvider {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    HSUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        logger.info("VAuthenticationProvider authenticate login user [username={}, password={}]", username, password);
        HSUserDetails userDetails = (HSUserDetails) userDetailsService.loadUserByUsername(username);
        logger.info("UserAuthenticationProvider authenticate userDetails [userDetails={}]", userDetails);
        if (userDetails == null) {
            throw new BadCredentialsException("User is not found");
        }
        if (!password.equals(userDetails.getPassword())) {
            logger.info("UserAuthenticationProvider authenticate BadCredentialsException [inputPassword={}, DBPassword={}]", password, userDetails.getPassword());
            throw new BadCredentialsException("Password is wrong");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
