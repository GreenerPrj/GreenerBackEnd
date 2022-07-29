package BitProject.Greener.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentsUpdateRequest {
    private String content;

    @Builder
    public CommentsUpdateRequest(String title, String content) {
        this.content = content;
    }
}