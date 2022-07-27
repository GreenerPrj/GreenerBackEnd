package BitProject.Greener.domain.members.Controller;



import BitProject.Greener.domain.members.Domain.Dto.UserDto;
import BitProject.Greener.domain.members.Domain.Entity.UserEntity;
import BitProject.Greener.domain.members.Service.UserServiceImple;
import BitProject.Greener.domain.members.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserServiceImple userService;

    private final TokenProvider tokenProvider;


    @PostMapping("/signup")
    public ResponseEntity<?> creating(@Valid @RequestBody UserDto userDto){
        log.info(userDto.getEmail());
        Long a = userService.create(userDto);
        if (a !=null) {
           return ResponseEntity.ok().body("sigup succes");
        }
        else{
            return ResponseEntity.badRequest().body("error");
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserDto userDto){

        UserEntity user = userService.getByCredentials(userDto.getEmail(),userDto.getPassword());

        if(user!=null){
            String token = tokenProvider.create(user);
            UserDto responseUserDto = UserDto.builder()
                    .email(user.getEmail())
                    .id(user.getId())
                    .token(token)
                    .build();
            return ResponseEntity.ok().body(responseUserDto);
        }
        else{
            return ResponseEntity.badRequest().body("login fail");
        }
    }


}
