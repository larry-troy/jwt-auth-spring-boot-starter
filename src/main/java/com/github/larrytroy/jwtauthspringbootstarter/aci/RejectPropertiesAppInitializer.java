package com.github.larrytroy.jwtauthspringbootstarter.aci;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePropertySource;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class RejectPropertiesAppInitializer implements ApplicationContextInitializer {

    public static final String DEFAULT_APPLICATION_PROPERTIES = "application.yml";

    public static final List<String> requiredProperties = Arrays.asList("jwt-auth",
            "jwt-auth.auth.url", "jwt-auth.jwt", "jwt-auth.jwt.expiration-time",
            "jwt-auth.jwt.token-prefix", "jwt-auth.jwt.secret-key");

    @Override
    public void initialize(final ConfigurableApplicationContext applicationContext) {
        final ConfigurableEnvironment environment = applicationContext.getEnvironment();
        PropertySource propertySource = getApplicationProperties(environment);

        for (String requiredProperty : requiredProperties) {
            if (propertySource.getProperty(requiredProperty) == null) {
                String errorMessage = String.format("Property '%s' is missing from application.ym",
                        propertySource);

                throw new RuntimeException(errorMessage);
            }
        }
    }

    protected PropertySource<?> getApplicationProperties(final ConfigurableEnvironment environment) {
        try {
            final Resource propertiesResource = new ClassPathResource(DEFAULT_APPLICATION_PROPERTIES);
            final ResourcePropertySource propertySource = new ResourcePropertySource(propertiesResource);
            return propertySource;
        } catch (final IOException ex) {
            throw new RuntimeException("Unable to load application properties from default location: "
                    + DEFAULT_APPLICATION_PROPERTIES, ex);
        }
    }
}
