package com.chojnacki.shoppinglist.webclient.service;


import com.chojnacki.shoppinglist.webclient.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class JpaSecurityService implements SecurityService{

    private final JpaShoppingListService shoppingListService;
    private final UserService userService;

    @Autowired
    public JpaSecurityService(JpaShoppingListService shoppingListService, UserService userService) {
        this.shoppingListService = shoppingListService;
        this.userService = userService;
    }

    @Override
    public boolean isOwner(Authentication authentication, long shoppingListId){
        User user = (User) authentication.getPrincipal();
        return shoppingListService.isOwner(user, shoppingListId);
    }
}
