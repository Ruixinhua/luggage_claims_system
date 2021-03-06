package com.spring.boot.luggage_claims_system.hirbernia_sina.repository;

import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Liu Dairui
 * @date 2019-04-11 12:39
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
