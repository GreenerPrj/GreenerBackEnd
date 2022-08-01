package BitProject.Greener.service;

import BitProject.Greener.controller.request.MyPlantsUpdateRequest;
import BitProject.Greener.domain.entity.UserEntity;
import BitProject.Greener.repository.UserRepository;
import BitProject.Greener.domain.entity.MyPlants;
import BitProject.Greener.domain.entity.Plants;
import BitProject.Greener.domain.dto.MyPlantsCreateRequest;
import BitProject.Greener.domain.dto.MyPlantsDTO;
import BitProject.Greener.repository.MyPlantsRepository;
import BitProject.Greener.repository.PlantsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PlantsService {

    private final PlantsRepository plantsRepository;
    private final MyPlantsRepository myPlantsRepository;
    private final UserRepository userRepository;

    public MyPlantsDTO createMyPlants(MyPlantsCreateRequest request) {
        UserEntity userEntity = userRepository.findById(request.getMembersId())
            .orElseThrow(() -> new RuntimeException("아이디 없음"));
        Plants plants = plantsRepository.findById(request.getPlantsId())
            .orElseThrow(() -> new RuntimeException("식물 없음"));
        // MyPlants생성 -> static 생성자 사용(빌더패턴 사용해도 무방)
        MyPlants myPlants = MyPlants.of(request.getName(), request.getBornDate(), request.getImagePath());
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
}


