package com.chojnacki.shoppinglist.service;

import com.chojnacki.shoppinglist.model.Account;
import com.chojnacki.shoppinglist.model.ShoppingList;
import com.chojnacki.shoppinglist.model.User;

import java.util.List;
import java.util.Optional;

public interface ShoppingListService {
    void saveShoppingList(ShoppingList shoppingList, Account user);

    Optional<ShoppingList> findById(long id);


    boolean isOwner(User user, long shoppingListId);

    List<ShoppingList> findAllByAccount(Account account);
}
