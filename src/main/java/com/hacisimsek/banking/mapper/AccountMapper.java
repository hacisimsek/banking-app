package com.hacisimsek.banking.mapper;

import com.hacisimsek.banking.dto.AccountDto;
import com.hacisimsek.banking.entity.Account;

import java.util.List;

public class AccountMapper {

    public static Account mapToAccount(AccountDto accountDto) {
        Account account = new Account(
                accountDto.getId(),
                accountDto.getAccountHolderName(),
                accountDto.getBalance()
        );
        return account;
    }

    public static AccountDto mapToAccountDto(Account account) {
        AccountDto accountDto = new AccountDto(
                account.getId(),
                account.getAccountHolderName(),
                account.getBalance()
        );
        return accountDto;
    }

    public static List<AccountDto> mapToAccountDtoList(List<Account> accounts) {
        return accounts.stream()
                .map(account -> new AccountDto(
                        account.getId(),
                        account.getAccountHolderName(),
                        account.getBalance()
                ))
                .toList();
    }
}
