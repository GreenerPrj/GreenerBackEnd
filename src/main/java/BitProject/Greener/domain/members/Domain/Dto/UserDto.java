package BitProject.Greener.domain.members.Domain.Dto;


import BitProject.Greener.domain.members.Domain.Entity.UserEntity;
import BitProject.Greener.domain.members.common.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String token;
    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @NotBlank(message = "id는 필수입니다.")
    private String nickname;

    @NotBlank(message = "email는 필수입니다.")
    private String email;

    @Enumerated(EnumType.STRING)
    private RoleType role;
    private String password;
    private String passwordcheck;



    public UserEntity toEntity(){
        UserEntity userDto = UserEntity.builder()
                .id(id)
                .name(name)
                .nickname(nickname)
                .email(email)
                .password(password)
                .role(RoleType.USER)
                .build();
        return userDto;
    }


}
