package com.spring.boot.luggage_claims_system.hirbernia_sina.repository;

import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.ClaimInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Liu Dairui
 * @date 2019-03-31 14:28
 */
@Repository
public interface ClaimRepository extends JpaRepository<ClaimInfo, Long> {
    List<ClaimInfo> getAllByCustomerId(Long customerId);

    List<ClaimInfo> getAllByEmployeeId(Long employeeId);

    List<ClaimInfo> getAllByEmployeeIdIsNot(Long employeeId);
}
