package BitProject.Greener.domain.plants.service;

import BitProject.Greener.domain.members.Members;
import BitProject.Greener.domain.members.repository.MembersRepository;
import BitProject.Greener.domain.plants.MyPlants;
import BitProject.Greener.domain.plants.Plants;
import BitProject.Greener.domain.plants.controller.request.MyPlantsCreateRequest;
import BitProject.Greener.domain.plants.dto.MyPlantsDTO;
import BitProject.Greener.domain.plants.repository.MyPlantsRepository;
import BitProject.Greener.domain.plants.repository.PlantsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PlantsService {

    private final PlantsRepository plantsRepository;
    private final MyPlantsRepository myPlantsRepository;
    private final MembersRepository membersRepository;

    public MyPlantsDTO createMyPlants(MyPlantsCreateRequest request) {
        Members members = membersRepository.findById(request.getMembersId())
            .orElseThrow(() -> new RuntimeException("아이디 없음"));
        Plants plants = plantsRepository.findById(request.getPlantsId())
            .orElseThrow(() -> new RuntimeException("식물 없음"));
        // MyPlants생성 -> static 생성자 사용(빌더패턴 사용해도 무방)
        MyPlants myPlants = MyPlants.of(request.getName(), request.getBornDate(), request.getImagePath());
        // 외래키 등록(연관관계 매핑)
        myPlants.mapMembersAndPlants(members, plants);
        // 저장
        myPlantsRepository.save(myPlants);
        // entity를 그대로 내리면 안돼서 DTO로 변환 후 return
        return MyPlantsDTO.convertToDTO(myPlants);

    }
}
