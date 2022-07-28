package BitProject.Greener.domain.members.service;



import BitProject.Greener.domain.members.domain.Dto.UserDto;
import BitProject.Greener.domain.members.domain.Entity.UserEntity;
import BitProject.Greener.domain.members.jwt.TokenProvider;
import BitProject.Greener.domain.members.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;



@Log4j2
@RequiredArgsConstructor
@Service
public class UserServiceImple implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;


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

    public String tokenstore(UserEntity user){
        String accessToken =tokenProvider.tokenstore(user);
        return accessToken;
    }
}
