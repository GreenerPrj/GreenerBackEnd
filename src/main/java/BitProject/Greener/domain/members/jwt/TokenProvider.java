package BitProject.Greener.domain.members.jwt;



import BitProject.Greener.domain.members.Domain.Entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@Component
public class TokenProvider {

    @Value("${SECRET_KEY}")
    @Autowired
    public  String secret_key;
//    System.out.println(secret_key);
    public  String create(UserEntity userEntity){
    Date expiryDate = Date.from(Instant.now().plus(1, ChronoUnit.MINUTES));

    Map<String, Object> headers = new HashMap<>();
    headers.put("typ", "JWT");

    return Jwts.builder()
            .signWith(SignatureAlgorithm.HS512,secret_key)
            .setHeader(headers)
            .setSubject(userEntity.getEmail()) // subject를 줄인 말로 토큰의 주인을 의미
            .setIssuer("temp2 app") // issuer를 줄인 말로 토큰을 발행한 주체
            .setIssuedAt(new Date()) // ussuerd at을 줄인 말로 토큰이 발행된 날짜와 시간을 의미
            .setExpiration(expiryDate) // expiration을 줄인 말로 토큰이 만료되는 시간
            .compact();
    }

    public String validateAndGetUserId(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(secret_key)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();

    }

}
