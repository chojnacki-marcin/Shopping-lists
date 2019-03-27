package com.chojnacki.shoppinglist.repository;

import com.chojnacki.shoppinglist.model.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {
    List<ShoppingList> findAllByAccountId(long id);
}
