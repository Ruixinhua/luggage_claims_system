package com.spring.boot.luggage_claims_system.hirbernia_sina.service;

import com.spring.boot.luggage_claims_system.hirbernia_sina.authentication.HSUserDetails;
import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.Permission;
import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.Role;
import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author Liu Dairui
 * @date 2019-04-11 17:12
 */
@Component
public class HSUserDetailsService implements UserDetailsService {

    @Autowired
    SecurityDataService securityDataService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = securityDataService.getUserByEmailAddress(username);
        if (userInfo == null) {
            throw new UsernameNotFoundException("The user is not exist");
        }
        Set<Role> role = securityDataService.getRoleByUserId(userInfo.getId());
        Set<Permission> permissions = securityDataService.getPermissionsByUserId(userInfo.getId());
        return new HSUserDetails(userInfo, role, permissions);
    }
}
