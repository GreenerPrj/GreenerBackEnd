package BitProject.Greener.domain.plants.dto;

import BitProject.Greener.domain.plants.Plants;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

public class PlantsDTO {

    private Long id;

    private String name;

    private String content;

    private Integer sunShine;

    private Integer water;

    private Integer tempelature;

    public PlantsDTO create(Plants plants){
        this.id = plants.getId();
        this.name = plants.getName();
        this.content = plants.getContent();
        this.sunShine = plants.getSunShine();
        this.water = plants.getWater();
        this.tempelature = plants.getTemperature();
        return this;
    }

}
