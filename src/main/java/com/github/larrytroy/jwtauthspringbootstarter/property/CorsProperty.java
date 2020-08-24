package com.github.larrytroy.jwtauthspringbootstarter.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Component
@ConfigurationProperties("jwt-auth.cors")
public class CorsProperty {

    private Boolean enabled = false;

    private List<String> allowedOrigins = new ArrayList<>();
}
