package BitProject.Greener.service;

import BitProject.Greener.controller.request.MyPlantsUpdateRequest;
import BitProject.Greener.domain.dto.MyPlantsWithMyPlantsFilesDTO;
import BitProject.Greener.domain.entity.*;
import BitProject.Greener.jwt.TokenProvider;
import BitProject.Greener.repository.*;
import BitProject.Greener.domain.dto.request.MyPlantsCreateRequest;
import BitProject.Greener.domain.dto.MyPlantsDTO;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Log4j2
public class PlantsService {

    private final PlantsRepository plantsRepository;
    private final MyPlantsRepository myPlantsRepository;
    private final MyPlantsFilesRepository myPlantsFilesRepository;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;
    @Value("${cloud.aws.s3.dir}")
    public String dir;

    @Transactional
    public List<Plants> getplats() {
        return plantsRepository.findAll();
    }


    @Transactional
    public Plants getDetailWithBoardFiles(Long boardsId) throws IOException {
        // 게시글 찾기

        Plants plants = plantsRepository.findById(boardsId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));

        return plants;

    }


    @Transactional
    public MyPlantsDTO createMyPlants(MyPlantsCreateRequest request, MultipartFile file, HttpServletRequest request2) throws IOException {
        String username = null;
        try {
            String token = tokenProvider.parseBearerToken(request2);
            username = tokenProvider.tokenEncry(token);
        } catch (ExpiredJwtException e) {
            username = e.getClaims().getSubject();
        } finally {
            UserEntity userEntity = userRepository.findByEmail(username);
            Plants plants = plantsRepository.findById(request.getPlantsId())
                    .orElseThrow(() -> new RuntimeException("식물 없음"));

            MyPlants myPlants = MyPlants.of(request.getName(), LocalDateTime.now());
            myPlants.mapMembersAndPlants(userEntity, plants);
            myPlantsRepository.save(myPlants);


            if (file != null) {
                Long fileSize = file.getSize();
                InputStream inputStream = file.getInputStream();
                String originFileName = file.getOriginalFilename();
                String fileName = UUID.randomUUID().toString();
                String savePath = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String filePath = "/plant/" + savePath;

                ObjectMetadata objMeta = new ObjectMetadata();
                objMeta.setContentLength(fileSize);
                String bucket_full = bucket + dir + filePath;
                amazonS3Client.putObject(bucket_full, fileName, inputStream, objMeta);


                MyPlantsFiles myPlantsFiles = MyPlantsFiles.of(originFileName, fileName, filePath);
                // 외래키 등록(연관관계 매핑)
                myPlantsFiles.mapMyPlants(myPlants);
                // 저장
                myPlantsFilesRepository.save(myPlantsFiles);
                // entity를 그대로 내리면 안돼서 DTO로 변환 후 return


            }
            return MyPlantsDTO.convertToDTO(myPlants);
        }

    }

    @Transactional
    public Long update(Long id, MyPlantsUpdateRequest myPlantsUpdateRequest, MultipartFile file) throws IOException {

        MyPlants myPlants = myPlantsRepository.findById(id)
                .orElseThrow(() -> new
                        IllegalArgumentException("해당 식물이 존재하지 않습니다."));

//         업데이트
        myPlants.update(myPlantsUpdateRequest.getName(),
                myPlantsUpdateRequest.getBornDate());

//         기존 이미지 삭제 후 다시 요청온 이미지 저장
        Optional<MyPlantsFiles> myPlantsFiles = myPlantsFilesRepository.findByMyPlantsId(id);
        if (file != null) {
            if (myPlantsFiles.isPresent()) {
                String bucket_full = bucket + dir + myPlantsFiles.get().getFilePath();
                DeleteObjectRequest request = new DeleteObjectRequest(bucket_full, myPlantsFiles.get().getFileName());
                amazonS3Client.deleteObject(request);
                myPlantsFilesRepository.delete(myPlantsFiles.get());
                String originFileName = file.getOriginalFilename();
                String fileName = UUID.randomUUID().toString();
//                String savePath = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String savePath2 = myPlantsFiles.get().getFilePath().split("/")[2];
                String filePath = "/plant/" + savePath2;

                ObjectMetadata objMeta = new ObjectMetadata();
                objMeta.setContentLength(file.getSize());
                InputStream inputStream = null;
                try {
                    inputStream = file.getInputStream();
                } catch (IOException e) {
                    log.info(e);
                }
                amazonS3Client.putObject(bucket_full, fileName, inputStream, objMeta);

                MyPlantsFiles myPlantsFiles2 = MyPlantsFiles.of(originFileName, fileName, filePath);
                myPlantsFiles2.mapMyPlants(myPlants);
                myPlantsFilesRepository.save(myPlantsFiles2);

            } else {
                Long fileSize = file.getSize();
                InputStream inputStream = file.getInputStream();
                String originFileName = file.getOriginalFilename();
                String fileName = UUID.randomUUID().toString();
                String savePath2 = myPlants.getCreatedDateTime().toString().split("T")[0];

                String filePath = "/plant/" + savePath2;
                ObjectMetadata objMeta = new ObjectMetadata();
                objMeta.setContentLength(fileSize);
                String bucket_full = bucket + dir + filePath;
                amazonS3Client.putObject(bucket_full, fileName, inputStream, objMeta);


                MyPlantsFiles myPlantsFiles2 = MyPlantsFiles.of(originFileName, fileName, filePath);
                myPlantsFiles2.mapMyPlants(myPlants);
                myPlantsFilesRepository.save(myPlantsFiles2);
            }


        }

        return id;
    }


    public void delete(Long id) {
        MyPlants myPlants = myPlantsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("등록된 식물이 없습니다."));

        try {

            myPlantsFilesRepository.findByMyPlantsId(myPlants.getId()).stream().forEach(myPlantsFile -> {
                String bucket_full = bucket + dir + myPlantsFile.getFilePath();
                DeleteObjectRequest request = new DeleteObjectRequest(bucket_full, myPlantsFile.getFileName());
                amazonS3Client.deleteObject(request);
                myPlantsFilesRepository.delete(myPlantsFile);
            });
        } catch (Exception e) {

        }
        myPlantsRepository.delete(myPlants);
    }

    @Transactional
    public MyPlantsWithMyPlantsFilesDTO getDetailWithMyPlantsFiles(Long myPlantsId) throws IOException {
        MyPlants myPlants = myPlantsRepository.findById(myPlantsId)
                .orElseThrow(() -> new IllegalArgumentException("내 식물이 존재하지 않습니다."));
        Optional<MyPlantsFiles> myPlantsFiles = myPlantsFilesRepository.findByMyPlants(myPlants);
        MyPlantsWithMyPlantsFilesDTO myPlantsWithMyPlantsFilesDTO = MyPlantsWithMyPlantsFilesDTO.convertToMyPlantsDTO(myPlants);

        if (myPlantsFiles.isPresent()) {
            myPlantsWithMyPlantsFilesDTO.setImg("https://cl6-2.s3.ap-northeast-2.amazonaws.com" + dir + myPlantsFiles.get().getFilePath() + "/" + myPlantsFiles.get().getFileName());
            // 파일이 있으면 변환한 DTO에 파일 정보도 세팅해서
            myPlantsFiles.ifPresent(myPlantsWithMyPlantsFilesDTO::mapMyPlantsFile);
        }
        // 파일이 있으면 변환한 DTO에 파일 정보도 세팅해서
        myPlantsFiles.ifPresent(myPlantsWithMyPlantsFilesDTO::mapMyPlantsFile);
        return myPlantsWithMyPlantsFilesDTO;
    }




    public List<MyPlantsDTO> getAllMyPlants(Long userId) {


        List<MyPlants> myPlantsList = myPlantsRepository.getMyPlantsByUserId(userId);
        List<MyPlantsDTO> myPlantsDTOList = new ArrayList<>();

        for (MyPlants myPlants : myPlantsList) {
            String url = null;
            MyPlants myPlants2 = myPlantsRepository.findById(myPlants.getId())
                    .orElseThrow(() -> new IllegalArgumentException("내 식물이 존재하지 않습니다."));

            Optional<MyPlantsFiles> myPlantsFiles = myPlantsFilesRepository.findByMyPlants(myPlants2);
            if (myPlantsFiles.isPresent()) {
                url = "https://cl6-2.s3.ap-northeast-2.amazonaws.com" + dir + myPlantsFiles.get().getFilePath() + "/" + myPlantsFiles.get().getFileName();
            }
            
            myPlantsDTOList.add(MyPlantsDTO.convertToDTO2(myPlants, url));
        }
        return myPlantsDTOList;

    }
}


