package BitProject.Greener.domain.dto;

import BitProject.Greener.domain.entity.Diary;
import BitProject.Greener.domain.entity.DiaryFiles;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class DiaryWithDiaryFilesDTO {

    private Long diaryId;
    private String content;
    private String createDate;
    private Long diaryFilesId;
    private String fileName;
    private Long myPlantsId;
    private String filePath;
    private String img;
    private byte[] img2;

    public static DiaryWithDiaryFilesDTO convertToDiaryDTO(Diary diary){
        DiaryWithDiaryFilesDTO instance = new DiaryWithDiaryFilesDTO();
        instance.diaryId = diary.getId();
        instance.content = diary.getContent();
        instance.createDate = diary.createDateTime().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분"));
        instance.myPlantsId = diary.getMyPlants().getId();
        return instance;
    }
    public void mapDiaryFile(DiaryFiles diaryFiles){
        this.diaryFilesId = diaryFiles.getId();
        this.fileName = diaryFiles.getFileName();
        this.filePath = diaryFiles.getFilePath();
    }

}
