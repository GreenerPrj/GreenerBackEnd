package BitProject.Greener.domain.plants.dto;


import BitProject.Greener.domain.boards.Boards;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardsDTO {

    private Long id;
    private String title;
    private String content;

    @Builder
    public  BoardsDTO (Long id,String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public Boards toEntity() {
        return Boards.builder()
                .title(title)
                .content(content)
                .build();
    }
}
