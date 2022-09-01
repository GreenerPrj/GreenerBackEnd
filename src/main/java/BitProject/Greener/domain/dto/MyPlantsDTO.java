package BitProject.Greener.domain.dto;


import BitProject.Greener.domain.entity.MyPlants;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyPlantsDTO{

    private Long id;
    private String name;
    private LocalDateTime bornDate;
    private String bornDate2;
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
        myPlantsDTO.bornDate2 = myPlants.getBornDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분"));;
        myPlantsDTO.img = url;
        return myPlantsDTO;
    }

}
