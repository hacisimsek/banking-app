package com.hacisimsek.banking.controller;

import com.hacisimsek.banking.dto.AccountDto;
import com.hacisimsek.banking.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

     @PostMapping
     public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto) {
         return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
     }

     @GetMapping("/{id}")
     public ResponseEntity<AccountDto> getAccount(@PathVariable Long id) {
        return new ResponseEntity<>(accountService.getAccount(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AccountDto>> getAccounts() {
        return new ResponseEntity<>(accountService.getAccounts(), HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable Long id, @RequestBody AccountDto accountDto) {
        return new ResponseEntity<>(accountService.updateAccount(id, accountDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<AccountDto> deposit(@PathVariable Long id, @RequestBody Map<String, String> amount) {
        String amountStr = amount.get("amount");
        return new ResponseEntity<>(accountService.deposit(id, amountStr), HttpStatus.OK);
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<AccountDto> withdraw(@PathVariable Long id, @RequestBody Map<String, String> amount) {
        String amountStr = amount.get("amount");
        return new ResponseEntity<>(accountService.withdraw(id, amountStr), HttpStatus.OK);
    }
}
