package BitProject.Greener.domain.dto;


import BitProject.Greener.common.RoleType;
import BitProject.Greener.domain.entity.UserEntity;
import lombok.Builder;
import lombok.Data;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class UserDto {

    private Long id;
    private String token;


    @NotBlank(message = "id는 필수입니다.")
    private String nickName;

    @NotBlank(message = "email는 필수입니다.")
    private String email;

    @Enumerated(EnumType.STRING)
    private RoleType role;
    private String password;
    private String passwordcheck;



    public UserEntity toEntity(){
        UserEntity userDto = UserEntity.builder()
                .id(id)
                .nickName(nickName)
                .email(email)
                .password(password)
                .role(RoleType.USER)
                .build();
        return userDto;
    }


}
