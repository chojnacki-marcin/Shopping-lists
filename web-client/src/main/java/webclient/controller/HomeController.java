package webclient.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import webclient.model.ShoppingList;
import webclient.service.ShoppingListService;
import webclient.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    private final ShoppingListService shoppingListService;
    private final UserService userService;

    @Autowired
    public HomeController(ShoppingListService shoppingListService, UserService userService) {
        this.shoppingListService = shoppingListService;
        this.userService = userService;
    }

    @GetMapping
    public String index(Model model, OAuth2Authentication authentication){
        var user = userService.getUserFromAuthentication(authentication);
        List<ShoppingList> shoppingLists = shoppingListService.findAllByAccount(user);

        model.addAttribute("shoppingLists", shoppingLists);
        return "index";
    }
}
