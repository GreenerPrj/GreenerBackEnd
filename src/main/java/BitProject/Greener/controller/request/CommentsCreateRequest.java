package BitProject.Greener.controller.request;

import lombok.Getter;

@Getter
public class CommentsCreateRequest {

    private Long boardsid;

    private Long parentCommentsId;

    private Long membersid;

    private String title;

    private String content;
}
