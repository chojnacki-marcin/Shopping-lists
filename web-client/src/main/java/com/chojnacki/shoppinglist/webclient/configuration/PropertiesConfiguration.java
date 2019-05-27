package com.chojnacki.shoppinglist.webclient.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("/local.properties")
public class PropertiesConfiguration {
}
