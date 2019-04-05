package com.spring.boot.luggage_claims_system.hirbernia_sina.repository;

import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.CustomerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Liu Dairui
 * @date 2019-03-31 15:45
 */
public interface CustomerRepository extends JpaRepository<CustomerInfo, Long> {
}
