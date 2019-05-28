package webclient.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CompositeFilter;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.Arrays;

@Configuration
@EnableOAuth2Client
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Resource(name = "accessTokenRequest")
    private AccessTokenRequest accessTokenRequest;

    @Bean
    @Qualifier("clientContext")
    @Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
    public OAuth2ClientContext clientContext() {
        return new DefaultOAuth2ClientContext(accessTokenRequest);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .antMatcher("/**")
                    .authorizeRequests().antMatchers( "/login**", "/css/**", "/error**").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .formLogin().loginPage("/login")
                .and()
                    .logout().logoutSuccessUrl("/login").permitAll()
                .and()
                    .addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
//        http
//                .authorizeRequests().antMatchers("/login/**", "/webjars/**", "/error**").permitAll()
//                .and()
//                .authorizeRequests().anyRequest().authenticated()
//                .and()
////                .formLogin().loginPage("/login")
////                .and()
//                .logout().permitAll()
//                .and()
//                .antMatcher("/**")
//                .addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
    }

    private Filter ssoFilter() {
        CompositeFilter filter = new CompositeFilter();
        var emailFilter = ssoFilter(email(), "/login/email");
        var facebookFilter = ssoFilter(facebook(), "/login/facebook");
        var filters = Arrays.asList(emailFilter, facebookFilter);

        filter.setFilters(filters);
        return filter;
    }

    private Filter ssoFilter(ClientResources client, String path) {
        OAuth2ClientAuthenticationProcessingFilter facebookFilter = new OAuth2ClientAuthenticationProcessingFilter(path);
        OAuth2RestTemplate template = new OAuth2RestTemplate(client.getClient(), clientContext());
        facebookFilter.setRestTemplate(template);
        UserInfoTokenServices tokenServices = new UserInfoTokenServices(client.getResource().getUserInfoUri(),
                client.getClient().getClientId());
        tokenServices.setRestTemplate(template);
        facebookFilter.setTokenServices(tokenServices);
        return facebookFilter;
    }

    @Bean
    @ConfigurationProperties("email")
    public ClientResources email() {
        return new ClientResources();
    }

    @Bean
    @ConfigurationProperties("facebook")
    public ClientResources facebook() {
        return new ClientResources();
    }


    @Bean
    public FilterRegistrationBean<OAuth2ClientContextFilter> oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
        FilterRegistrationBean<OAuth2ClientContextFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }

    private static class ClientResources{

        @NestedConfigurationProperty
        private AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails();

        @NestedConfigurationProperty
        private ResourceServerProperties resource = new ResourceServerProperties();

        public AuthorizationCodeResourceDetails getClient() {
            return client;
        }

        public ResourceServerProperties getResource() {
            return resource;
        }
    }
}
