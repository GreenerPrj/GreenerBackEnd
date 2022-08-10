package BitProject.Greener.controller;


import BitProject.Greener.controller.request.MyPlantsUpdateRequest;



import BitProject.Greener.domain.dto.request.MyPlantsCreateRequest;
import BitProject.Greener.domain.dto.MyPlantsDTO;
import BitProject.Greener.service.PlantsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/api/v1/my-plants")
@RequiredArgsConstructor
@RestController
public class MyPlantsController {

    private final PlantsService plantsService;



//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping()
//    public void create(@Valid @ModelAttribute BoardsDTO boardsDTO){
//        plantsService.create(getMyPlantsDTO());
//    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public ResponseEntity<MyPlantsDTO> create(@RequestBody MyPlantsCreateRequest request, HttpServletRequest request2){
        return ResponseEntity.ok(plantsService.createMyPlants(request, request2));
    }

    @PutMapping()
    public Long update(@PathVariable Long id, @RequestBody MyPlantsUpdateRequest myPlantsUpdateRequest){
        return plantsService.update(id, myPlantsUpdateRequest);
    }

    @DeleteMapping()
    public void delete(@PathVariable Long id){
        plantsService.delete(id);
    }

    @GetMapping()
    public ResponseEntity<List<MyPlantsDTO>> getMyPlantsDTO(){
        return ResponseEntity.ok(plantsService.getAllMyPlants());
    }

    @GetMapping("/{myPlantsId}/detail")
    public ResponseEntity<MyPlantsDTO> detail(
            @PathVariable Long myPlantsId
    ){
        return ResponseEntity.ok(plantsService.getDetailWithMyPlants(myPlantsId));
    }

}
