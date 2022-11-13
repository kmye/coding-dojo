package com.govtech.dojo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.govtech.dojo.model.UserAccount;

@Repository("userAccountRepository")
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    UserAccount findByEmail(String email);
}