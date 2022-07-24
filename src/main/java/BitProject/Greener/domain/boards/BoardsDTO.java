package BitProject.Greener.domain.boards;

import lombok.Getter;

@Getter
public class BoardsDTO {
    private Long id;
    private String title;
    private String content;

    public static BoardsDTO convertToDTO(Boards boards) {
        BoardsDTO boardsDTO = new BoardsDTO();
        boardsDTO.id = boards.getId();
        boardsDTO.title = boardsDTO.getTitle();
        boardsDTO.content = boardsDTO.getContent();
        return boardsDTO;
    }
}
