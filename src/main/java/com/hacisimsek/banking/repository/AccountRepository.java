package com.hacisimsek.banking.repository;

import com.hacisimsek.banking.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository  extends JpaRepository<Account, Long> {
}
