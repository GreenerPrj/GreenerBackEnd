package BitProject.Greener.domain.boards;

import lombok.Getter;

@Getter
public class BoardsDTO {
    private Long id;
    private String title;
    private String content;
    private String imagePath;

    public static BoardsDTO convertToDTO(Boards boards) {
        BoardsDTO boardsDTO = new BoardsDTO();
        boardsDTO.id = boards.getId();
        boardsDTO.title = boards.getTitle();
        boardsDTO.content = boards.getContent();
        boardsDTO.imagePath = boards.getImagePath();
        return boardsDTO;
    }
}