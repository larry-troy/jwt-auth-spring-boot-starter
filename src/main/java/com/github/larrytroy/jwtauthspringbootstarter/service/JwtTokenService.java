package com.github.larrytroy.jwtauthspringbootstarter.service;

import com.github.larrytroy.jwtauthspringbootstarter.dto.JwtPayloadDto;

public interface JwtTokenService {

    String create(JwtPayloadDto jwtPayloadDto);

    JwtPayloadDto getTokenSubject(String token);
}
