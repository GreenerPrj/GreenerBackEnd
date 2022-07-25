package BitProject.Greener.domain.boards;

import lombok.Getter;

@Getter
public class CommentsCreateRequest {

    private Long boardsid;

    private Long membersid;

    private String title;

    private String content;
}
