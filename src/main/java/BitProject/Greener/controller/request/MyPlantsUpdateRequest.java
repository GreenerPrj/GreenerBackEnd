package BitProject.Greener.controller.request;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MyPlantsUpdateRequest {
    private String name;
    private LocalDateTime bornDate;

    @Builder
    public MyPlantsUpdateRequest(String name, LocalDateTime bornDate) {
        this.name = name;
        this.bornDate = bornDate;
    }
}
