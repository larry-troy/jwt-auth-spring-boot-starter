package com.github.larrytroy.jwtauthspringbootstarter.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.larrytroy.jwtauthspringbootstarter.dto.JwtPayloadDto;
import com.github.larrytroy.jwtauthspringbootstarter.property.JwtProperty;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Date;

@RequiredArgsConstructor
public class JwtAccessTokenService implements JwtTokenService {

    private final ObjectMapper objectMapper;

    private final JwtProperty jwtProperty;

    @Override
    public String create(@NonNull JwtPayloadDto jwtPayloadDto) {
        try {
            String subject = objectMapper.writeValueAsString(jwtPayloadDto);
            String jwtToken = JWT.create()
                    .withSubject(subject)
                    .withExpiresAt(new Date(System.currentTimeMillis() + jwtProperty.getExpirationTime()))
                    .sign(Algorithm.HMAC512(jwtProperty.getSecretKey().getBytes()));
            return String.format("%s %s", jwtProperty.getTokenPrefix(), jwtToken);
        } catch (IOException e) {
            throw new JWTCreationException(e.getMessage(), e);
        }
    }

    @Override
    public JwtPayloadDto getTokenSubject(@NonNull String token) {
        String subject = JWT.require(Algorithm.HMAC512(jwtProperty.getSecretKey().getBytes()))
                .build()
                .verify(token.replace(jwtProperty.getTokenPrefix(), ""))
                .getSubject();
        try {
            return objectMapper.readValue(subject, JwtPayloadDto.class);
        } catch (IOException e) {
            throw new JWTDecodeException(e.getMessage());
        }
    }
}
