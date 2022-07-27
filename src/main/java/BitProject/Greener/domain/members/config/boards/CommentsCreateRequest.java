package BitProject.Greener.domain.members.config.boards;

import lombok.Getter;

@Getter
public class CommentsCreateRequest {

    private Long boardsid;

    private Long membersid;

    private String title;

    private String content;
}
