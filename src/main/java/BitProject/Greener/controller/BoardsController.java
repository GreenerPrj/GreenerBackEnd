package BitProject.Greener.controller;


import BitProject.Greener.controller.request.BoardsUpdateRequest;
import BitProject.Greener.domain.dto.BoardsWithBoardFilesDTO;
import BitProject.Greener.domain.dto.BoardsWithUserDTO;
import BitProject.Greener.domain.dto.BoardsDTO;
import BitProject.Greener.service.BoardsService;
import BitProject.Greener.domain.dto.request.BoardsCreateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;


@RequestMapping("/api/v1/boards")
@RequiredArgsConstructor
@RestController
@Log4j2
public class BoardsController{

    private final BoardsService boardsService;

    @PostMapping()
    public ResponseEntity<BoardsDTO> create(
            @RequestPart(required = false) MultipartFile file,
            @RequestPart BoardsCreateRequest request, HttpServletRequest request2) throws IOException {
        return ResponseEntity.ok(boardsService.createBoards(request, file, request2));
    }


    @PutMapping("/{boardsId}")
    public Long update(@PathVariable Long boardsId,
                         @RequestPart BoardsUpdateRequest boardsUpdateRequest,
                         @RequestPart(required = false) MultipartFile files) throws IOException {

        return boardsService.update(boardsId, boardsUpdateRequest, files);

    }


    @DeleteMapping("/{boardsId}")
    public ResponseEntity<?> delete(@PathVariable Long boardsId){
        boardsService.delete(boardsId);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }


    @GetMapping()
    public ResponseEntity<Page<BoardsWithUserDTO>> getBoardsWithUserDTO(@PageableDefault(size = 30000) Pageable pageable){
        return ResponseEntity.ok(boardsService.getBoardsWithUserDTO(pageable));

    }


    @GetMapping("/{boardsId}/detail")
    public ResponseEntity<BoardsWithBoardFilesDTO> detail(@PathVariable Long boardsId) throws IOException {
        return ResponseEntity.ok(boardsService.getDetailWithBoardFiles(boardsId));
    }

}
