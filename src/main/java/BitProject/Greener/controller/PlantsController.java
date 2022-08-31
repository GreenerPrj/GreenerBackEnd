package BitProject.Greener.controller;


import BitProject.Greener.domain.entity.Plants;
import BitProject.Greener.service.PlantsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.List;

@RequestMapping("/api/v1/plants")
@RequiredArgsConstructor
@RestController
public class PlantsController {

    private final PlantsService plantsService;

    @GetMapping()
    public ResponseEntity<List<?>> getPlant() {
        return ResponseEntity.ok(plantsService.getplats());
    }


    @GetMapping("/{plants_boardsId}/detail")
    public ResponseEntity<Plants> detail(@PathVariable Long plants_boardsId) throws IOException {
        return ResponseEntity.ok(plantsService.getDetailWithBoardFiles(plants_boardsId));
    }
}
