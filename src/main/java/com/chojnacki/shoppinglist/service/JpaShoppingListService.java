package com.chojnacki.shoppinglist.service;

import com.chojnacki.shoppinglist.model.User;
import com.chojnacki.shoppinglist.repository.ShoppingListRepository;
import com.chojnacki.shoppinglist.model.ShoppingList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JpaShoppingListService implements ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;

    @Autowired
    public JpaShoppingListService(ShoppingListRepository shoppingListRepository) {
        this.shoppingListRepository = shoppingListRepository;
    }

    public void saveShoppingList(ShoppingList shoppingList, User account){
        shoppingList.setOwner(account);
        shoppingListRepository.save(shoppingList);
    }

    @Override
    public Optional<ShoppingList> findById(long id){
        return shoppingListRepository.findById(id);
    }

    @Override
    public List<ShoppingList> findAllByAccountId(long accountId){
        return shoppingListRepository.findAllByAccountId(accountId);
    }

    @Override
    public boolean isOwner(User user, long shoppingListId){
        Optional<ShoppingList> result = shoppingListRepository.findById(shoppingListId);
        return result.filter(shoppingList -> shoppingList.getOwner().getUserIdentifier().equals(user.getUserIdentifier()))
                .isPresent();
    }
}
