package BitProject.Greener.domain.dto;

import BitProject.Greener.domain.entity.Diary;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class DiaryDTO {

    private Long id;
    private Long myPlantsId;
    private Long membersId;
    private String content;
    private LocalDateTime createDateTime;

    public static DiaryDTO convertToDTO(Diary diary) {
        DiaryDTO diaryDTO = new DiaryDTO();
        diaryDTO.id = diary.getId();
        diaryDTO.membersId =
                Objects.nonNull(diary.getUserEntity()) ? diary.getUserEntity().getId()
                        : null;
        diaryDTO.myPlantsId =
                Objects.nonNull(diary.getMyPlants()) ? diary.getMyPlants().getId()
                        :null;
        diaryDTO.content = diary.getContent();
        diaryDTO.createDateTime = diary.createDateTime();
        return diaryDTO;
    }
}
