package com.spring.boot.luggage_claims_system.hirbernia_sina.repository;

import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Liu Dairui
 * @date 2019-04-27 18:20
 */
@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {
    List<Policy> getAllByCustomerId(Long customerId);
}
