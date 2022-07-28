package BitProject.Greener.boards.controller.request;

import BitProject.Greener.boards.BoardsType;
import lombok.Getter;

@Getter
public class BoardsCreateRequest {


    private Long membersid;

    private String title;

    private String content;

    private String imagePath;

    private BoardsType boardsType;
}
