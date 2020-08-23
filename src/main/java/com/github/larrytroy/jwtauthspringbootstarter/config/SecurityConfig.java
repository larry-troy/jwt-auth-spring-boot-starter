package com.github.larrytroy.jwtauthspringbootstarter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.larrytroy.jwtauthspringbootstarter.property.AuthProperty;
import com.github.larrytroy.jwtauthspringbootstarter.service.JwtTokenService;
import com.github.larrytroy.jwtauthspringbootstarter.filter.JWTAuthenticationFilter;
import com.github.larrytroy.jwtauthspringbootstarter.filter.JWTAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.cors.CorsConfigurationSource;

@ConditionalOnBean(name = "userDetailsServiceImpl")
@ConditionalOnProperty("jwt-auth.auth.url")
@RequiredArgsConstructor
@EnableConfigurationProperties(AuthProperty.class)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenService jwtTokenService;

    private final AuthProperty authProperty;

    private final UserDetailsService userDetailsService;

    private final CorsConfigurationSource corsConfigurationSource;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors().configurationSource(corsConfigurationSource)
                .and().csrf().disable()
                .authorizeRequests()
                .antMatchers(authProperty.getPermitUrls().toArray(new String[authProperty.getPermitUrls().size()]))
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(),
                        authProperty.getUrl(), jwtTokenService,
                        new ObjectMapper()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtTokenService))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
}
