package BitProject.Greener.domain.plants.controller;

import BitProject.Greener.domain.plants.service.PlantsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/plants")
@RequiredArgsConstructor
@RestController
public class PlantsController {

    private final PlantsService plantsService;



}
