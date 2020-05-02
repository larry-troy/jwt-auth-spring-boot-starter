package github.com.larrytroy.jwtauthspringbootstarter.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
@ConfigurationProperties("jwt-auth.auth")
public class AuthProperty {

    private String url;

    private List<String> permitUrls;

    private Boolean passwordEncoder = true;
}
