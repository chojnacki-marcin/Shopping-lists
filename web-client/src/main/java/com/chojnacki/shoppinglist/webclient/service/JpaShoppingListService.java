package com.chojnacki.shoppinglist.webclient.service;

import com.chojnacki.shoppinglist.webclient.model.ShoppingList;
import com.chojnacki.shoppinglist.webclient.model.User;
import com.chojnacki.shoppinglist.webclient.repository.ShoppingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JpaShoppingListService implements ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;
    private final UserService userService;

    @Autowired
    public JpaShoppingListService(ShoppingListRepository shoppingListRepository, UserService userService) {
        this.shoppingListRepository = shoppingListRepository;
        this.userService = userService;
    }

    @Override
    public void saveShoppingList(ShoppingList shoppingList, User user){
        shoppingList.setOwner(user);
        shoppingListRepository.save(shoppingList);
    }

    @Override
    public Optional<ShoppingList> findById(long id){
        return shoppingListRepository.findById(id);
    }


    @Override
    public boolean isOwner(User user, long shoppingListId){
        Optional<ShoppingList> result = shoppingListRepository.findById(shoppingListId);
        return result.filter(shoppingList -> shoppingList.getOwner().getUserIdentifier().equals(user.getUserIdentifier()))
                .isPresent();
    }

    @Override
    public List<ShoppingList> findAllByAccount(User user) {
        return shoppingListRepository.findAllByOwner(user);
    }
}
