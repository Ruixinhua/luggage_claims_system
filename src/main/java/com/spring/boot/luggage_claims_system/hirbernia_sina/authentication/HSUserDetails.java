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
    private Role roleDetail;
    private Set<Permission> permissionSet = null;

    public HSUserDetails(UserInfo userInfo, Role roleDetail, Set<Permission> permissionSet) {
        super(userInfo);
        this.roleDetail = roleDetail;
        this.permissionSet = permissionSet;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        StringBuilder authoritiesBuilder = new StringBuilder();
        if (roleDetail != null) {
            authoritiesBuilder.append(",").append(roleDetail.getRole());
        }
        Set<Permission> tempPermissionSet = this.getPermissionSet();
        if (tempPermissionSet != null) {
            for (Permission permission : tempPermissionSet) {
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
