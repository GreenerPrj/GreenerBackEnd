package BitProject.Greener.controller;

import BitProject.Greener.controller.request.MyPlantsUpdateRequest;
import BitProject.Greener.domain.dto.MyPlantsCreateRequest;
import BitProject.Greener.domain.dto.MyPlantsDTO;
import BitProject.Greener.service.PlantsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping()
    public Long update(@PathVariable Long id, @RequestBody MyPlantsUpdateRequest myPlantsUpdateRequest){
        return plantsService.update(id, myPlantsUpdateRequest)
    }

    @DeleteMapping()
    public void delete(@PathVariable Long id){
        plantsService.delete(id);
    }

}
