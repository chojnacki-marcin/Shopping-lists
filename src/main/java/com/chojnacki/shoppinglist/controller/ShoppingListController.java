package com.chojnacki.shoppinglist.controller;

import com.chojnacki.shoppinglist.exception.ResourceNotFoundException;
import com.chojnacki.shoppinglist.model.Item;
import com.chojnacki.shoppinglist.model.ShoppingList;
import com.chojnacki.shoppinglist.model.User;
import com.chojnacki.shoppinglist.service.JpaSecurityService;
import com.chojnacki.shoppinglist.service.JpaShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Optional;

@Controller
@RequestMapping("/shopping-list")
public class ShoppingListController {

    private final JpaShoppingListService shoppingListService;

    private final JpaSecurityService securityService;

    @Autowired
    public ShoppingListController(JpaShoppingListService shoppingListService, JpaSecurityService securityService) {
        this.shoppingListService = shoppingListService;
        this.securityService = securityService;
    }

    @GetMapping
    public String showEmptyShoppingListForm(ShoppingList shoppingList){
        return "create";
    }

    @PostMapping
    public String createShoppingList(ShoppingList shoppingList, @AuthenticationPrincipal User account){
        shoppingListService.saveShoppingList(shoppingList, account);
        return String.format("redirect:/shopping-list/%d",shoppingList.getId());
    }


    @GetMapping("{id}")
    @PreAuthorize("@jpaSecurityService.isOwner(authentication, #id)")
    public String showShoppingList(@PathVariable long id, Model model){
        Optional<ShoppingList> shoppingListOptional = shoppingListService.findById(id);
        if(!shoppingListOptional.isPresent()){
            throw new ResourceNotFoundException();
        }
        ShoppingList shoppingList = shoppingListOptional.get();
        model.addAttribute(shoppingList);
        return "shopping-list";
    }

    @PostMapping("{id}")
    @PreAuthorize("@jpaSecurityService.isOwner(authentication, #shoppingList.id)")
    public String modifyShoppingList(@PathVariable long id, ShoppingList shoppingList, @AuthenticationPrincipal User account){
        shoppingListService.saveShoppingList(shoppingList, account);
        return String.format("redirect:/shopping-list/%d", id);
    }

    @RequestMapping(value = "{id}", params = {"addItem"})
    @PreAuthorize("@jpaSecurityService.isOwner(authentication, #shoppingList.id)")
    public String addItem(@PathVariable long id, ShoppingList shoppingList, BindingResult bindingResult){
        if(shoppingList.getItems() == null){
            shoppingList.setItems(Collections.singletonList(new Item()));
        }
        else {
            shoppingList.getItems().add(new Item());
        }
        return "shopping-list";
    }

    @RequestMapping(value = "{id}", params = {"removeItem"})
    @PreAuthorize("@jpaSecurityService.isOwner(authentication, #shoppingList.id)")
    public String removeItem(@PathVariable long id, ShoppingList shoppingList, BindingResult bindingResult, HttpServletRequest httpServletRequest, @AuthenticationPrincipal User account){
        int itemNumber = Integer.valueOf(httpServletRequest.getParameter("removeItem"));
        if(shoppingList.getItems() != null && !shoppingList.getItems().isEmpty()
                && itemNumber >= 0 && itemNumber < shoppingList.getItems().size()){

            shoppingList.getItems().remove(itemNumber);

            shoppingListService.saveShoppingList(shoppingList, account);
        }
        return String.format("redirect:/shopping-list/%d", id);
    }
}
