package BitProject.Greener.domain.members.domain.Dto;



import BitProject.Greener.domain.members.domain.Entity.BoardEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BoardDto {
    private long id;
    private String title;
    private String content;


   public BoardEntity toEntity(){
        BoardEntity boardDto = BoardEntity.builder()
                .id(id)
                .title(title)
                .content(content)
                .build();
        return boardDto;
    }



}
