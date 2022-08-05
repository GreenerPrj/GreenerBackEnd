package BitProject.Greener.domain.dto;


import BitProject.Greener.common.BoardsType;

import BitProject.Greener.domain.entity.Boards;
import BitProject.Greener.domain.entity.UserEntity;

import lombok.Getter;

import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
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
