package BitProject.Greener.domain.dto;

import BitProject.Greener.domain.entity.MyPlants;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class MyPlantsDTO {

    private Long id;
    private String name;
//    private LocalDateTime bornDate;
    private String bornDate;
    private String originFileName;
    private String fileName;
    private String filePath;
    public static MyPlantsDTO convertToDTO(MyPlants myPlants){
        MyPlantsDTO myPlantsDTO = new MyPlantsDTO();
        myPlantsDTO.id = myPlants.getId();
        myPlantsDTO.name = myPlants.getName();
        myPlantsDTO.bornDate = myPlants.getBornDate();
        myPlantsDTO.originFileName = myPlants.getOriginFileName();
        myPlantsDTO.fileName = myPlants.getFileName();
        myPlantsDTO.filePath = myPlants.getFilePath();
        return myPlantsDTO;
    }


}
