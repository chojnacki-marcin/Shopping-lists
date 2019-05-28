package webclient.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface SecurityService {
    boolean isOwner(Authentication authentication, long shoppingListId);
}
