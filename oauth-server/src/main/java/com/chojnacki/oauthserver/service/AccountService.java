package com.chojnacki.oauthserver.service;

import com.chojnacki.oauthserver.dto.AccountDto;
import com.chojnacki.oauthserver.model.Account;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface AccountService extends UserDetailsService {

    Account createAccount(AccountDto account);

    boolean emailExists(String email);
}
