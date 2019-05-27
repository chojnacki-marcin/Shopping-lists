package com.chojnacki.shoppinglist.webclient.service;

import com.chojnacki.shoppinglist.webclient.model.ShoppingList;
import com.chojnacki.shoppinglist.webclient.model.User;

import java.util.List;
import java.util.Optional;

public interface ShoppingListService {

    void saveShoppingList(ShoppingList shoppingList, User user);

    Optional<ShoppingList> findById(long id);


    boolean isOwner(User user, long shoppingListId);


    List<ShoppingList> findAllByAccount(User user);
}
