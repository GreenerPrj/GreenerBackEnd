package BitProject.Greener.domain.dto;


import BitProject.Greener.domain.entity.MyPlants;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyPlantsDTO{

    private Long id;
    private String name;
    private LocalDateTime bornDate;
    private String img;

    public static MyPlantsDTO convertToDTO(MyPlants myPlants){
        MyPlantsDTO myPlantsDTO = new MyPlantsDTO();
        myPlantsDTO.id = myPlants.getId();
        myPlantsDTO.name = myPlants.getName();
        myPlantsDTO.bornDate = myPlants.getBornDate();
        return myPlantsDTO;
    }
    public static MyPlantsDTO convertToDTO2(MyPlants myPlants, String url){
        MyPlantsDTO myPlantsDTO = new MyPlantsDTO();
        myPlantsDTO.id = myPlants.getId();
        myPlantsDTO.name = myPlants.getName();
        myPlantsDTO.bornDate = myPlants.getBornDate();
        myPlantsDTO.img = url;
        return myPlantsDTO;
    }

}
