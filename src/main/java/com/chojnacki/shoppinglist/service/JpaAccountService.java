package com.chojnacki.shoppinglist.service;

import com.chojnacki.shoppinglist.dto.UserDto;
import com.chojnacki.shoppinglist.repository.AccountRepository;
import com.chojnacki.shoppinglist.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class JpaAccountService implements AccountService{

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    public JpaAccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder, UserService userService){
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    public Account loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email);
        if(account == null){
            throw new UsernameNotFoundException("User " + email + " not found.");
        }
        return account;
    }


    @Override
    public void createAccount(UserDto accountDto) {
        Account account = new Account();
        String encryptedPassword = passwordEncoder.encode(accountDto.getPassword());

        account.setEmail(accountDto.getEmail());
        account.setEncryptedPassword(encryptedPassword);
        accountRepository.save(account);

        userService.createUserFromInternalAccount(account);
    }

    @Override
    public boolean emailExists(String email) {
        return accountRepository.existsAccountByEmail(email);
    }
}
