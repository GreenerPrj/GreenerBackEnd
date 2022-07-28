package BitProject.Greener.domain.members.jwt;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;


@Log4j2
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Value("${SECRET_KEY}")
    public  String secret_key;
    @PostConstruct
    protected void init() {
        secret_key = Base64.getEncoder().encodeToString(secret_key.getBytes());
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            log.info("Filter is running...");
            String accesstoken = parseBearerToken(request);// 리퀘스트에서 토큰 가져오기.

            if (accesstoken != null) {// 토큰 검사하기. JWT이므로 인가 서버에 요청 하지 않고도 검증 가능.

                String claims = Jwts.parser()
                        .setSigningKey(secret_key)
                        .parseClaimsJws(accesstoken)
                        .getBody()
                        .getSubject();

                String userId = claims; // accesstoken에서 userId 가져오기. 위조 된 경우 예외 처리 된다.

                log.info("Authenticated user ID : " + userId); // 인증 완료; SecurityContextHolder에 등록해야 인증된 사용자라고 생각한다.
                AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userId, // 인증된 사용자의 정보. 문자열이 아니어도 아무거나 넣을 수 있다.
                        null, //
                        AuthorityUtils.NO_AUTHORITIES
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authentication);
                SecurityContextHolder.setContext(securityContext);
                log.info("accesstoken ooo");
            }
        }
        catch (ExpiredJwtException ex){
            String userId = ex.getClaims().getSubject(); //useremaill 추출
            if (tokenProvider.tokenvaild(userId)){
                AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userId, // 인증된 사용자의 정보. 문자열이 아니어도 아무거나 넣을 수 있다.
                        null, //
                        AuthorityUtils.NO_AUTHORITIES
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authentication);
                SecurityContextHolder.setContext(securityContext);
                log.info("retoken");
                log.info(userId);
            }
        }
         catch (Exception ex) {
                log.info(ex);
         }
        filterChain.doFilter(request, response);
    }


    private String parseBearerToken(HttpServletRequest request) {
        // Http 리퀘스트의 헤더를 파싱해 Bearer 토큰을 리턴한다.
        String bearerToken = request.getHeader("accessToken");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
