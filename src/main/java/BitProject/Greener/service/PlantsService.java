package BitProject.Greener.service;

import BitProject.Greener.controller.request.MyPlantsUpdateRequest;
import BitProject.Greener.domain.entity.UserEntity;
import BitProject.Greener.jwt.JwtAuthenticationFilter;
import BitProject.Greener.jwt.TokenProvider;
import BitProject.Greener.repository.UserRepository;
import BitProject.Greener.domain.entity.MyPlants;
import BitProject.Greener.domain.entity.Plants;
import BitProject.Greener.domain.dto.request.MyPlantsCreateRequest;
import BitProject.Greener.domain.dto.MyPlantsDTO;
import BitProject.Greener.repository.MyPlantsRepository;
import BitProject.Greener.repository.PlantsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class PlantsService {

    private final PlantsRepository plantsRepository;
    private final MyPlantsRepository myPlantsRepository;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    public MyPlantsDTO createMyPlants(MyPlantsCreateRequest request, HttpServletRequest request2) {
        String token = tokenProvider.parseBearerToken(request2);
        String username = tokenProvider.tokenEncry(token);
        UserEntity userEntity = userRepository.findByEmail(username);
        log.info(request2.getHeader("plantsId"));

        Plants plants = plantsRepository.findById(request.getPlantsId()).orElseThrow(() -> new RuntimeException("식물 없음"));
        // MyPlants생성 -> static 생성자 사용(빌더패턴 사용해도 무방)
        MyPlants myPlants = MyPlants.of(request.getName(), request.getBornDate(), request.getOriginFileName(),request.getFileName(),request.getFilePath());
        // 외래키 등록(연관관계 매핑)
        myPlants.mapMembersAndPlants(userEntity, plants);
        // 저장
        myPlantsRepository.save(myPlants);
        // entity를 그대로 내리면 안돼서 DTO로 변환 후 return
        return MyPlantsDTO.convertToDTO(myPlants);

    }

    public Long update(Long id, MyPlantsUpdateRequest myPlantsUpdateRequest){
        MyPlants myPlants = myPlantsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("등록된 식물이 없습니다."));

        myPlants.update(myPlantsUpdateRequest.getName(),
                myPlantsUpdateRequest.getBornDate(),
                myPlantsUpdateRequest.getImagePath());

        return id;
    }

    public void delete(Long id){
        MyPlants myPlants = myPlantsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("등록된 식물이 없습니다."));

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
