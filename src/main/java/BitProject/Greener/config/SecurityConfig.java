package BitProject.Greener.config;


import BitProject.Greener.jwt.JwtAuthenticationEntryPoint;
import BitProject.Greener.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception{

        http
                .cors()
                .and()
                .csrf()         //csrf는 현재 사용하지 않으므로 disable
                .disable()
                .httpBasic() // token을 사용하므로 basic 인증 disable
                .disable()
                .sessionManagement() // session 기반이 아님을 선언
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                .and()
                .authorizeRequests() // /와 /board/**경로는 인증 안해도 됨.
                .antMatchers("/","/auth/signup","/auth/login","/api/v1/boards/**","/oauth/**","/kauth.kakao.com/**").permitAll()
//                .antMatchers("**").permitAll()
                .anyRequest()// 그 이외의 경로는 모두 인증해야함
                .authenticated();
                http.addFilterBefore(jwtAuthenticationFilter, CorsFilter.class);
    }
}
