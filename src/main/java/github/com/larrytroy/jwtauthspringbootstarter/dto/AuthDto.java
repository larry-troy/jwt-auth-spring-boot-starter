package github.com.larrytroy.jwtauthspringbootstarter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthDto {

    private String username;

    private String password;
}
