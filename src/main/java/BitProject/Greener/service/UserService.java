package BitProject.Greener.service;

import BitProject.Greener.domain.dto.MyPlantsDTO;
import java.util.List;

public interface UserService {

    List<MyPlantsDTO> getMyPlants(Long userId);
}
