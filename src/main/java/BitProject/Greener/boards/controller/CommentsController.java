package BitProject.Greener.boards.controller;


import BitProject.Greener.boards.dto.CommentsDTO;
import BitProject.Greener.boards.controller.request.CommentsCreateRequest;
import BitProject.Greener.boards.service.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
@RestController
public class CommentsController {

    private final CommentsService commentsService;

    @PostMapping()
    public ResponseEntity<CommentsDTO> create(
            @RequestBody CommentsCreateRequest request
    ) {
        return ResponseEntity.ok(commentsService.createComments(request));
    }
}
