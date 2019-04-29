package com.spring.boot.luggage_claims_system.hirbernia_sina.service;

import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.*;
import com.spring.boot.luggage_claims_system.hirbernia_sina.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Autowired
    private PolicyRepository policyRepository;

    public ClaimInfo saveAndUpdateClaim(ClaimInfo claimInfo) {
        return claimRepository.saveAndFlush(claimInfo);
    }

    public List<ClaimInfo> getAllClaimsByCustomerId(Long customerId) {
        return claimRepository.getAllByCustomerId(customerId);
    }

    public List<ClaimInfo> getAllClaimsByEmployeeId(Long employeeId) {
        return claimRepository.getAllByEmployeeId(employeeId);
    }

    public List<ClaimInfo> getAllClaimsByEmployeeIdIsNot(Long employeeId) {
        return claimRepository.getAllByEmployeeIdIsNot(employeeId);
    }
    public Policy getPolicyById(Long id) {
        return policyRepository.findById(id).orElse(null);
    }

    public List<Policy> getAllPoliciesByCustomerId(Long customerId) {
        return policyRepository.getAllByCustomerId(customerId);
    }

    public UserInfo saveUser(UserInfo userInfo) {
        if (emailExist(userInfo.getEmailAddress())) {
            return null;
        }
        // the rest of the registration operation
        return userRepository.save(userInfo);
    }

    public UserInfo updateUser(UserInfo userInfo){
        return userRepository.save(userInfo);
    }

    private boolean emailExist(String email) {
        UserInfo user = userRepository.findByEmailAddress(email);
        if (user != null) {
            return true;
        }
        return false;
    }

    public ClaimInfo getClaimById(Long serialNo) {
        return claimRepository.findById(serialNo).orElse(null);
    }

    public UserInfo getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public UserInfo getUserByEmailAddress(String userAddress) {
        return userRepository.findByEmailAddress(userAddress);
    }

    public List<ClaimInfo> getAllClaims() {
        return claimRepository.findAll();
    }

    public Set<Role> getRolesByUserId(Long userId) {
        UserInfo userInfo = getUserById(userId);
        System.out.println(userInfo);
        userInfo.getRoles().add(roleRepository.getOne(userInfo.getRole()));
        System.out.println(userInfo);
        return userInfo.getRoles();
    }

    public Role getRoleById(int roleId) {
        return roleRepository.getOne(roleId);
    }

    public Permission getPermissionById(int permissionId) {
        return permissionRepository.getOne(permissionId);
    }

    public Set<Permission> getPermissionsByUserId(UserInfo userInfo) {
//        System.out.println(userInfo);
        Set<Permission> permissions = new HashSet<>();
        for (Role r : userInfo.getRoles()) {
            RolePermission rolePermission = rolePermissionRepository.findRolePermissionByRolesId(r.getId());
//            System.out.println(rolePermission);
            int permissionId = rolePermission.getPermissionsId();
            permissions.add(permissionRepository.getOne(permissionId));
        }
        return permissions;
    }
}
