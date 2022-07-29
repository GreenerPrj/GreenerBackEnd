package BitProject.Greener.controller.request;


import jdk.vm.ci.meta.Local;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
public class MyPlantsUpdateRequest {
    private String name;
    private LocalDateTime bornDate;
    private String imagePath;

    @Builder
    public MyPlantsUpdateRequest(String name, LocalDateTime bornDate, String imagePath) {
        this.name = name;
        this.bornDate = bornDate;
        this.imagePath = imagePath;
    }
}
