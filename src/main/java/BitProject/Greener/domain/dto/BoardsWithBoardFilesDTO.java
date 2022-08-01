package BitProject.Greener.domain.dto;


import BitProject.Greener.domain.entity.BoardFiles;
import BitProject.Greener.domain.entity.Boards;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class BoardsWithBoardFilesDTO {
    private Long boardsId;
    private String title;
    private String content;

    private LocalDateTime createDate;

    private Long boardFilesId;
    private String fileName;
    private String filePath;

    public static BoardsWithBoardFilesDTO convertToBoardDTO(Boards boards){
        BoardsWithBoardFilesDTO instance = new BoardsWithBoardFilesDTO();
        instance.boardsId = boards.getId();
        instance.title = boards.getTitle();
        instance.content = boards.getContent();
        instance.createDate = boards.createDateTime();
        return instance;
    }

    public void mapBoardsFile(BoardFiles boardFiles){
        this.boardFilesId = boardFiles.getId();
        this.fileName = boardFiles.getFileName();
        this.filePath = boardFiles.getFilePath();
    }

}
