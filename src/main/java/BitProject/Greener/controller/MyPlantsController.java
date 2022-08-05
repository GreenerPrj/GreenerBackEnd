package BitProject.Greener.controller;


import BitProject.Greener.controller.request.MyPlantsUpdateRequest;


import BitProject.Greener.domain.dto.BoardsDTO;
import BitProject.Greener.domain.dto.CommentsDTO;
import BitProject.Greener.domain.dto.request.BoardsCreateRequest;
import BitProject.Greener.domain.dto.request.CommentsCreateRequest;
import BitProject.Greener.domain.dto.request.MyPlantsCreateRequest;

import BitProject.Greener.domain.dto.MyPlantsDTO;
import BitProject.Greener.service.PlantsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
