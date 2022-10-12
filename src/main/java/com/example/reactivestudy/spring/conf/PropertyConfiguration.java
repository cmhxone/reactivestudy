package com.example.reactivestudy.spring.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class PropertyConfiguration {

    @Bean(name = "propertySourcesConfigurer")
    public PropertySourcesPlaceholderConfigurer configurer() {

        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();

        configurer.setLocations(new ClassPathResource("database.properties"));
        configurer.setLocalOverride(true);

        return configurer;
    }
}