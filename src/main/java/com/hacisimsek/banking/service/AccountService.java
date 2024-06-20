package com.hacisimsek.banking.service;

import com.hacisimsek.banking.dto.AccountDto;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    AccountDto createAccount(AccountDto accountDto);
    AccountDto getAccount(Long id);
    List<AccountDto> getAccounts();
    AccountDto updateAccount(Long id, AccountDto accountDto);
    void deleteAccount(Long id);

    //deposit method
    AccountDto deposit(Long id, BigDecimal amount);

    //withdraw method
    AccountDto withdraw(Long id, BigDecimal amount);
}
