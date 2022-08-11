package BitProject.Greener.domain.dto;



import BitProject.Greener.common.BoardsType;
import BitProject.Greener.domain.entity.Boards;
import BitProject.Greener.domain.entity.BoardsCategory;
import BitProject.Greener.domain.entity.UserEntity;

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
    private Long userId;
    private String name;
    private String email;
    private String nickName;

    private Long categoryId;
    private BoardsType categoryName;


    public static BoardsWithUserDTO convertToDto(Boards boards, UserEntity userEntity, BoardsCategory category){
        BoardsWithUserDTO instance = new BoardsWithUserDTO();
        instance.boardsId = boards.getId();
        instance.title = boards.getTitle();
        instance.content = boards.getContent();
        instance.userId = userEntity.getId();
        instance.name = userEntity.getName();
        instance.email = userEntity.getEmail();
        instance.nickName = userEntity.getNickName();
        instance.categoryId = category.getId();
        instance.categoryName = category.getName();

        return instance;
    }
}
