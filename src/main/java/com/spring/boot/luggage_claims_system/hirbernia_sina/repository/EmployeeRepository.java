package com.spring.boot.luggage_claims_system.hirbernia_sina.repository;

import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.EmployeeInfo;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Liu Dairui
 * @date 2019-03-30 23:00
 */
public interface EmployeeRepository extends CrudRepository<EmployeeInfo, Long> {
}
