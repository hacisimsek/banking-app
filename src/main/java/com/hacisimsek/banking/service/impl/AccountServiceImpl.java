package com.hacisimsek.banking.service.impl;

import com.hacisimsek.banking.dto.AccountDto;
import com.hacisimsek.banking.entity.Account;
import com.hacisimsek.banking.exception.AccountNotFoundException;
import com.hacisimsek.banking.exception.InvalidAmountException;
import com.hacisimsek.banking.mapper.AccountMapper;
import com.hacisimsek.banking.repository.AccountRepository;
import com.hacisimsek.banking.service.AccountService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccount(Long id) {
        Account account = accountRepository.findById(id).orElse(null);
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public List<AccountDto> getAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return AccountMapper.mapToAccountDtoList(accounts);
    }

    @Override
    public AccountDto updateAccount(Long id, AccountDto accountDto) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + id));

        account.setAccountHolderName(accountDto.getAccountHolderName());
        account.setBalance(accountDto.getBalance());

        Account updatedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(updatedAccount);
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public AccountDto deposit(Long id, String amount) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + id));

        BigDecimal depositAmount = parseAmount(amount);
        BigDecimal currentBalance = new BigDecimal(account.getBalance());
        BigDecimal newBalance = currentBalance.add(depositAmount);
        account.setBalance(newBalance.toString());

        Account updatedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(updatedAccount);
    }

    private BigDecimal parseAmount(String amount) {
        try {
            return new BigDecimal(amount);
        } catch (NumberFormatException e) {
            throw new InvalidAmountException("Invalid amount: " + amount, e);
        }
    }
}
