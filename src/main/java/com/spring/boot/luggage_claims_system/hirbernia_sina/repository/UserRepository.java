package com.spring.boot.luggage_claims_system.hirbernia_sina.repository;

import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Liu Dairui
 * @date 2019-03-30 23:00
 */
@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {

    UserInfo findByEmailAddress(String emailAddress);
}
