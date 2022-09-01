package BitProject.Greener.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DiaryUpdateRequest {
    private String content;

    @Builder
    public DiaryUpdateRequest(String content) {this.content = content;}
}
