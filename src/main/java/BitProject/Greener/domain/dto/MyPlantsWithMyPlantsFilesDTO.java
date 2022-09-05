package BitProject.Greener.domain.dto;


import BitProject.Greener.domain.entity.MyPlants;
import BitProject.Greener.domain.entity.MyPlantsFiles;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Getter
@Setter
public class MyPlantsWithMyPlantsFilesDTO {

    private Long myPlantsId;
    private String name;
    private String bornDate;
    private String createDate;
    private Long myPlantsFilesId;
    private String fileName;
    private String filePath;
    private String nickName;
    private String img;
    private byte[] img2;
    private Long userId;
    private Long plantsId;

    public static MyPlantsWithMyPlantsFilesDTO convertToMyPlantsDTO(MyPlants myPlants){
        MyPlantsWithMyPlantsFilesDTO instance = new MyPlantsWithMyPlantsFilesDTO();
        instance.myPlantsId = myPlants.getId();
        instance.name = myPlants.getName();
        instance.createDate = myPlants.createDateTime().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분"));
        instance.plantsId =
                Objects.nonNull(myPlants.getPlants()) ? myPlants.getPlants().getId() : null;

        return instance;

    }

    public void mapMyPlantsFile(MyPlantsFiles myPlantsFiles){
        this.myPlantsFilesId = myPlantsFiles.getId();
        this.fileName = myPlantsFiles.getFileName();
        this.filePath = myPlantsFiles.getFilePath();
    }
}
