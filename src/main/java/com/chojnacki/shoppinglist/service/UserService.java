package com.chojnacki.shoppinglist.service;

import com.chojnacki.shoppinglist.model.Account;
import com.chojnacki.shoppinglist.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {

    User createUserFromInternalAccount(Account account);
    Optional<User> getUserFromInternalAccount(Account account);

}
