package BitProject.Greener.domain.members.config.boards;

import lombok.Getter;

@Getter
public class CommentsDTO {

    private Long id;
    private String content;

    public static CommentsDTO convertToDTO(Comments comments) {
        CommentsDTO commentsDTO = new CommentsDTO();
        commentsDTO.id = comments.getId();
        commentsDTO.content = commentsDTO.getContent();
        return commentsDTO;

    }
}
