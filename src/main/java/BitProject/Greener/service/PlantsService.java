package BitProject.Greener.service;

import BitProject.Greener.controller.request.BoardsUpdateRequest;
import BitProject.Greener.controller.request.MyPlantsUpdateRequest;

import BitProject.Greener.domain.entity.*;
import BitProject.Greener.jwt.TokenProvider;

import BitProject.Greener.repository.*;
import BitProject.Greener.domain.dto.request.MyPlantsCreateRequest;
import BitProject.Greener.domain.dto.MyPlantsDTO;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Paths;
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
    private static final String absPath = "src/main/resources/static/images/myPlants";

    @Transactional
    public MyPlantsDTO createMyPlants(MyPlantsCreateRequest request, MultipartFile file, HttpServletRequest request2) {
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
            log.info(request.getBornDate());
            MyPlants myPlants = MyPlants.of(request.getName(), request.getBornDate());

            myPlants.mapMembersAndPlants(userEntity,plants);
            myPlantsRepository.save(myPlants);


            if (file != null) {
                String originFileName = file.getOriginalFilename();
                String fileName = UUID.randomUUID().toString();
                String absPath = "src/main/resources/static/images/";
                String savePath = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String filePath = savePath + "/" + fileName + ".png";
                String filePath2 = Paths.get(absPath + savePath, fileName + ".png").toString();
                File saveFile = new File(absPath + savePath);

                if (!saveFile.exists()) { //저장 디렉토리가 없으면 생성
                    saveFile.mkdir();
                }

                try {
                    file.transferTo(Paths.get(filePath2)); // 사진저장
                } catch (Exception e) {
                    e.printStackTrace();
                }

                log.info(fileName);
                MyPlantsFiles myPlantsFiles = MyPlantsFiles.of(originFileName, fileName, filePath);
                // 외래키 등록(연관관계 매핑)
                myPlantsFiles.mapMyPlants(myPlants);

                // 저장
                myPlantsRepository.save(myPlants);
                // entity를 그대로 내리면 안돼서 DTO로 변환 후 return


            }
            return MyPlantsDTO.convertToDTO(myPlants);
        }

        }

        @Transactional
        public Long update(Long id, MyPlantsUpdateRequest myPlantsUpdateRequest, MultipartFile file) {

            MyPlants myPlants = myPlantsRepository.findById(id)
                    .orElseThrow(() -> new
                            IllegalArgumentException("해당 식물이 존재하지 않습니다."));

//         업데이트
            myPlants.update(myPlantsUpdateRequest.getName(),
                    myPlantsUpdateRequest.getBornDate());

//         기존 이미지 삭제 후 다시 요청온 이미지 저장

            try {
                myPlantsFilesRepository.findByMyPlantsId(id).ifPresent(myPlantsFiles -> {
                    String fullname = absPath + "/" + myPlantsFiles.getFilePath();
                    //현재 게시판에 존재하는 파일객체를 만듬
                    File files = new File(fullname);

                    if (file!=null&&!myPlantsFiles.getOriginFileName().equals(file.getOriginalFilename())) { // 파일이 존재하면
                        files.delete(); // 파일 삭제
                        myPlantsFilesRepository.delete(myPlantsFiles);
                    }


                });

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (file != null) {
                    String originFileName = file.getOriginalFilename();
                    String fileName = UUID.randomUUID().toString();
                    String absPath = "src/main/resources/static/images/";
                    String savePath = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    String filePath = savePath + "/" + fileName + ".png";
                    String filePath2 = Paths.get(absPath + savePath, fileName + ".png").toString();
                    File saveFile = new File(absPath + savePath);

                    if (!saveFile.exists()) { //저장 디렉토리가 없으면 생성
                        saveFile.mkdir();
                    }

                    try {
                        file.transferTo(Paths.get(filePath2)); // 사진저장
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    MyPlantsFiles myPlantsFiles = MyPlantsFiles.of(originFileName, fileName, filePath);
                    myPlantsFiles.mapMyPlants(myPlants);
                    myPlantsFilesRepository.save(myPlantsFiles);
                }

            }



            return id;
        }





    public void delete(Long id){
        MyPlants myPlants = myPlantsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("등록된 식물이 없습니다."));

        String path = "src/main/resources/static/images/myplants";
        try{

            myPlantsFilesRepository.findById(id).ifPresent(myPlantsFiles -> {
                String fullname = path + myPlantsFiles.getFilePath();
                File file = new File(fullname);
                if(file.exists()){
                    file.delete();
                }
                myPlantsFilesRepository.delete(myPlantsFiles);
            });
        }
        catch(Exception e){

        }
            myPlantsRepository.delete(myPlants);
    }

    public List<MyPlantsDTO> getAllMyPlants(){
    List<MyPlants> myPlantsList = myPlantsRepository.findAll();
    List<MyPlantsDTO> myPlantsDTOList = new ArrayList<>();

    for (MyPlants myPlants : myPlantsList) {
        myPlantsDTOList.add(MyPlantsDTO.convertToDTO(myPlants));
    }
    return myPlantsDTOList;

    }


    public MyPlantsDTO getDetailWithMyPlants(Long myplantsId){

        MyPlants myPlants = myPlantsRepository.findById(myplantsId)
                .orElseThrow(() -> new IllegalArgumentException("등록된 내 식물이 존재하지 않습니다."));
        MyPlantsDTO myPlantsDTO = MyPlantsDTO.convertToDTO(myPlants);


        return myPlantsDTO;
    }

}
