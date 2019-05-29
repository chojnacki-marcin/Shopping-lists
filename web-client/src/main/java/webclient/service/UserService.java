package webclient.service;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import webclient.model.User;

@Service
public interface UserService {


    User getUserFromAuthentication(OAuth2Authentication authentication);

    String getUniqueIdentifier(OAuth2Authentication authentication);
}
