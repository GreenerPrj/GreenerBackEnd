package BitProject.Greener.domain.plants.controller;

import BitProject.Greener.domain.plants.MyPlants;
import BitProject.Greener.domain.plants.controller.request.PlantsCreateRequest;
import BitProject.Greener.domain.plants.dto.PlantsDTO;
import BitProject.Greener.domain.plants.service.PlantsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/plants")
@RequiredArgsConstructor
@RestController
public class PlantsController {

    private final PlantsService plantsService;


    @PostMapping
    public ResponseEntity<PlantsDTO> create(
            @RequestBody PlantsCreateRequest request
            ){
        return ResponseEntity.ok(plantsService.createPlants(request));
    }




}
