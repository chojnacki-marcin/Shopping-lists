package com.chojnacki.oauthserver.controller;

import com.chojnacki.oauthserver.service.AccountService;
import com.chojnacki.oauthserver.dto.AccountDto;
import com.chojnacki.oauthserver.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RequestMapping("/api/accounts")
@RestController
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * @param accountDto - username and password of newly created account
     * @return Creates a new account
     */
    @PostMapping
    public ResponseEntity<?> createAccount(@Valid @RequestBody AccountDto accountDto){
        if(accountService.emailExists(accountDto.getEmail())){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Account account = accountService.createAccount(accountDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(account.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
