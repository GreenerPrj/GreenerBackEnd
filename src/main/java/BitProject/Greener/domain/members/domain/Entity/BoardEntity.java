package BitProject.Greener.domain.members.domain.Entity;



import BitProject.Greener.domain.members.domain.Dto.BoardDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String content;

   public BoardDto toDto(){
        BoardDto boardEntity = BoardDto.builder()
                .id(id)
                .title(title)
                .content(content)
                .build();
        return boardEntity;
    }
}
