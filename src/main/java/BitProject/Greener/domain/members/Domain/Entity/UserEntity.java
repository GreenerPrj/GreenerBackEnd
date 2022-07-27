package BitProject.Greener.domain.members.Domain.Entity;

import BitProject.Greener.common.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.Email;

import BitProject.Greener.domain.members.Domain.Dto.UserDto;
import BitProject.Greener.domain.members.common.RoleType;
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
//
//    @GeneratedValue(strategy = IDENTITY)
//    @Id
//    @Column(nullable = false)
//    private Long id;
//
//    @Column(nullable = false)
//    private String name;
//
//    @Column(nullable = false)
//    private String email;
//
//    @Column(nullable = false)
//    private String password;
//
//    @Column(nullable = false)
//    private String address;
//
//    @Column(nullable = false)
//    private String detailAddress;
//
//    @Column(nullable = false)
//    private LocalDateTime birthday;
//
//    @Column(nullable = false)
//    private String nickName;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String nickname;
    @Column(unique = true)
    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    private RoleType role;
    private String password;


    public UserDto toEntity(){
        UserDto userEntity = UserDto.builder()
                .id(id)
                .name(name)
                .nickname(nickname)
                .email(email)
                .role(RoleType.USER)
                .password(password)
                .build();
        return userEntity;
    }
}
