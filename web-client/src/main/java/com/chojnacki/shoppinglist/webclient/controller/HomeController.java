package com.chojnacki.shoppinglist.webclient.controller;

import com.chojnacki.shoppinglist.webclient.model.ShoppingList;
import com.chojnacki.shoppinglist.webclient.service.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.chojnacki.shoppinglist.webclient.model.User;

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
    public String index(Model model, @AuthenticationPrincipal User user){
        List<ShoppingList> shoppingLists = shoppingListService.findAllByAccount(user);
        model.addAttribute("shoppingLists", shoppingLists);
        return "index";
    }
}
