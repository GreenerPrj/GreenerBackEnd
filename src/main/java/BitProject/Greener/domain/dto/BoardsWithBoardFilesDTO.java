package BitProject.Greener.domain.dto;


import BitProject.Greener.domain.entity.BoardFiles;
import BitProject.Greener.domain.entity.Boards;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

import BitProject.Greener.domain.entity.Comments;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardsWithBoardFilesDTO {
    private Long boardsId;
    private Long membersId;
    private String title;
    private String content;
    private LocalDateTime createDate;
    private Long boardFilesId;
    private String fileName;
    private String filePath;
    private String nickName;
    private String img;
    private byte[] img2;
    private Long userId;

    private List<String> comment_content;
    private List<String> comment_nickName;
    private List<LocalDateTime> comment_createDateTime;

    public static BoardsWithBoardFilesDTO convertToBoardDTO(Boards boards){
        BoardsWithBoardFilesDTO instance = new BoardsWithBoardFilesDTO();
        instance.boardsId = boards.getId();
        instance.title = boards.getTitle();
        instance.content = boards.getContent();
        instance.createDate = boards.createDateTime();
        instance.nickName = boards.getNickName();

        return instance;
    }

    public void mapBoardsFile(BoardFiles boardFiles){
        this.boardFilesId = boardFiles.getId();
        this.fileName = boardFiles.getFileName();
        this.filePath = boardFiles.getFilePath();
    }




    public void mapComments(List<String> comments, List<LocalDateTime> comments1, List<String> comments2) {
        this.comment_content = comments;
        this.comment_createDateTime = comments1;
        this.comment_nickName = comments2;
    }

}
