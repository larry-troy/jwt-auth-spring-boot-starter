package com.github.larrytroy.jwtauthspringbootstarter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JwtPayloadDto {

    private String username;

    private List<String> roles;
}
