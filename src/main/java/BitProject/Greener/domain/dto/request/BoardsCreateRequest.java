package BitProject.Greener.domain.dto.request;

import BitProject.Greener.common.BoardsType;
import lombok.Getter;

@Getter
public class BoardsCreateRequest {


    private Long membersid;

    private String title;

    private String content;

    private String nickName;

    private BoardsType boardsType;
}
