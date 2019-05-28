package webclient.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import webclient.model.ShoppingList;
import webclient.model.User;
import webclient.service.ShoppingListService;

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
