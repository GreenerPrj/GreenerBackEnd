package BitProject.Greener.controller;


import BitProject.Greener.domain.entity.Boards;
import BitProject.Greener.domain.dto.BoardsDTO;
import BitProject.Greener.service.BoardsService;
import BitProject.Greener.controller.request.BoardsCreateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/boards")
@RequiredArgsConstructor
@RestController
@Log4j2
public class BoardsController {

    private final BoardsService boardsService;

    @PostMapping()
    public ResponseEntity<BoardsDTO> create(@RequestBody BoardsCreateRequest request) {
        return ResponseEntity.ok(boardsService.createBoards(request));
    }

    @GetMapping("/list")
    public ResponseEntity<?> reading(){
        List<Boards> list = boardsService.reading();
        return ResponseEntity.ok().body(list);
    }

}
