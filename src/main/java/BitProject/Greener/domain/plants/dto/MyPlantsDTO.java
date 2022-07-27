package BitProject.Greener.domain.plants.dto;

import BitProject.Greener.domain.plants.Entity.MyPlants;
import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class MyPlantsDTO {

    private Long id;
    private String name;
    private LocalDateTime bornDate;
    private String imagePath;

    public static MyPlantsDTO convertToDTO(MyPlants myPlants){
        MyPlantsDTO myPlantsDTO = new MyPlantsDTO();
        myPlantsDTO.id = myPlants.getId();
        myPlantsDTO.name = myPlants.getName();
        myPlantsDTO.bornDate = myPlants.getBornDate();
        myPlantsDTO.imagePath = myPlants.getImagePath();
        return myPlantsDTO;
    }


}
