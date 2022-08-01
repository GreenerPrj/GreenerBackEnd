package BitProject.Greener.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardsUpdateRequest {

    private String title;
    private String content;
    private String imagePath;

    @Builder
    public BoardsUpdateRequest(String imagePath,String title, String content) {
        this.title = title;
        this.content = content;
        this.imagePath= imagePath;
    }
}
