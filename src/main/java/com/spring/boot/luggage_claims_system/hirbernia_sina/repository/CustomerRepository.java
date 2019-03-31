package com.spring.boot.luggage_claims_system.hirbernia_sina.repository;

import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.CustomerInfo;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Liu Dairui
 * @date 2019-03-31 15:45
 */
public interface CustomerRepository extends CrudRepository<CustomerInfo, Long> {
}
