package com.chojnacki.shoppinglist.controller;

import com.chojnacki.shoppinglist.exception.ResourceNotFoundException;
import com.chojnacki.shoppinglist.model.Account;
import com.chojnacki.shoppinglist.model.Item;
import com.chojnacki.shoppinglist.model.ShoppingList;
import com.chojnacki.shoppinglist.service.SecurityService;
import com.chojnacki.shoppinglist.service.ShoppingListService;
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

    private final ShoppingListService shoppingListService;

    private final SecurityService securityService;

    @Autowired
    public ShoppingListController(ShoppingListService shoppingListService, SecurityService securityService) {
        this.shoppingListService = shoppingListService;
        this.securityService = securityService;
    }

    @GetMapping
    public String showEmptyShoppingListForm(ShoppingList shoppingList){
//        List<Item> items = new ArrayList<>();
//        model.addAttribute(items);
        return "create";
    }

    @PostMapping
    public String createShoppingList(ShoppingList shoppingList, @AuthenticationPrincipal Account account){
        shoppingListService.saveShoppingList(shoppingList, account);
        return "redirect:/shopping-list/" + shoppingList.getId();
    }


    @GetMapping("{id}")
    @PreAuthorize("@securityService.isOwner(authentication, #id)")
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
    public String modifyShoppingList(@PathVariable long id, ShoppingList shoppingList, @AuthenticationPrincipal Account account){
        shoppingListService.saveShoppingList(shoppingList, account);
        return "redirect:/shopping-list/" + id;
    }

    @RequestMapping(value = "{id}", params = {"addItem"})
    public String addItem(@PathVariable long id, ShoppingList shoppingList, BindingResult bindingResult){
//        Optional<ShoppingList> shoppingList = shoppingListService.findById(id);
//        shoppingList.ifPresent(s -> s.getItems().add(new Item()));
        if(shoppingList.getItems() == null){
            shoppingList.setItems(Collections.singletonList(new Item()));
        }
        else {
            shoppingList.getItems().add(new Item());
        }
        return "shopping-list";
    }

    @RequestMapping(value = "{id}", params = {"removeItem"})
    public String removeItem(@PathVariable long id, ShoppingList shoppingList, BindingResult bindingResult, HttpServletRequest httpServletRequest, @AuthenticationPrincipal Account account){
        int itemNumber = Integer.valueOf(httpServletRequest.getParameter("removeItem"));
        if(shoppingList.getItems() != null && !shoppingList.getItems().isEmpty() && itemNumber >= 0 && itemNumber < shoppingList.getItems().size()){
            shoppingList.getItems().remove(itemNumber);
            shoppingListService.saveShoppingList(shoppingList, account);
        }
        return "redirect:/shopping-list/" + id;
    }
}
