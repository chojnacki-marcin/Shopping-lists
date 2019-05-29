package webclient.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import webclient.model.User;

@Service("securityService")
public class JpaSecurityService implements SecurityService {

    private final JpaShoppingListService shoppingListService;
    private final UserService userService;

    @Autowired
    public JpaSecurityService(JpaShoppingListService shoppingListService, UserService userService) {
        this.shoppingListService = shoppingListService;
        this.userService = userService;
    }

    @Override
    public boolean isOwner(OAuth2Authentication authentication, long shoppingListId) {
        String identifier = userService.getUniqueIdentifier(authentication);
        return shoppingListService.isOwner(identifier, shoppingListId);
    }
}
