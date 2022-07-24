package BitProject.Greener.domain.plants.controller.request;

import lombok.Getter;

@Getter
public class PlantsCreateRequest {

    private String name;

    private String content;

    private Integer sunShine;

    private Integer water;

    private Integer tempelature;
}
