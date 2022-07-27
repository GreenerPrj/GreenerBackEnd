//package BitProject.Greener.domain.members.Domain.Entity;
//
//
//import BitProject.Greener.domain.members.Domain.Dto.UserDto;
//import BitProject.Greener.domain.members.common.RoleType;
//import lombok.Builder;
//import lombok.Getter;
//import javax.persistence.*;
//import javax.validation.constraints.Email;
//
//
//@Getter
//@Entity
//@Builder
//public class UserEntity2 {
//
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String name;
//    @Column(unique = true)
//    private String nickname;
//    @Column(unique = true)
//    @Email
//    private String email;
//
//    @Enumerated(EnumType.STRING)
//    private RoleType role;
//    private String password;
//
//
//    public UserDto toEntity(){
//        UserDto userEntity = UserDto.builder()
//                .id(id)
//                .name(name)
//                .nickname(nickname)
//                .email(email)
//                .role(RoleType.USER)
//                .password(password)
//                .build();
//        return userEntity;
//    }
//
//
//}
