package BitProject.Greener.domain.members.controller;



import BitProject.Greener.domain.members.domain.Dto.BoardDto;
import BitProject.Greener.domain.members.domain.Entity.BoardEntity;
import BitProject.Greener.domain.members.service.BoardServiceImple;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
public class BoardController {
    private final BoardServiceImple boardServiceImple;

    @GetMapping("home")
    public String home2(){
        return "hi";
    }

    @GetMapping("/board/list")
    public ResponseEntity<?> reading(){
        List<BoardEntity> list = boardServiceImple.reading();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/board/create")
    public ResponseEntity<?> creating(@RequestBody BoardDto boardDto){
        boardServiceImple.creating(boardDto);
        return ResponseEntity.ok().body("글등록완료");
    }

    @PutMapping("/board/list/{id}")
    public ResponseEntity<?> updating(@RequestBody BoardDto boardDto,@PathVariable Long id){
       String a = boardServiceImple.updating(boardDto,id);
        return ResponseEntity.ok().body(a);
    }


    @DeleteMapping("/board/{id}")
    public ResponseEntity<?>deleting(@PathVariable Long id){
        String a = boardServiceImple.deleteing(id);
        return ResponseEntity.ok().body(a);
    }
}
