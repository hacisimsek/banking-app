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

    private final AccountRepository accountRepository;

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
        Account account = findAccountById(id);
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public List<AccountDto> getAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return AccountMapper.mapToAccountDtoList(accounts);
    }

    @Override
    public AccountDto updateAccount(Long id, AccountDto accountDto) {
        Account account = findAccountById(id);
        updateAccountDetails(account, accountDto);
        Account updatedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(updatedAccount);
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public AccountDto deposit(Long id, String amount) {
        Account account = findAccountById(id);
        BigDecimal depositAmount = parseAmount(amount);
        updateAccountBalance(account, depositAmount, true);
        Account updatedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(updatedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, String amount) {
        Account account = findAccountById(id);
        BigDecimal withdrawAmount = parseAmount(amount);
        updateAccountBalance(account, withdrawAmount, false);
        Account updatedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(updatedAccount);
    }

    private Account findAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + id));
    }

    private void updateAccountDetails(Account account, AccountDto accountDto) {
        account.setAccountHolderName(accountDto.getAccountHolderName());
        account.setBalance(accountDto.getBalance());
    }

    private BigDecimal parseAmount(String amount) {
        try {
            return new BigDecimal(amount);
        } catch (NumberFormatException e) {
            throw new InvalidAmountException("Invalid amount: " + amount, e);
        }
    }

    private void updateAccountBalance(Account account, BigDecimal amount, boolean isDeposit) {
        BigDecimal currentBalance = new BigDecimal(account.getBalance());
        BigDecimal newBalance = isDeposit ? currentBalance.add(amount) : currentBalance.subtract(amount);
        account.setBalance(newBalance.toString());
    }
}