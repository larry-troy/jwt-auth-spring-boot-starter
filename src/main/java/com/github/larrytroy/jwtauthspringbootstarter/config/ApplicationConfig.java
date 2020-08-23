package com.github.larrytroy.jwtauthspringbootstarter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.larrytroy.jwtauthspringbootstarter.encoder.DefaultPasswordEncoder;
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

@Import({SecurityConfig.class})
@RequiredArgsConstructor
@EnableConfigurationProperties(JwtProperty.class)
@Configuration
public class ApplicationConfig {

    private final JwtProperty jwtProperty;

    @Bean
    public JwtTokenService jwtAccessTokenService() {
        return new JwtAccessTokenService(new ObjectMapper(), jwtProperty);
    }

    @ConditionalOnMissingBean
    @Bean
    public PasswordEncoder jwtAuthPasswordEncoder() {
        return new DefaultPasswordEncoder();
    }
}
