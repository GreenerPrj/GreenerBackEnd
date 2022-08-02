package BitProject.Greener.controller;

<<<<<<< HEAD
import BitProject.Greener.controller.request.MyPlantsUpdateRequest;
import BitProject.Greener.domain.dto.MyPlantsCreateRequest;
=======
import BitProject.Greener.domain.dto.request.MyPlantsCreateRequest;
>>>>>>> 91775ec96c490597ceea36ec7e5b2c47a58409c6
import BitProject.Greener.domain.dto.MyPlantsDTO;
import BitProject.Greener.service.PlantsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/api/v1/my-plants")
@RequiredArgsConstructor
@RestController
public class MyPlantsController {

    private final PlantsService plantsService;

    @PostMapping()
    public ResponseEntity<MyPlantsDTO> create(@RequestBody MyPlantsCreateRequest request, HttpServletRequest request2){
        return ResponseEntity.ok(plantsService.createMyPlants(request,request2));
    }

    @PutMapping()
    public Long update(@PathVariable Long id, @RequestBody MyPlantsUpdateRequest myPlantsUpdateRequest){
        return plantsService.update(id, myPlantsUpdateRequest);
    }

    @DeleteMapping()
    public void delete(@PathVariable Long id){
        plantsService.delete(id);
    }

}
