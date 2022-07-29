package BitProject.Greener.jwt;



import BitProject.Greener.domain.entity.TokenEntity;
import BitProject.Greener.domain.entity.UserEntity;
import BitProject.Greener.repository.TokenRespository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;



@Log4j2
@Component
@RequiredArgsConstructor
public class TokenProvider {

    private final TokenRespository tokenRespository;

    @Value("${SECRET_KEY}")
    public  String secret_key;

    @PostConstruct
    protected void init() {
        secret_key = Base64.getEncoder().encodeToString(secret_key.getBytes());
    }

    public  String accessToken(UserEntity userEntity){
        Date expiryDate = Date.from(Instant.now().plus(30, ChronoUnit.MINUTES));
        Claims claims = Jwts.claims().setSubject(userEntity.getEmail());

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512,secret_key)
                .setClaims(claims)
                .setIssuer("temp2 app") // issuer를 줄인 말로 토큰을 발행한 주체
                .setIssuedAt(new Date()) // ussuerd at을 줄인 말로 토큰이 발행된 날짜와 시간을 의미
                .setExpiration(expiryDate) // expiration을 줄인 말로 토큰이 만료되는 시간
                .compact();
    }

    public  String refreshToken(){
        Date expiryDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512,secret_key)
                .setIssuer("temp2 app") // issuer를 줄인 말로 토큰을 발행한 주체

                .setIssuedAt(new Date()) // ussuerd at을 줄인 말로 토큰이 발행된 날짜와 시간을 의미
                .setExpiration(expiryDate) // expiration을 줄인 말로 토큰이 만료되는 시간
                .compact();
    }

    public boolean tokenvaild(String email){
        UserEntity user = UserEntity.builder()
                .email(email)
                .build();

        try {// access 만료, refresh 유효 하면   accesstoken랑 refreshtoken 재발급
            // refreshtoken이 유효하지 않으면 403
            String claims = Jwts.parser()
                    .setSigningKey(secret_key)
                    .parseClaimsJws(tokenRespository.findByEmail(email).getRefresh())
                    .getBody()
                    .getSubject();
            tokenstore(user);
            log.info("access xxxx , refresh oooo");
            return true;

        }
        catch (ExpiredJwtException e){
            log.info("access xxxx , refresh xxxxx");
            return false;
        }
       catch (Exception e){
            log.info(e);
            return false;
       }
    }


    public String tokenstore(UserEntity user) {
        String accessToken = accessToken(user);
        String refreshToken = refreshToken();

        TokenEntity responsetoken = TokenEntity.builder()
                .email(user.getEmail())
                .access(accessToken)
                .refresh(refreshToken)
                .build();
        tokenRespository.save(responsetoken);
        return accessToken;
    }


}
