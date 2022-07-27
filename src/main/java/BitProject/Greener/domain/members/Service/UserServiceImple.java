package BitProject.Greener.domain.members.Service;


import BitProject.Greener.domain.members.Domain.Dto.UserDto;
import BitProject.Greener.domain.members.Domain.Entity.UserEntity;
import BitProject.Greener.domain.members.repository.MembersRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;


@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImple implements UserService {
    private final MembersRepository membersRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public Long create(@Valid UserDto userDto ) {
        if(!userDto.getPassword().equals(userDto.getPasswordcheck())) {
            return null;
        }
        else {
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userDto.setPasswordcheck(passwordEncoder.encode(userDto.getPasswordcheck()));
            UserEntity a = membersRepository.save(userDto.toEntity());
            return a.getId();
        }
    }

    public UserEntity getByCredentials(final String email, final String password) {

        final UserEntity originalUser = membersRepository.findByEmail(email);
        if (originalUser != null && passwordEncoder.matches(password, originalUser.getPassword())) {
            return originalUser;
        }
        return null;
    }

}
