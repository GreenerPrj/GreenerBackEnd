package BitProject.Greener.service;

import BitProject.Greener.domain.dto.UserDto;
import BitProject.Greener.domain.entity.TokenEntity;
import BitProject.Greener.domain.entity.UserEntity;
import BitProject.Greener.jwt.TokenProvider;
import BitProject.Greener.repository.TokenRespository;
import BitProject.Greener.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;
@Log4j2
@RequiredArgsConstructor
@Service
public class UserServiceImple implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final TokenRespository tokenRespository;


    @Transactional
    public Long create(@Valid UserDto userDto ) {
        if(!userDto.getPassword().equals(userDto.getPasswordcheck())) {
            return null;
        }
        else {
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userDto.setPasswordcheck(passwordEncoder.encode(userDto.getPasswordcheck()));
            UserEntity a = userRepository.save(userDto.toEntity());
            return a.getId();
        }
    }

    public UserEntity getByCredentials(final String email, final String password) {
        final UserEntity originalUser = userRepository.findByEmail(email);
        if (originalUser != null && passwordEncoder.matches(password, originalUser.getPassword())) {
            return originalUser;
        }
        return null;
    }

    public String tokenstore(UserEntity user, HttpServletResponse response){
        TokenEntity a =tokenProvider.tokenstore(user);
        String accessToken = a.getAccess();
        response.setHeader("access",a.getAccess());
        response.setHeader("id",user.getId().toString());
        return accessToken;
    }

    public String logout(HttpServletRequest request){
        String token =tokenProvider.parseBearerToken(request);
        String userid = tokenProvider.tokenEncry(token);
        TokenEntity user = TokenEntity.builder()
                .email(userid)
                .build();
        if (tokenRespository.findByEmail(userid)!=null){
            tokenRespository.delete(user);
            return "logout";
        }
        return "error";
    }


}
