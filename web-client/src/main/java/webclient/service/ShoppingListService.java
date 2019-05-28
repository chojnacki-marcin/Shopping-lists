package webclient.service;


import webclient.model.ShoppingList;
import webclient.model.User;

import java.util.List;
import java.util.Optional;

public interface ShoppingListService {

    void saveShoppingList(ShoppingList shoppingList, User user);

    Optional<ShoppingList> findById(long id);


    boolean isOwner(User user, long shoppingListId);


    List<ShoppingList> findAllByAccount(User user);
}
