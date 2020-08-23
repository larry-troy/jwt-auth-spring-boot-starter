package com.github.larrytroy.jwtauthspringbootstarter.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.larrytroy.jwtauthspringbootstarter.dto.AuthDto;
import com.github.larrytroy.jwtauthspringbootstarter.dto.JwtPayloadDto;
import com.github.larrytroy.jwtauthspringbootstarter.service.JwtTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenService jwtTokenService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager,
                                   String url,
                                   JwtTokenService jwtTokenService,
                                   ObjectMapper objectMapper) {
        this.authenticationManager = authenticationManager;
        this.setFilterProcessesUrl(url);
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            AuthDto creds = new ObjectMapper()
                    .readValue(req.getInputStream(), AuthDto.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            Collections.emptyList())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res,
                                            FilterChain chain, Authentication auth){
        res.setContentType(MediaType.APPLICATION_JSON.toString());
        UserDetails user = (UserDetails) auth.getPrincipal();
        JwtPayloadDto jwtPayloadDto = new JwtPayloadDto(user.getUsername(), Collections.emptyList());
        String jwtAccessToken = jwtTokenService.create(jwtPayloadDto);
        log.info("Generate access token for username: {}", user.getUsername());
        res.setHeader(HttpHeaders.AUTHORIZATION, jwtAccessToken);
    }
}
