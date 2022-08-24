package BitProject.Greener.controller;

import BitProject.Greener.domain.dto.BoardsWithUserDTO;
import BitProject.Greener.domain.dto.MyPlantsDTO;
import BitProject.Greener.service.PlantsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/v1/plants")
@RequiredArgsConstructor
@RestController
public class PlantsController {

    private final PlantsService plantsService;


    @GetMapping()
    public ResponseEntity<List<?>> getPlant(){
        return ResponseEntity.ok(plantsService.getplats());
    }

}
