package BitProject.Greener.controller;

import BitProject.Greener.controller.request.DiaryUpdateRequest;
import BitProject.Greener.domain.dto.DiaryDTO;
import BitProject.Greener.domain.dto.DiaryWithDiaryFilesDTO;
import BitProject.Greener.domain.dto.request.DiaryCreateRequest;
import BitProject.Greener.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;
import java.io.IOException;
import java.util.List;

@RequestMapping("/api/v1/diary")
@RequiredArgsConstructor
@RestController
public class DiaryController {

    private final DiaryService diaryService;


    @PostMapping()
    public ResponseEntity<DiaryDTO> create(
            @RequestPart(required = false) MultipartFile file,
            @RequestPart DiaryCreateRequest request, HttpServletRequest request2)  throws IOException {
        return ResponseEntity.ok(diaryService.createDiary(request,file ,request2));
    }
    @PutMapping("/{diaryId}")
    public Long update(@PathVariable Long diaryId,
                       @RequestPart DiaryUpdateRequest diaryUpdateRequest,
                       @RequestPart(required = false) MultipartFile files) throws IOException {

        return diaryService.update(diaryId, diaryUpdateRequest, files);

    }

    @DeleteMapping("/{diaryId}")
    public ResponseEntity<?> delete(@PathVariable Long diaryId) {
        diaryService.delete(diaryId);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @GetMapping("/{diaryId}/detail")
    public ResponseEntity<DiaryWithDiaryFilesDTO> detail(
            @PathVariable Long diaryId
    )throws IOException{
        return ResponseEntity.ok(diaryService.getDetailWithDiaryFiles(diaryId));
    }

}
