package com.chojnacki.shoppinglist.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface AccountService extends UserDetailsService {
}
