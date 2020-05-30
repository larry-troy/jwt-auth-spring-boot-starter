package github.com.larrytroy.jwtauthspringbootstarter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import github.com.larrytroy.jwtauthspringbootstarter.property.JwtProperty;
import github.com.larrytroy.jwtauthspringbootstarter.service.JwtAccessTokenService;
import github.com.larrytroy.jwtauthspringbootstarter.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

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
}
