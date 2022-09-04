package BitProject.Greener.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;
import BitProject.Greener.common.BaseEntity;
import java.time.LocalDateTime;
import javax.persistence.*;
import BitProject.Greener.common.RoleType;
import BitProject.Greener.domain.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseEntity {

    @GeneratedValue(strategy = IDENTITY)
    @Id
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false,unique = true)
    private String email;
    private String password;
    @Column(nullable = false)
    private String nickName;

    @Enumerated(EnumType.STRING)
    private RoleType role;



    public UserDto toEntity(){
        UserDto userEntity = UserDto.builder()
                .id(id)
                .nickName(nickName)
                .email(email)
                .role(RoleType.USER)
                .password(password)
                .build();
        return userEntity;
    }



}
