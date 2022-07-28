package BitProject.Greener.domain.plants.controller;

import BitProject.Greener.domain.plants.MyPlants;
import BitProject.Greener.domain.plants.service.PlantsService;
import lombok.RequiredArgsConstructor;
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



}
