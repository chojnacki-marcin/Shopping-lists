package com.chojnacki.shoppinglist.service;

import com.chojnacki.shoppinglist.model.Account;
import com.chojnacki.shoppinglist.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class JpaSecurityService implements SecurityService{

    private final JpaShoppingListService shoppingListService;
    private final AccountRepository accountRepository;
    private final UserService userService;

    @Autowired
    public JpaSecurityService(JpaShoppingListService shoppingListService, AccountRepository accountRepository, UserService userService) {
        this.shoppingListService = shoppingListService;
        this.accountRepository = accountRepository;
        this.userService = userService;
    }

    @Override
    public boolean isOwner(Authentication authentication, long shoppingListId){
        String email = authentication.getName();
        Account account = accountRepository.findByEmail(email);
        var user = userService.getUserFromInternalAccount(account);
        return user.isPresent() && shoppingListService.isOwner(user.get(), shoppingListId);
    }
}
