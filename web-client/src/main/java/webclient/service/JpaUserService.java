package webclient.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import webclient.configuration.SecurityConfiguration;
import webclient.model.ProviderType;
import webclient.model.User;
import webclient.repository.UserRepository;

import java.util.List;

@Service
public class JpaUserService implements UserService {

    private final UserRepository userRepository;
    private final List<SecurityConfiguration.ClientResources> oauthClients;


    @Autowired
    public JpaUserService(UserRepository userRepository, List<SecurityConfiguration.ClientResources> oauthClients) {
        this.userRepository = userRepository;
        this.oauthClients = oauthClients;
    }

    @Override
    public User getUserFromAuthentication(OAuth2Authentication authentication) {

        String principal = (String) authentication.getPrincipal();
        var providerType = getProviderType(authentication);

        String userId = getUniqueIdentifier(providerType, principal);

        var userOptional = userRepository.findById(userId);
        //create new user if the principal isn't already linked with user entity
        if(userOptional.isEmpty()) {
            return createUser(providerType, userId);
        }

        return userOptional.get();
    }

    private User createUser(ProviderType providerType, String userId) {
        User user = new User();
        user.setProvider(providerType);
        user.setUserIdentifier(userId);
        userRepository.save(user);
        return user;
    }

    private ProviderType getProviderType(OAuth2Authentication authentication){
        var requestClientId = authentication.getOAuth2Request().getClientId();
        var clientType = oauthClients.stream().filter(c -> c.getClient().getClientId().equals(requestClientId)).findFirst().orElseThrow();
        return clientType.getProviderType();
    }

    private String getUniqueIdentifier(ProviderType providerType, String principal) {
        return String.format("%s:%s", providerType.toString(), principal);
    }

    @Override
    public String getUniqueIdentifier(OAuth2Authentication authentication) {
        var providerType = getProviderType(authentication);
        String principal = (String) authentication.getPrincipal();
        return String.format("%s:%s", providerType.toString(), principal);
    }
}
