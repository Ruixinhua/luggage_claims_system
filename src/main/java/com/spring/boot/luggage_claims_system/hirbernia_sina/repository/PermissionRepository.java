package com.spring.boot.luggage_claims_system.hirbernia_sina.repository;

import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Liu Dairui
 * @date 2019-04-14 12:19
 */
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
}
