

jwt-auth-spring-boot-starter.
=====================

Is a starter that will create an endpoint and will take over
 the generation of the jwt token, the generation 
 of the refresh token.
***
All you need to do is connect it via maven or gradle 
and implement the UserDetails interface.

### Quick start
** 1. To Add repository
```groovy
repositories {
      mavenCentral()
      maven {
          url 'https://raw.github.com/larry-troy/jwt-auth-spring-boot-starter/mvn-repo'
      }
  }
```

** 2. To Add dependency
```groovy
implementation "com.github.larrytroy:jwt-auth-spring-boot-starter:0.1"
```

** 3. To Add settings for application.yml
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


** 4. Implement UserDetailsService (The class's name must be 'UserDetailsServiceImpl')
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

** 5. Result
```bash
curl -i --header "Content-Type: application/json" \
  --request POST \
  --data '{"username":"xyz","password":"xyz"}' \
  http://localhost:9595/api/auth  | less
  
Response:

HTTP/1.1 200 
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Authorization: BearereyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ7XCJ1c2VybmFtZVwiOlwieHl6XCIsXCJyb2xlc1wiOltdfSIsImV4cCI6MTU5ODE4MjQ3Mn0.PU0MTcJ5N2-K4yhGRHunhfnd4cNsOQwIsKoeMXfeO2qwslL4Y0hUSMlmEsJYrQiOf-HNIuA004WdS8J49eBUNA
X-Content-Type-Options: nosniff  
```