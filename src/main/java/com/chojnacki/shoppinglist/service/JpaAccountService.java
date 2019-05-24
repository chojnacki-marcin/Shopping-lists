package com.chojnacki.shoppinglist.service;

import com.chojnacki.shoppinglist.repository.AccountRepository;
import com.chojnacki.shoppinglist.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class JpaAccountService implements AccountService{

    private AccountRepository accountRepository;

    @Autowired
    public JpaAccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @Override
    public Account loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email);
        if(account == null){
            throw new UsernameNotFoundException("User " + email + " not found.");
        }
        return account;
    }


}
