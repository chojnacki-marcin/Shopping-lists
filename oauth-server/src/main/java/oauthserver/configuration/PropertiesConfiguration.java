package oauthserver.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
@PropertySource("/local.properties")
public class PropertiesConfiguration {

}
