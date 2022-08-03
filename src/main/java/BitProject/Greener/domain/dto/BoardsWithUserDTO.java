package BitProject.Greener.domain.dto;

import static javax.persistence.EnumType.STRING;

import BitProject.Greener.common.BoardsType;
import BitProject.Greener.common.RoleType;
import BitProject.Greener.domain.entity.Boards;
import BitProject.Greener.domain.entity.UserEntity;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardsWithUserDTO {

    private Long boardsId;
    private String title;
    private String content;
    private BoardsType boardsType;
    private Long userId;
    private String name;
    private String email;

    private String nickName;


    public static BoardsWithUserDTO convertToDto(Boards boards, UserEntity userEntity){
        BoardsWithUserDTO instance = new BoardsWithUserDTO();
        instance.boardsId = boards.getId();
        instance.title = boards.getTitle();
        instance.content = boards.getContent();
        instance.boardsType = boards.getBoardsType();
        instance.userId = userEntity.getId();
        instance.name = userEntity.getName();
        instance.email = userEntity.getEmail();
        instance.nickName = userEntity.getNickName();

        return instance;
    }
}
