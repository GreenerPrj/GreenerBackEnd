package BitProject.Greener.domain.dto.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DiaryCreateRequest {

    private Long myPlantsid;
    private Long membersid;
    private String content;
    private LocalDateTime createDateTime;
}
