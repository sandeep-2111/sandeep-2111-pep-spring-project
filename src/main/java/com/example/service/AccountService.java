package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account register(Account account) {

        if (account.getUsername().isBlank() || account.getPassword().length() < 4) {
            return null;// Return null if validation fails
        }

    
        if (accountRepository.findByUsername(account.getUsername()).isPresent()) {
            return null;// Return null if username is already taken
        }
        return accountRepository.save(account);// Save and return the new account
    }

  
    public Account login(Account account) {

       
        Optional<Account> existingAccount = accountRepository.findByUsername(account.getUsername());

        if (existingAccount.isPresent() && existingAccount.get().getPassword().equals(account.getPassword())) {
            return existingAccount.get();// Return the account if login is successful
        }
        return null;
    }
    
}