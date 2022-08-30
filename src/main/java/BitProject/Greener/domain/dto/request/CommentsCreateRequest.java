package BitProject.Greener.domain.dto.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentsCreateRequest {

    private Long boardsid;

    private Long parentCommentsId;

    private Long membersid;

    private String title;

    private String content;
    private String nickName;
    private LocalDateTime createDate;
}
