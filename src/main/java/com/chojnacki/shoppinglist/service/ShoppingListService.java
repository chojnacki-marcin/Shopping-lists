package com.chojnacki.shoppinglist.service;

import com.chojnacki.shoppinglist.repository.ShoppingListRepository;
import com.chojnacki.shoppinglist.model.Account;
import com.chojnacki.shoppinglist.model.ShoppingList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;

    @Autowired
    public ShoppingListService(ShoppingListRepository shoppingListRepository) {
        this.shoppingListRepository = shoppingListRepository;
    }

    public void saveShoppingList(ShoppingList shoppingList, Account account){
        shoppingList.setAccount(account);
        shoppingListRepository.save(shoppingList);
    }

    public Optional<ShoppingList> findById(long id){
        return shoppingListRepository.findById(id);
    }

    public List<ShoppingList> findAllByAccountId(long accountId){
        return shoppingListRepository.findAllByAccountId(accountId);
    }

    public boolean isOwner(Account account, long shoppingListId){
        Optional<ShoppingList> result = shoppingListRepository.findById(shoppingListId);
        return result.filter(shoppingList -> shoppingList.getAccount().getId() == account.getId()).isPresent();
    }
}
