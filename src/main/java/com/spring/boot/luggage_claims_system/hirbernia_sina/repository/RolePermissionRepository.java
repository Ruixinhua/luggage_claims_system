package com.spring.boot.luggage_claims_system.hirbernia_sina.repository;

import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Liu Dairui
 * @date 2019-04-14 12:59
 */
public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {
    RolePermission findRolePermissionByRolesId(int rolesId);
}
