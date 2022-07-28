package BitProject.Greener.domain.members.domain.Entity;

import static javax.persistence.GenerationType.IDENTITY;

import BitProject.Greener.common.BaseEntity;
import java.time.LocalDateTime;
import javax.persistence.*;

import BitProject.Greener.domain.members.common.RoleType;
import BitProject.Greener.domain.members.domain.Dto.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

//    @Column(nullable = false)
    private String address;

//    @Column(nullable = false)
    private String detailAddress;

//    @Column(nullable = false)
    private LocalDateTime birthday;

//    @Column(nullable = false)
    private String nickName;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    public UserDto toEntity(){
        UserDto userEntity = UserDto.builder()
                .id(id)
                .name(name)
                .nickName(nickName)
                .email(email)
                .role(RoleType.USER)
                .password(password)
                .build();
        return userEntity;
    }



}
