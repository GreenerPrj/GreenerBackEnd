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
    private String imagePath;

    @Builder
    public MyPlantsUpdateRequest(String name, String bornDate, String imagePath) {
        this.name = name;
        this.bornDate = bornDate;
        this.imagePath = imagePath;
    }
}
