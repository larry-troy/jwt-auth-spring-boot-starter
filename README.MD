

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
    url: /api/auth #url for authentication
    permit-urls: /api/public/news/**, /api/public-files/** #endpoints that will not require a token
    password-encoder: false
  jwt:
    access:
      expirationTime: 600000 #Token lifetime
      tokenPrefix: Bearer
      secretKey: zupMjoPbUv #Generation secret key
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
  http://localhost:8080/api/auth  | less
  
Http response headers:

HTTP/1.1 200 
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ7XCJ1c2VybmFtZVwiOlwiYWRtaW5AYWRtaW4uYWRtaW5cIixcInJvbGVzXCI6W119IiwiZXhwIjoxNTk4MTg0MzUxfQ.zBji4U9TsEmGZopRYDykO4EZJYFp--rMWd03SPZrTMsuD_bDyx6_PBOejLURtRL1RmuHHPheNyABsu1spxXQlA
X-Content-Type-Options: nosniff  
```