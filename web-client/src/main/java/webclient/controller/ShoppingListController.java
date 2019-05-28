package webclient.controller;

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
import webclient.exception.ResourceNotFoundException;
import webclient.model.Item;
import webclient.model.ShoppingList;
import webclient.model.User;
import webclient.service.SecurityService;
import webclient.service.ShoppingListService;

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
        return "create";
    }

    @PostMapping
    public String createShoppingList(ShoppingList shoppingList, @AuthenticationPrincipal User user){
        shoppingListService.saveShoppingList(shoppingList, user);
        return String.format("redirect:/shopping-list/%d",shoppingList.getId());
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
    @PreAuthorize("@securityService.isOwner(authentication, #shoppingList.id)")
    public String modifyShoppingList(@PathVariable long id, ShoppingList shoppingList, @AuthenticationPrincipal User user){
        shoppingListService.saveShoppingList(shoppingList, user);
        return String.format("redirect:/shopping-list/%d", id);
    }

    @RequestMapping(value = "{id}", params = {"addItem"})
    @PreAuthorize("@securityService.isOwner(authentication, #shoppingList.id)")
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
    @PreAuthorize("@securityService.isOwner(authentication, #shoppingList.id)")
    public String removeItem(@PathVariable long id, ShoppingList shoppingList, BindingResult bindingResult,
                             HttpServletRequest httpServletRequest, @AuthenticationPrincipal User user){
        int itemNumber = Integer.valueOf(httpServletRequest.getParameter("removeItem"));
        if(shoppingList.getItems() != null && !shoppingList.getItems().isEmpty()
                && itemNumber >= 0 && itemNumber < shoppingList.getItems().size()){

            shoppingList.getItems().remove(itemNumber);

            shoppingListService.saveShoppingList(shoppingList, user);
        }
        return String.format("redirect:/shopping-list/%d", id);
    }
}
