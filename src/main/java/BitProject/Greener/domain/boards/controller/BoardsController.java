package BitProject.Greener.domain.boards.controller;


import BitProject.Greener.domain.boards.controller.request.BoardsCreateRequest;
import BitProject.Greener.domain.boards.service.BoardsService;
import BitProject.Greener.domain.boards.dto.BoardsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/boards")
@RequiredArgsConstructor
@RestController
public class BoardsController {

    private final BoardsService boardsService;

    @PostMapping()
    public ResponseEntity<BoardsDTO> create(
            @RequestBody BoardsCreateRequest request
    ) {
        return ResponseEntity.ok(boardsService.createBoards(request));
    }
}
