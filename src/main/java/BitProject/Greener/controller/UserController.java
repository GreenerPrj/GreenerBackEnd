package BitProject.Greener.controller;

import BitProject.Greener.domain.dto.MyPlantsDTO;
import BitProject.Greener.domain.dto.UserDto;
import BitProject.Greener.domain.entity.UserEntity;
import BitProject.Greener.service.UserServiceImple;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Log4j2
public class UserController {

    private final UserServiceImple userService;



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
        UserEntity user = userService.getByCredentials(userDto.getEmail(),userDto.getPassword()); //id, pw check

        if(user!=null){
            String a = userService.tokenstore(user,response);
            return ResponseEntity.ok().body("login succes");
        }
        else{
            return ResponseEntity.badRequest().body("login fail");
        }
    }



    @GetMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request) {
        String reponse = userService.logout(request);
        if (reponse.equals("logout")){
            return ResponseEntity.ok().body("logout");
        }
        return ResponseEntity.ok().body("error");
    }

    @GetMapping("/{userId}/myplants")
    public ResponseEntity<List<MyPlantsDTO>> getMyPlants(
        @PathVariable Long userId
    ){
        return ResponseEntity.ok(userService.getMyPlants(userId));

    }

}

