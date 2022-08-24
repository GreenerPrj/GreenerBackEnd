package BitProject.Greener.service;

import BitProject.Greener.controller.request.MyPlantsUpdateRequest;

import BitProject.Greener.domain.entity.UserEntity;
import BitProject.Greener.jwt.TokenProvider;

import BitProject.Greener.repository.*;
import BitProject.Greener.domain.entity.MyPlants;
import BitProject.Greener.domain.entity.Plants;
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
@Transactional
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
    public List<Plants> getplats(){
        return plantsRepository.findAll();
    }
    @Transactional
    public MyPlantsDTO createMyPlants(MyPlantsCreateRequest request, MultipartFile file, HttpServletRequest request2) {
        String username = null;
        try {
            String token = tokenProvider.parseBearerToken(request2);
            username = tokenProvider.tokenEncry(token);
        }
        catch (ExpiredJwtException e){
            username = e.getClaims().getSubject();
        }
        finally {

            UserEntity userEntity = userRepository.findByEmail(username);
            Plants plants = plantsRepository.findById(request.getPlantsId()).orElseThrow(() -> new RuntimeException("식물 없음"));

            String originFileName = file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            String savePath = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            File saveFile = new File(savePath);
            if (!saveFile.exists()) {
                saveFile.mkdir();
            }

            String filePath = savePath + "\\" + fileName;
            String filePath2 = Paths.get(savePath, fileName).toString();


            try {
                file.transferTo(Paths.get(filePath2));
            } catch (Exception e) {
                e.printStackTrace();
            }
            // MyPlants생성 -> static 생성자 사용(빌더패턴 사용해도 무방)
            MyPlants myPlants = MyPlants.of(request.getName(), LocalDateTime.now(), originFileName, fileName, filePath);
            // 외래키 등록(연관관계 매핑)
            myPlants.mapMembersAndPlants(userEntity, plants);
            // 저장
            log.info(userEntity.getId());
            myPlantsRepository.save(myPlants);
            // entity를 그대로 내리면 안돼서 DTO로 변환 후 return
            return MyPlantsDTO.convertToDTO(myPlants);

        }

    }


//    @Transactional
//    public Long update(Long id, MyPlantsUpdateRequest myPlantsUpdateRequest, MultipartFile file){
//        MyPlants myPlants = myPlantsRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("등록된 식물이 없습니다."));
//
//        myPlants.update(myPlantsUpdateRequest.getName(),
//                myPlantsUpdateRequest.getBornDate());
//
//
//        return id;
//    }

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
