package BitProject.Greener.domain.dto;


import BitProject.Greener.domain.entity.MyPlants;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class MyPlantsDTO{

    private Long id;
    private String name;
    private LocalDateTime bornDate;

    public static MyPlantsDTO convertToDTO(MyPlants myPlants){
        MyPlantsDTO myPlantsDTO = new MyPlantsDTO();
        myPlantsDTO.id = myPlants.getId();
        myPlantsDTO.name = myPlants.getName();
        myPlantsDTO.bornDate = myPlants.getBornDate();
        return myPlantsDTO;
    }

}
