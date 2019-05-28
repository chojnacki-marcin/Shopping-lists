package oauthserver.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration  extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .antMatcher("/me")
                .authorizeRequests().anyRequest().authenticated();
//                .authorizeRequests().antMatchers("/me", "/oauth/authorize").authenticated()
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                .and()
//                .authorizeRequests().anyRequest().permitAll()
//                .and()
//                .formLogin().permitAll()
//                .and()
//                .logout().permitAll();

    }
}