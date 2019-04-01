package com.spring.boot.luggage_claims_system.hirbernia_sina.repository;

import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.ClaimInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Liu Dairui
 * @date 2019-03-31 14:28
 */
public interface ClaimRepository extends CrudRepository<ClaimInfo, Long> {
}
