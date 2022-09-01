package BitProject.Greener.service;

import BitProject.Greener.controller.request.DiaryUpdateRequest;
import BitProject.Greener.domain.dto.BoardsDTO;
import BitProject.Greener.domain.dto.DiaryDTO;
import BitProject.Greener.domain.dto.DiaryWithDiaryFilesDTO;
import BitProject.Greener.domain.dto.request.DiaryCreateRequest;
import BitProject.Greener.domain.entity.*;
import BitProject.Greener.jwt.TokenProvider;
import BitProject.Greener.repository.DiaryFilesRepository;
import BitProject.Greener.repository.DiaryRepository;
import BitProject.Greener.repository.MyPlantsRepository;
import BitProject.Greener.repository.UserRepository;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final MyPlantsRepository myPlantsRepository;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final AmazonS3Client amazonS3Client;
    private final DiaryFilesRepository diaryFilesRepository;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;
    @Value("${cloud.aws.s3.dir}")
    public String dir;

    @Transactional
    public DiaryDTO createDiary(DiaryCreateRequest request, MultipartFile file, HttpServletRequest request2) throws IOException {
        String username = null;
        try {
            String token = tokenProvider.parseBearerToken(request2);
            username = tokenProvider.tokenEncry(token);
        } catch (ExpiredJwtException e) {
            username = e.getClaims().getSubject();
        } finally {
//            UserEntity userEntity = userRepository.findByEmail(username);
        log.info(request.getMembersid());
            UserEntity userEntity = userRepository.findById(request.getMembersid())
                    .orElseThrow(() -> new RuntimeException("아이디 없음"));
            MyPlants myPlants = myPlantsRepository.findById(request.getMyPlantsid())
                    .orElseThrow(() -> new RuntimeException("내 식물 없음"));
            Diary diary = Diary.of(request.getContent(),request.getCreateDateTime());


            diary.mapMembersAndMyPlants(userEntity, myPlants);
            diaryRepository.save(diary);

            if (file != null) {
                Long fileSize = file.getSize();
                InputStream inputStream = file.getInputStream();
                String originFileName = file.getOriginalFilename();
                String fileName = UUID.randomUUID().toString();
                String savePath = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String filePath = "/diary/" + savePath;

                ObjectMetadata objMeta = new ObjectMetadata();
                objMeta.setContentLength(fileSize);
                String bucket_full = bucket + dir + filePath;
                amazonS3Client.putObject(bucket_full, fileName, inputStream, objMeta);


                DiaryFiles diaryFiles = DiaryFiles.of(originFileName, fileName, filePath);
                diaryFiles.mapDiary(diary);
                diaryFilesRepository.save(diaryFiles);
            }
            return DiaryDTO.convertToDTO(diary);
        }
    }

    @Transactional
    public Long update(Long id, DiaryUpdateRequest diaryUpdateRequest, MultipartFile file) throws IOException {

        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new
                        IllegalArgumentException("해당 다이어리가 존재하지 않습니다."));

        diary.update(diaryUpdateRequest.getContent());

        Optional<DiaryFiles> diaryFiles = diaryFilesRepository.findByDiaryId(id);

        if (file != null) {
            if (diaryFiles.isPresent()) {
                String bucket_full = bucket + dir + diaryFiles.get().getFilePath();
                DeleteObjectRequest request = new DeleteObjectRequest(bucket_full, diaryFiles.get().getFileName());
                amazonS3Client.deleteObject(request);
                diaryFilesRepository.delete(diaryFiles.get());


                String originFileName = file.getOriginalFilename();
                String fileName = UUID.randomUUID().toString();
//                String savePath = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String savePath2 = diaryFiles.get().getFilePath().split("/")[2];
                String filePath = "/diary/" + savePath2;

                ObjectMetadata objMeta = new ObjectMetadata();
                objMeta.setContentLength(file.getSize());
                InputStream inputStream = null;
                try {
                    inputStream = file.getInputStream();
                } catch (IOException e) {
                    log.info(e);
                }
                amazonS3Client.putObject(bucket_full, fileName, inputStream, objMeta);


                DiaryFiles diaryFiles2 = DiaryFiles.of(originFileName, fileName, filePath);
                diaryFiles2.mapDiary(diary);
                diaryFilesRepository.save(diaryFiles2);
            }
            else{
                Long fileSize = file.getSize();
                InputStream inputStream = file.getInputStream();
                String originFileName = file.getOriginalFilename();
                String fileName = UUID.randomUUID().toString();
                String savePath2 = diary.getCreatedDateTime().toString().split("T")[0];

                String filePath = "/diary/" + savePath2;
                ObjectMetadata objMeta = new ObjectMetadata();
                objMeta.setContentLength(fileSize);
                String bucket_full = bucket + dir + filePath;
                amazonS3Client.putObject(bucket_full, fileName, inputStream, objMeta);


                DiaryFiles diaryFiles2 = DiaryFiles.of(originFileName, fileName, filePath);
                diaryFiles2.mapDiary(diary);
                diaryFilesRepository.save(diaryFiles2);
            }
        }

        return id;
    }

    public void delete(Long id) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 다이어리가 존재하지 않습니다."));

        try {
            diaryFilesRepository.findByDiaryId(id).ifPresent(diaryFiles -> {
                String bucket_full = bucket + dir + diaryFiles.getFilePath();
                DeleteObjectRequest request = new DeleteObjectRequest(bucket_full, diaryFiles.getFileName());
                amazonS3Client.deleteObject(request);
                diaryFilesRepository.delete(diaryFiles);
            });
        } catch (Exception e) {
        }
        diaryRepository.delete(diary);
    }

    @Transactional
    public DiaryWithDiaryFilesDTO getDetailWithDiaryFiles(Long diaryId) throws IOException{

        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 다이어리가 존재하지 않습니다."));

        Optional<DiaryFiles> diaryFiles = diaryFilesRepository.findByDiary(diary);
        DiaryWithDiaryFilesDTO diaryWithDiaryFilesDTO = DiaryWithDiaryFilesDTO.convertToDiaryDTO(diary);
        if (diaryFiles.isPresent()) {
            diaryWithDiaryFilesDTO.setImg("https://cl6-2.s3.ap-northeast-2.amazonaws.com" + dir + diaryFiles.get().getFilePath() + "/" + diaryFiles.get().getFileName());
            diaryFiles.ifPresent(diaryWithDiaryFilesDTO::mapDiaryFile);
        }
//        diaryWithDiaryFilesDTO.setDiaryId(diary.getMyPlants().getId());
        return diaryWithDiaryFilesDTO;
    }


}

