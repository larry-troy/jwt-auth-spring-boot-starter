package github.com.larrytroy.jwtauthspringbootstarter.filter;

import github.com.larrytroy.jwtauthspringbootstarter.dto.JwtPayloadDto;
import github.com.larrytroy.jwtauthspringbootstarter.service.JwtTokenService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtTokenService jwtTokenService;

    public JWTAuthorizationFilter(AuthenticationManager authManager,
                                  JwtTokenService jwtTokenService) {
        super(authManager);
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null) {
            chain.doFilter(req, res);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (token != null) {
            JwtPayloadDto jwtPayload = jwtTokenService.getTokenSubject(token);
            if (jwtPayload != null) {
                return new UsernamePasswordAuthenticationToken(jwtPayload.getUsername(), null, new ArrayList<>());
            }
        }
        return null;
    }
}
