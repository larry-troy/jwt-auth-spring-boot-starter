package com.github.larrytroy.jwtauthspringbootstarter.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Getter
@Setter
@Component
@ConfigurationProperties("jwt-auth.jwt.access")
public class JwtProperty {

    private Long expirationTime = 0L;

    private String tokenPrefix = "";

    private String secretKey = "";
}
