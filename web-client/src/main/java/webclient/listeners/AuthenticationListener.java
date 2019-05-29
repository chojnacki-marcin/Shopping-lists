package webclient.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import webclient.configuration.SecurityConfiguration;
import webclient.service.UserService;


@Component
public class AuthenticationListener implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {

    private UserService userService;
    private SecurityConfiguration.ClientResources oauthClient;

    @Autowired
    public AuthenticationListener(UserService userService, SecurityConfiguration.ClientResources facebook) {
        this.userService = userService;
        this.oauthClient = facebook;
    }

    @Override
    public void onApplicationEvent(final InteractiveAuthenticationSuccessEvent event) {

    }



}