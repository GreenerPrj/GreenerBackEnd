package BitProject.Greener.controller;


import BitProject.Greener.controller.request.BoardsUpdateRequest;
import BitProject.Greener.domain.dto.BoardsWithBoardFilesDTO;
import BitProject.Greener.domain.dto.BoardsWithUserDTO;
import BitProject.Greener.domain.entity.Boards;
import BitProject.Greener.domain.dto.BoardsDTO;
import BitProject.Greener.service.BoardsService;


import BitProject.Greener.domain.dto.request.BoardsCreateRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@RequestMapping("/api/v1/boards")
@RequiredArgsConstructor
@RestController
@Log4j2
public class BoardsController {

    private final BoardsService boardsService;

    @PostMapping()
    public ResponseEntity<BoardsDTO> create(
            @RequestPart MultipartFile file,
            @RequestBody BoardsCreateRequest request, HttpServletRequest request2) {
        return ResponseEntity.ok(boardsService.createBoards(request, file,request2));
    }
    @PutMapping()
    public Long update(@PathVariable Long id, @RequestBody BoardsUpdateRequest boardsUpdateRequest) {
        return boardsService.update(id,boardsUpdateRequest);
    }

    @DeleteMapping()
    public void delete(@PathVariable Long id){
        boardsService.delete(id);
    }


    @GetMapping()
    public ResponseEntity<?> getBoardsWithUserDTO(){
        return ResponseEntity.ok(boardsService.getBoardsWithUserDTO());
    }

    @GetMapping("/{boardsId}/detail")
    public ResponseEntity<BoardsWithBoardFilesDTO> detail(
        @PathVariable Long boardsId
    ){
       return ResponseEntity.ok(boardsService.getDetailWithBoardFiles(boardsId));
    }

}
