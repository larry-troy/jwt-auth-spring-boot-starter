package com.github.larrytroy.jwtauthspringbootstarter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.larrytroy.jwtauthspringbootstarter.encoder.DefaultPasswordEncoder;
import com.github.larrytroy.jwtauthspringbootstarter.property.CorsProperty;
import com.github.larrytroy.jwtauthspringbootstarter.service.JwtAccessTokenService;
import com.github.larrytroy.jwtauthspringbootstarter.property.JwtProperty;
import com.github.larrytroy.jwtauthspringbootstarter.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@Import({SecurityConfig.class})
@RequiredArgsConstructor
@EnableConfigurationProperties({JwtProperty.class, CorsProperty.class})
@Configuration
public class ApplicationConfig {

    private final JwtProperty jwtProperty;

    private final CorsProperty corsProperty;

    @Bean
    public JwtTokenService jwtAccessTokenService() {
        return new JwtAccessTokenService(new ObjectMapper(), jwtProperty);
    }

    @ConditionalOnMissingBean
    @Bean
    public PasswordEncoder jwtAuthPasswordEncoder() {
        return new DefaultPasswordEncoder();
    }

    @ConditionalOnMissingBean
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        if (corsProperty.getEnabled() != null && corsProperty.getEnabled()) {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowCredentials(true);
            configuration.setAllowedOrigins(corsProperty.getAllowedOrigins());
            configuration.setAllowedMethods(Arrays.asList("GET", "PUT", "POST", "DELETE"));
            configuration.setAllowedHeaders(Arrays.asList("X-Requested-With","Origin","Content-Type","Accept","Authorization"));
            configuration.setExposedHeaders(Arrays.asList("Access-Control-Allow-Headers", "Authorization, x-xsrf-token, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, " +
                    "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers"));
            source.registerCorsConfiguration("/**", configuration);
        }

        return source;
    }
}
