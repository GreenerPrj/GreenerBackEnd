package BitProject.Greener.controller.request;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MyPlantsUpdateRequest {
    private String name;
    private String bornDate;

    @Builder
    public MyPlantsUpdateRequest(String name, String bornDate) {
        this.name = name;
        this.bornDate = bornDate;
    }
}
