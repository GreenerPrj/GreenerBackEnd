package BitProject.Greener.domain.dto.request;



import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyPlantsCreateRequest {


    private Long plantsId;

    // TODO: 토큰 구현되면 지우고 토큰에서 가져와도 됨
//     private Long membersId;

    private String name;

    private String bornDate;

//    private String bornDate;

}
