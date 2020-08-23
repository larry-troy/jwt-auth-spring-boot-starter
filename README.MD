

jwt-auth-spring-boot-starter.
=====================

is a starter that will create an endpoint and will take over
 the generation of the jwt token, the generation 
 of the refresh token.
***
My author's spring-boot-starter 
which will create an endpoint 
for jwt authentication.
***
All you need to do is connect it via maven or gradle 
and implement the UserDetails interface.

### Quick start
** 1. Added repository
```groovy
repositories {
      mavenCentral()
      maven {
          url 'https://raw.github.com/larry-troy/jwt-auth-spring-boot-starter/mvn-repo'
      }
  }
```

** 2. Added dependency
```groovy
implementation "com.github.larrytroy:jwt-auth-spring-boot-starter:0.1"
```

** 3. Added settings for application.yml
```yaml
jwt-auth:
  auth:
    url: /api/auth
    permitUrls: /api/internal/**
    password-encoder: false
  jwt:
    access:
      expirationTime: 600000
      tokenPrefix: Bearer
      secretKey: 123123123123123123123
```


** 4. Implement UserDetailsService (The class name must be UserDetailsServiceImpl)
```java
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), Collections.emptyList());
    }
}
```