package BitProject.Greener.controller;



import BitProject.Greener.domain.dto.UserDto;
import BitProject.Greener.domain.entity.TokenEntity;
import BitProject.Greener.domain.entity.UserEntity;
import BitProject.Greener.repository.TokenRespository;
import BitProject.Greener.service.UserServiceImple;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Log4j2
public class UserController {

    private final UserServiceImple userService;
    private final TokenRespository tokenRespository;



    @PostMapping("/signup")
    public ResponseEntity<?> creating(@Valid @RequestBody UserDto userDto){
        Long a = userService.create(userDto);

        if (a !=null) {
            UserDto responseuserdto = UserDto.builder()
                    .email(userDto.getEmail())
                    .id(userDto.getId())
                    .name(userDto.getName())
                    .build();
            return ResponseEntity.ok().body(responseuserdto);
        }
        else{
            return ResponseEntity.badRequest().body("error");
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserDto userDto, HttpServletResponse response){
        UserEntity user = userService.getByCredentials(userDto.getEmail(),userDto.getPassword());

        if(user!=null){
            String a = userService.tokenstore(user,response);
            TokenEntity tokenEntity = tokenRespository.findByEmail(user.getEmail());
            return ResponseEntity.ok().body("login succes");
        }
        else{
            return ResponseEntity.badRequest().body("login fail");
        }
    }



    @DeleteMapping("/logout")
    public ResponseEntity logout(@RequestBody UserDto userDto) {
        UserEntity user = userService.getByCredentials(userDto.getEmail(), userDto.getPassword());
        if (user != null) {
//            ArrayList a = userService.tokenstore(user);
//            UserDto responseUserDto = userService.tokenvalue(user);
            return ResponseEntity.ok().body("삭제완료");
        }
        return null;
    }

}

