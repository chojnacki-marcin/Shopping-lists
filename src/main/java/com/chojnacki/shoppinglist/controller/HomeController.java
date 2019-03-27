package com.chojnacki.shoppinglist.controller;

import com.chojnacki.shoppinglist.model.Account;
import com.chojnacki.shoppinglist.model.ShoppingList;
import com.chojnacki.shoppinglist.service.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    private final ShoppingListService shoppingListService;

    @Autowired
    public HomeController(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    @GetMapping
    public String index(Model model, @AuthenticationPrincipal Account account){
        List<ShoppingList> shoppingLists = shoppingListService.findAllByAccountId(account.getId());
        model.addAttribute("shoppingLists", shoppingLists);
        return "index";
    }
}
