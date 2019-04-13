package com.spring.boot.luggage_claims_system.hirbernia_sina.authentication;

import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.UserInfo;
import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.Permission;
import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.Role;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;
import java.util.Set;

/**
 * @author Liu Dairui
 * @date 2019-04-11 11:22
 */
@Data
public class HSUserDetails extends UserInfo implements org.springframework.security.core.userdetails.UserDetails {

    private static final long serialVersionUID = 1L;
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private Set<Role> roles;
    private Set<Permission> permissions = null;

    public HSUserDetails(UserInfo userInfo, Set<Role> roleDetail, Set<Permission> permissionSet) {
        super(userInfo);
        this.roles = roleDetail;
        this.permissions = permissionSet;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        StringBuilder authoritiesBuilder = new StringBuilder();
        Set<Role> tempRoles = this.getRoles();
        if (tempRoles != null) {
            for (Role role : tempRoles) {
                authoritiesBuilder.append(",").append(role.getRole());
            }
        }
        Set<Permission> tempPermissions = this.getPermissions();
        if (tempPermissions != null) {
            for (Permission permission : tempPermissions) {
                authoritiesBuilder.append(",").append(permission.getPermission());
            }
        }
        String authoritiesStr = "";
        if (authoritiesBuilder.length() > 0) {
            authoritiesStr = authoritiesBuilder.deleteCharAt(0).toString();
        }
        logger.info("HSUserDetails getAuthorities [authoritiesStr={} ", authoritiesStr);
        return AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesStr);
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getEmailAddress();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
