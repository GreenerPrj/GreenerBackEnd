package BitProject.Greener.domain.boards;

import lombok.Getter;

@Getter
public class BoardsCreateRequest {


    private Long membersid;

    private String title;

    private String content;

    private String imagePath;

    private BoardsType boardsType;
}
