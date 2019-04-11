package com.spring.boot.luggage_claims_system.hirbernia_sina.service;

import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.ClaimInfo;
import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.Permission;
import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.UserInfo;
import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.Role;
import com.spring.boot.luggage_claims_system.hirbernia_sina.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author Liu Dairui
 * @date 2019-04-11 10:27
 */
@Service
public class SecurityDataService {

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

//    @Autowired
//    private PermissionRepository permissionRepository;

    public ClaimInfo saveAndUpdateClaim(ClaimInfo claimInfo) {
        return claimRepository.saveAndFlush(claimInfo);
    }

    public UserInfo saveAndUpdateUser(UserInfo userInfo) {
        return userRepository.saveAndFlush(userInfo);
    }

    public ClaimInfo getClaimById(Long serialNo) {
        return claimRepository.getOne(serialNo);
    }

    public UserInfo getUserById(Long userId) {
        return userRepository.getOne(userId);
    }

    public UserInfo getUserByEmailAddress(String userAddress) {
        return userRepository.findByEmailAddress(userAddress);
    }

    public List<ClaimInfo> getAllClaims() {
        return claimRepository.findAll();
    }

    public Role getRoleByUserId(Long userId) {
        UserInfo userInfo = getUserById(userId);
        return roleRepository.getOne(userInfo.getRole());
    }

    public Set<Permission> getPermissionsByUserId(Long userId) {
        Role role = getRoleByUserId(userId);
        return role.getPermissions();
    }
}
