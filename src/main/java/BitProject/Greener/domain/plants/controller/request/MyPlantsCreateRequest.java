package BitProject.Greener.domain.plants.controller.request;

import static javax.persistence.FetchType.LAZY;

import BitProject.Greener.domain.members.Members;
import BitProject.Greener.domain.plants.Plants;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
