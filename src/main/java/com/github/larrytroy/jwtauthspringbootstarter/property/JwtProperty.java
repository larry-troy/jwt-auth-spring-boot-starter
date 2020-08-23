package com.github.larrytroy.jwtauthspringbootstarter.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("jwt-auth.jwt.access")
public class JwtProperty {

    private Long expirationTime;

    private String tokenPrefix;

    private String secretKey;
}
