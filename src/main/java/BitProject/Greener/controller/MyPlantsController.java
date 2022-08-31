package BitProject.Greener.controller;


import BitProject.Greener.controller.request.MyPlantsUpdateRequest;
import BitProject.Greener.domain.dto.MyPlantsWithMyPlantsFilesDTO;
import BitProject.Greener.domain.dto.request.MyPlantsCreateRequest;
import BitProject.Greener.domain.dto.MyPlantsDTO;
import BitProject.Greener.service.PlantsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RequestMapping("/api/v1/my-plants")
@RequiredArgsConstructor
@RestController
public class MyPlantsController {

    private final PlantsService plantsService;


    @PostMapping()
    public ResponseEntity<MyPlantsDTO> create(
            @RequestPart(required = false) MultipartFile file,
            @RequestPart MyPlantsCreateRequest request, HttpServletRequest request2) throws IOException {
        return ResponseEntity.ok(plantsService.createMyPlants(request,file, request2));
    }

    @PutMapping("/{myPlantsId}")
    public Long update(@PathVariable Long myPlantsId,
                       @RequestPart MyPlantsUpdateRequest myplantsUpdateRequest,
                       @RequestPart(required = false) MultipartFile files

    ) throws IOException {
        return plantsService.update(myPlantsId,myplantsUpdateRequest, files);
    }

    @DeleteMapping("/{myPlantsId}")
    public ResponseEntity<?> delete(@PathVariable Long myPlantsId){
        plantsService.delete(myPlantsId);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @GetMapping()
    public ResponseEntity<List<MyPlantsDTO>> getMyPlantsDTO(){
        return ResponseEntity.ok(plantsService.getAllMyPlants());
    }

    @GetMapping("/{myPlantsId}/detail")
    public ResponseEntity<MyPlantsWithMyPlantsFilesDTO> detail(
            @PathVariable Long myPlantsId
    ) throws IOException {
        return ResponseEntity.ok(plantsService.getDetailWithMyPlantsFiles(myPlantsId));
    }

}
