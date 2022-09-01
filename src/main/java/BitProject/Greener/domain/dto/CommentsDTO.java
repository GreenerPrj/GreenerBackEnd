package BitProject.Greener.domain.dto;

import BitProject.Greener.domain.entity.Comments;

import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;

@Getter
public class CommentsDTO {

    private Long id;
    private Long membersId;
    private Long parentCommentsId;
    private String content;
    private String nickName;
    private LocalDateTime createDate;



    public static CommentsDTO convertToDTO(Comments comments) {
        CommentsDTO commentsDTO = new CommentsDTO();
        commentsDTO.id = comments.getId();
        commentsDTO.parentCommentsId =
            Objects.nonNull(comments.getParentComments()) ? comments.getParentComments().getId()
                : null;
        // comments의 필드에서 content를 가져와야함
        commentsDTO.membersId =
                Objects.nonNull(comments.getUserEntity()) ? comments.getUserEntity().getId()
                         : null;
        commentsDTO.content = comments.getContent();
        commentsDTO.nickName = comments.getNickName();
        commentsDTO.createDate = comments.createDateTime();
        return commentsDTO;

    }
}
