package com.chojnacki.shoppinglist.service;

import com.chojnacki.shoppinglist.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface AccountService extends UserDetailsService {

    void createAccount(UserDto account);

    boolean emailExists(String email);
}
