package BitProject.Greener.controller;

import BitProject.Greener.domain.dto.MyPlantsCreateRequest;
import BitProject.Greener.domain.dto.MyPlantsDTO;
import BitProject.Greener.service.PlantsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/my-plants")
@RequiredArgsConstructor
@RestController
public class MyPlantsController {

    private final PlantsService plantsService;




    @PostMapping()
    public ResponseEntity<MyPlantsDTO> create(
        @RequestBody MyPlantsCreateRequest request
    ){
        return ResponseEntity.ok(plantsService.createMyPlants(request));
    }

}
