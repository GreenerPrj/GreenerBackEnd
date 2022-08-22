package BitProject.Greener.domain.dto;

import BitProject.Greener.domain.entity.BoardFiles;
import BitProject.Greener.domain.entity.MyPlants;
import BitProject.Greener.domain.entity.MyPlantsFiles;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class MyPlantsWithMyPlantsFilesDTO {

    private Long myPlantsId;
    private String name;
    private String bornDate;
    private LocalDateTime createDate;
    private Long myPlantsFilesId;
    private String fileName;
    private String filePath;
    private String nickName;
    private String img;
    private byte[] img2;
    private Long userId;

    public static MyPlantsWithMyPlantsFilesDTO convertToMyPlantsDTO(MyPlants myPlants){
        MyPlantsWithMyPlantsFilesDTO instance = new MyPlantsWithMyPlantsFilesDTO();
        instance.myPlantsId = myPlants.getId();
        instance.name = myPlants.getName();

        return instance;

    }

    public void mapMyPlantsFile(MyPlantsFiles myPlantsFiles){
        this.myPlantsFilesId = myPlantsFiles.getId();
        this.fileName = myPlantsFiles.getFileName();
        this.filePath = myPlantsFiles.getFilePath();
    }
}
