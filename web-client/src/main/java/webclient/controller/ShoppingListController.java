package webclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import webclient.exception.ResourceNotFoundException;
import webclient.model.Item;
import webclient.model.ShoppingList;
import webclient.model.User;
import webclient.service.SecurityService;
import webclient.service.ShoppingListService;
import webclient.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Optional;

@Controller
@RequestMapping("/shopping-list")
public class ShoppingListController {

    private final ShoppingListService shoppingListService;

    private final SecurityService securityService;
    private final UserService userService;

    @Autowired
    public ShoppingListController(ShoppingListService shoppingListService, SecurityService securityService, UserService userService) {
        this.shoppingListService = shoppingListService;
        this.securityService = securityService;
        this.userService = userService;
    }

    @GetMapping
    public String showEmptyShoppingListForm(ShoppingList shoppingList){
        return "create";
    }

    @PostMapping
    public String createShoppingList(ShoppingList shoppingList, OAuth2Authentication authentication){
        var user = userService.getUserFromAuthentication(authentication);

        shoppingListService.saveShoppingList(shoppingList, user);
        return String.format("redirect:/shopping-list/%d",shoppingList.getId());
    }


    @GetMapping("{id}")
    @PreAuthorize("@securityService.isOwner(#authentication, #id)")
    public String showShoppingList(@PathVariable long id, Model model, OAuth2Authentication authentication){
        Optional<ShoppingList> shoppingListOptional = shoppingListService.findById(id);
        if(shoppingListOptional.isEmpty()){
            throw new ResourceNotFoundException();
        }
        ShoppingList shoppingList = shoppingListOptional.get();
        model.addAttribute(shoppingList);
        return "shopping-list";
    }

    @PostMapping("{id}")
    @PreAuthorize("@securityService.isOwner(#authentication, #shoppingList.id)")
    public String modifyShoppingList(@PathVariable long id, ShoppingList shoppingList, OAuth2Authentication authentication){
        var user = userService.getUserFromAuthentication(authentication);

        shoppingListService.saveShoppingList(shoppingList, user);
        return String.format("redirect:/shopping-list/%d", id);
    }

    @RequestMapping(value = "{id}", params = {"addItem"})
    @PreAuthorize("@securityService.isOwner(#authentication, #shoppingList.id)")
    public String addItem(@PathVariable long id, ShoppingList shoppingList, BindingResult bindingResult,
                          OAuth2Authentication authentication){
        if(shoppingList.getItems() == null){
            shoppingList.setItems(Collections.singletonList(new Item()));
        }
        else {
            shoppingList.getItems().add(new Item());
        }
        return "shopping-list";
    }

    @RequestMapping(value = "{id}", params = {"removeItem"})
    @PreAuthorize("@securityService.isOwner(#authentication, #shoppingList.id)")
    public String removeItem(@PathVariable long id, ShoppingList shoppingList, BindingResult bindingResult,
                             HttpServletRequest httpServletRequest, @AuthenticationPrincipal User user,
                             OAuth2Authentication authentication){
        int itemNumber = Integer.valueOf(httpServletRequest.getParameter("removeItem"));
        if(shoppingList.getItems() != null && !shoppingList.getItems().isEmpty()
                && itemNumber >= 0 && itemNumber < shoppingList.getItems().size()){

            shoppingList.getItems().remove(itemNumber);

            shoppingListService.saveShoppingList(shoppingList, user);
        }
        return String.format("redirect:/shopping-list/%d", id);
    }
}
