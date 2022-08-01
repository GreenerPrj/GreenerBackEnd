package BitProject.Greener.controller;


import BitProject.Greener.controller.request.CommentsUpdateRequest;
import BitProject.Greener.domain.dto.CommentsDTO;
import BitProject.Greener.controller.request.CommentsCreateRequest;
import BitProject.Greener.service.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping()
    public Long update(@PathVariable Long id, @RequestBody CommentsUpdateRequest commentsUpdateRequest) {
        return commentsService.update(id, commentsUpdateRequest);
    }

    @DeleteMapping()
    public void delete(@PathVariable Long id){
        commentsService.delete(id);
    }

}
