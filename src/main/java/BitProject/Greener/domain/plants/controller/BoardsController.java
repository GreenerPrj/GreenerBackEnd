package BitProject.Greener.domain.plants.controller;


import BitProject.Greener.domain.plants.dto.BoardsDTO;
import BitProject.Greener.domain.plants.service.BoardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class BoardsController {

    private final BoardsService boardsService;
    
    @PostMapping("/api/v1/boards")
    public Long save(@RequestBody BoardsDTO boardsDTO){
        return boardsService.save(boardsDTO);
    }
}
