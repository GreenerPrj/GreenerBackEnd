package BitProject.Greener.domain.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class MyPlantsCreateRequest {


    private Long plantsId;

    // TODO: 토큰 구현되면 지우고 토큰에서 가져와도 됨
    private Long membersId;

    private String name;

    private LocalDateTime bornDate;

    private String imagePath;

}
