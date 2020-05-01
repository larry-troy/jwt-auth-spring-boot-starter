package github.com.larrytroy.jwtauthspringbootstarter.service;

import github.com.larrytroy.jwtauthspringbootstarter.dto.JwtPayloadDto;

public interface JwtTokenService {

    String create(JwtPayloadDto jwtPayloadDto);

    JwtPayloadDto getTokenSubject(String token);
}
