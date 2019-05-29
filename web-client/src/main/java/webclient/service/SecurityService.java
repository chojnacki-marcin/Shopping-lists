package webclient.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

@Service
public interface SecurityService {

    boolean isOwner(OAuth2Authentication authentication, long shoppingListId);
}
